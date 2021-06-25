package com.example.remotecontroljoystick
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

fun interface Service {
    fun onChange(a: Float, e: Float)
}