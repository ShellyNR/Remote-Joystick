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
import android.widget.SeekBar


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

        val rseek = findViewById<View>(R.id.rudderseekbar) as SeekBar
        rseek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                var rudder:Double = (progress-100).toDouble()
                rudder = rudder/100.0
                Model.q.add("set /controls/flight/rudder " + rudder.toString() + "\r\n")
            }
        })

        val tseek = findViewById<View>(R.id.throttleseekbar) as SeekBar
        tseek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                var throttle:Double = progress.toDouble()
                throttle = throttle/100.0
                Model.q.add("set /controls/engines/current-engine/throttle " + throttle.toString() + "\r\n")
            }
        })


        var act_main = object:Service {
            override fun onChange(a: Float, e: Float) {
                setAileron(a)
                setElevator(e)
            }
        }

        val joystick: Joystick = Joystick(this,act_main)

        joysticklayout.addView(joystick)

    }

    // called when clicking on the connect button
    public final fun onClick(view: View) {
        val IP = ipaddress.text.toString()
        val Port = port.text.toString().toInt()
        Model.initModel(IP,Port)
    }


    public final fun setAileron(a:Float) {
        Model.q.add("set /controls/flight/aileron " + a.toString() + "\r\n")
    }

    public final fun setElevator(e:Float) {
        Model.q.add("set /controls/flight/elevator " + e.toString() + "\r\n")
    }


}


