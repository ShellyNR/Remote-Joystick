package com.example.remotecontroljoystick

import java.io.PrintWriter
import java.lang.Thread.sleep
import java.net.Socket
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

object Model {

    //    val e = LiveData<Double>
//    val a = LiveData<Double>
//    val t = LiveData<Double>
//    val r = LiveData<Double>}
    val q: BlockingQueue<String> = LinkedBlockingQueue<String>() //create queue

    // connect to flightgear
    fun connectServer(ip: String, port: Int) {
        //val socket = Socket(ip, port)
        val socket = Socket("192.168.0.191", 6565)
        val out = PrintWriter(socket.outputStream, true)

        while (true) {
            if(!(q.isEmpty())){
                // get and send "task" from queue
                out.print(q.take())
                out.flush()
            }
        }
        socket.close()
    }


    fun initModel(IP: String, Port: Int) {
        // create another thread that will connect the FG
        val clientThread = Thread(Runnable{connectServer(IP, Port)})
        clientThread.start()
    }
}