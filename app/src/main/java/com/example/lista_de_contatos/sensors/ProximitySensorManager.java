package com.example.lista_de_contatos.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ProximitySensorManager implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private OnProximityListener listener;

    public interface OnProximityListener {
        void onObjectNear();
    }

    // Inicializa SensorManager e obtém o sensor de proximidade
    public ProximitySensorManager(Context context, OnProximityListener listener) {
        this.listener = listener;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }
    }

    // Registra este listener para receber eventos do sensor
    public void register() {
        if (sensorManager != null && proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    // Cancela o registro para parar de receber eventos do sensor
    public void unregister() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    // Chama listener quando objeto se aproxima do sensor
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY && event.values[0] < proximitySensor.getMaximumRange()) {
            if (listener != null) {
                listener.onObjectNear();
            }
        }
    }

    // Não utilizado, mas exigido pela interface
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
