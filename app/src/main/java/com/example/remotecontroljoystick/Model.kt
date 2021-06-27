package com.example.remotecontroljoystick

import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

object Model {

    val q: BlockingQueue<String> = LinkedBlockingQueue<String>() //create queue

    // connect to flightgear
    fun connectServer(ip: String, port: Int) {
        val socket = Socket(ip, port)
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
