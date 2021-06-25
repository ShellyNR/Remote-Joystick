package com.example.remotecontroljoystick

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View


public class Joystick : SurfaceView, SurfaceHolder.Callback, View.OnTouchListener {

    var act = object:Service {
        override fun onChange(a: Float, e: Float) {}
    }

    private var centerX = 0f
    private var centerY = 0f
    private var baseRadius = 0f
    private var hatRadius = 0f
    //private var joystickCallback: JoystickListener? = null
    private val ratio = 3 //The smaller, the more shading will occur

    private fun setupDimensions() {
        centerX = (5*(width / 12)).toFloat()
        centerY = (height / 4).toFloat()
        baseRadius = (Math.min(width, height) / 4).toFloat()
        hatRadius = (Math.min(width, height) / 6).toFloat()
    }

    constructor(context: Context, service: Service) : super(context){
        holder.addCallback(this)
        setOnTouchListener(this)
        act = service
    }

    constructor(context: Context) : super(context){
        holder.addCallback(this)
        setOnTouchListener(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        setupDimensions()
        drawJoystick(centerX, centerY);
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    private fun drawJoystick(newX: Float, newY: Float) {
        if (getHolder().getSurface().isValid()) {
            val myCanvas: Canvas = this.getHolder().lockCanvas() //Stuff to draw
            val colors = Paint()
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR) // Clear the
            myCanvas.drawColor(Color.WHITE)

            //First determine the sin and cos of the angle that the touched point is at relative to the center of the joystick
            val hypotenuse =
                Math.sqrt(
                    Math.pow(
                        (newX - centerX).toDouble(),
                        2.0
                    ) + Math.pow((newY - centerY).toDouble(), 2.0)
                )
                    .toFloat()
            val sin = (newY - centerY) / hypotenuse //sin = o/h
            val cos = (newX - centerX) / hypotenuse //cos = a/h

            //Draw the base first before shading
            colors.setARGB(255, 100, 100, 100)
            myCanvas.drawCircle(centerX, centerY, baseRadius, colors)
            for (i in 1..(baseRadius / ratio).toInt()) {
                colors.setARGB(
                    150 / i,
                    255,
                    0,
                    0
                ) //Gradually decrease the shade of black drawn to create a nice shading effect
                myCanvas.drawCircle(
                    newX - cos * hypotenuse * (ratio / baseRadius) * i,
                    newY - sin * hypotenuse * (ratio / baseRadius) * i,
                    i * (hatRadius * ratio / baseRadius),
                    colors
                ) //Gradually increase the size of the shading effect
            }

            //Drawing the joystick hat
            for (i in 0..(hatRadius / ratio).toInt()) {
                colors.setARGB(
                    255,
                    (i * (255 * ratio / hatRadius)).toInt(),
                    (i * (255 * ratio / hatRadius)).toInt(), 255
                ) //Change the joystick color for shading purposes
                myCanvas.drawCircle(
                    newX,
                    newY,
                    hatRadius - i.toFloat() * ratio / 2,
                    colors
                ) //Draw the shading for the hat
            }
            getHolder().unlockCanvasAndPost(myCanvas) //Write the new drawing to the SurfaceView
        }
    }

    override fun onTouch(v: View, e: MotionEvent): Boolean {
        if (v == this) {
            if (e.action != MotionEvent.ACTION_UP) {

                val displacement =
                    Math.sqrt(
                        Math.pow(
                            (e.x - centerX).toDouble(),
                            2.0
                        ) + Math.pow((e.y - centerY).toDouble(), 2.0)
                    )
                        .toFloat()
                if (displacement < baseRadius) {
                    drawJoystick(e.x, e.y)
                    /**
                    joystickCallback!!.onJoystickMoved(
                        (e.x - centerX) / baseRadius, (e.y - centerY) / baseRadius,
                        id
                    )
                    **/
                    act.onChange((e.getX() - centerX) / baseRadius, (e.getY() - centerY) / baseRadius)

                } else {

                    val ratio = baseRadius / displacement
                    val constrainedX = centerX + (e.x - centerX) * ratio
                    val constrainedY = centerY + (e.y - centerY) * ratio
                    drawJoystick(constrainedX, constrainedY)
                    /**
                    joystickCallback!!.onJoystickMoved(
                        (constrainedX - centerX) / baseRadius,
                        (constrainedY - centerY) / baseRadius,
                        id
                    )
                    **/

                    act.onChange((constrainedX - centerX) / baseRadius, (constrainedY - centerY) / baseRadius )
                }
            } else drawJoystick(centerX, centerY)
            act.onChange(0f, 0f)

            //joystickCallback!!.onJoystickMoved(0f, 0f, id)

        }
        return true
    }




}
