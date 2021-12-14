package hu.inf.unideb.project

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var square: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        square = findViewById(R.id.square)
        SensorConfiguration()
    }

    private fun SensorConfiguration() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val xAxis = event.values[0]
            val yAxis = event.values[1]

            square.apply {
                rotationX = yAxis * 3f
                rotationY = xAxis * 3f
                rotation = -xAxis
                translationX = xAxis * -10
                translationY = yAxis * 10
            }
            val color = if (yAxis.toInt() == 0 && xAxis.toInt() == 0) Color.GREEN else Color.RED
            square.setBackgroundColor(color)
            square.text = "Fel/Le: ${yAxis.toInt()}\n\n\n\n\n\n\nBal/Jobb: ${xAxis.toInt()}"
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}