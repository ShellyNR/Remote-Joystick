package com.example.remotecontroljoystick

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.remotecontroljoystick.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Button


class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val mainViewModel = ViewModelProviders.of(this).get(ViewModelApp::class.java)

        DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        ).apply {
            this.setLifecycleOwner(this@MainActivity)
            this.viewModel = mainViewModel
        }

        // set the onClick method as listener to the button
        findViewById<Button>(R.id.button).setOnClickListener{
                onClick(it)
        }

        var act_main = object:Service {
            override fun onChange(a: Float, e: Float) {
                mainViewModel.setAileron(a)
                mainViewModel.setElevator(e)
            }
        }

        /**

        // initializing the joystick
        val joystick: Joystick = Joystick(this,Service { a: Double, e: Double -> {mainViewModel.setAileron(a)
            mainViewModel.setElevator(e)} } )
        **/

        val joystick: Joystick = Joystick(this,act_main)



        joysticklayout.addView(joystick)



    }

    // called when clicking on the connect button
    public final fun onClick(view: View) {
        val IP = ipaddress.text.toString()
        val Port = port.text.toString().toInt()
        /**
        val IP = findViewById<EditText>(R.id.ipaddress).text.toString()
        val Port = findViewById<EditText>(R.id.port).text.toString().toInt()
        **/
        Model.initModel(IP,Port)
    }



    fun onJoystickMoved(xPercent: Float, yPercent: Float, id: Int) {
        /**
        when (id) {
            R.id.joystickRight -> Log.d(
                "Right Joystick",
                "X percent: $xPercent Y percent: $yPercent"
            )
            R.id.joystickLeft -> Log.d("Left Joystick", "X percent: $xPercent Y percent: $yPercent")

        }
        **/
    }



}


