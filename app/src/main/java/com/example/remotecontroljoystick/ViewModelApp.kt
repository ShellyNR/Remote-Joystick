package com.example.remotecontroljoystick

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ViewModelApp : ViewModel() {
    fun setAileron(a: Float) {
        Log.d("Aileron:", a.toString());

    }

    fun setElevator(e: Float) {
        Log.d("Elevator", e.toString());

    }

    val _isOn = MutableLiveData<String>()
    val isOn: LiveData<String>
        get() = Model.Current
}




