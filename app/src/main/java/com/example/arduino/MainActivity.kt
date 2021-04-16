package com.example.arduino

import android.bluetooth.BluetoothAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    val device = mBluetoothAdapter.getRemoteDevice("98:D3:32:71:17:DE")
    var btSocket = device.createRfcommSocketToServiceRecord(myUUID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button_atualizar)

        button?.setOnClickListener() {

        }
    }

    private fun CheckBt() {
        Toast.makeText(applicationContext, "It has started", Toast.LENGTH_SHORT).show()
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (!mBluetoothAdapter.enable()) {
            Toast.makeText(applicationContext, "Bluetooth Disabled !", Toast.LENGTH_SHORT).show()
            /* It tests if the bluetooth is enabled or not, if not the app will show a message. */
            finish()
        }

        if (mBluetoothAdapter == null) {
            Toast.makeText(applicationContext, "Bluetooth null !", Toast.LENGTH_SHORT).show()
        }
    }

    fun Connect() {
        val device = mBluetoothAdapter.getRemoteDevice("98:D3:32:71:17:DE")
        Log.d("", "Connecting to ... $device")
        Toast.makeText(applicationContext, "Connecting to ... ${device.name} mac: ${device.uuids[0]} address: ${device.address}", Toast.LENGTH_LONG).show()
        mBluetoothAdapter.cancelDiscovery()
        try {
            btSocket = device.createRfcommSocketToServiceRecord(myUUID)
            /* Here is the part the connection is made, by asking the device to create a RfcommSocket (Unsecure socket I guess), It map a port for us or something like that */
            btSocket.connect()
            Log.d("", "Connection made.")
            Toast.makeText(applicationContext, "Connection made.", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            try {
                btSocket.close()
            } catch (e2: IOException) {
                Log.d("", "Unable to end the connection")
                Toast.makeText(applicationContext, "Unable to end the connection", Toast.LENGTH_SHORT).show()
            }

            Log.d("", "Socket creation failed")
            Toast.makeText(applicationContext, "Socket creation failed", Toast.LENGTH_SHORT).show()
        }

        //beginListenForData()
        /* this is a method used to read what the Arduino says for example when you write Serial.print("Hello world.") in your Arduino code */
    }

    private fun writeData(data: String) {
        var outStream = btSocket.outputStream
        try {
            outStream = btSocket.outputStream
        } catch (e: IOException) {
            //Log.d(FragmentActivity.TAG, "Bug BEFORE Sending stuff", e)
        }
        val msgBuffer = data.toByteArray()

        try {
            outStream.write(msgBuffer)
        } catch (e: IOException) {
            //Log.d(FragmentActivity.TAG, "Bug while sending stuff", e)
        }

    }
}