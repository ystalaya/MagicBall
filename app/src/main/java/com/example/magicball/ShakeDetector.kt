package study.android.magicball

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class ShakeDetector(context: Context) : SensorEventListener {
    private var mShakeTimestamp: Long = 0
    private var mListener: OnShakeListener? = null
    private val SHAKE_THRESHOLD_GRAVITY = 2.0
    private val  SHAKE_COUNT_RESET_TIME_MS = 3000

    override fun onAccuracyChanged(
        sensor: Sensor,
        accuracy: Int
    ) {
    }

    fun setOnShakeListener(listener: OnShakeListener?) {
        mListener = listener
    }

    interface OnShakeListener {
        fun onShake()
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        val gX = x / SensorManager.GRAVITY_EARTH
        val gY = y / SensorManager.GRAVITY_EARTH
        val gZ = z / SensorManager.GRAVITY_EARTH
        val gForce =
            Math.sqrt(gX * gX + gY * gY + (gZ * gZ).toDouble())
        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            val now = System.currentTimeMillis()
            if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS > now) {
                return
            }
            mShakeTimestamp = now
            if (mListener != null) {
                mListener!!.onShake()
            }
        }
    }

    init {
        val sensorManager =
            context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer =
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }
}
