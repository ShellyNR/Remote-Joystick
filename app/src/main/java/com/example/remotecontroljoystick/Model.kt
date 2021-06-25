package com.example.remotecontroljoystick

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.PrintWriter
import java.net.Socket
import java.util.*

object Model {

    fun ConnectServer(ip: String, port: Int) : ()-> Unit {
        return{
            val socket = Socket(ip, port)
            val out = PrintWriter(socket.outputStream, true)
            while(true){



            }
        }
    }

    private val _current = MutableLiveData<String>()

    val Current: LiveData<String>
        get() = _current



    // connect to flightgear
    fun initModel(IP: String, Port: Int ): LiveData<String>{
        _current.value = IP
        return Current
    }

    init {
        _current.value = "not here"
    }




}