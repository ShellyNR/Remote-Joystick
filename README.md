# Remote Control Joystick

• Project Description:

We were required to write an app for Android, the app is a remote control for a joystick for FlightGear - flight simulator.  
The app was written in Kotlin, edit in android studio IDE and use MVVM architecture.  
The app allows the user to connect as a client to the FG server and send command strings through this connection,  
these commands will move the plane and we will see the plane moving in the FG simulator.  
_______________________________________________________________________________________________________
• Video Link --->
_______________________________________________________________________________________________________
• Presentation --->  [Project Presentation.pdf](https://github.com/ShellyNR/Remote-Joystick/files/6721598/Project.Presentation.pdf)

_______________________________________________________________________________________________________
• Architecture MVVM

--
_______________________________________________________________________________________________________
• Preliminary requirements

Install the FlightGear ,flight simulator, which we know from Exercise 1 in this semester.  
_______________________________________________________________________________________________________

• Operating instructions

For detailed running instructions, watch the "Project Presentation" that attached to the Git.  
_______________________________________________________________________________________________________
• Connection information

The Android emulator runs on another computer, if we will give the Android app the localhost (127.0.0.1) as the IP for the connection,  
it will not found anything there because it does not run on the same computer that the FG run on.  
Therefore we have to give to the Android app the IP of the computer on the network - the local network.  
For more details you can look on the "Project Presentation" that attached to the Git.  
In the project we used object active, the connection with the sever made in a different thread.  
We have a thread-pool with 2 threads in it.  
One thread will "run the all project" - get information from the user (if the user moves the joystick or the seek-bars) and push the right command to the queue (set the command).
The second thread is responsible of the connection with the server and to execute the command (pulls command out from the queue and sends it to the server) - classic object active.
If the all project was running on one thread, because sending the commands to the server is an IO operation we might stuck the user - so separation prevents the user from waiting for the IO to end, it’s happens in parallel.
_______________________________________________________________________________________________________

• class diagram 

![image](https://user-images.githubusercontent.com/82324960/123545056-ea231f00-d75e-11eb-8808-88970124371b.png)

