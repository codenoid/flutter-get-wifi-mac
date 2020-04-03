package com.example.fblue

import androidx.annotation.NonNull;
import android.provider.Settings;
import android.bluetooth.BluetoothAdapter;
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugins.GeneratedPluginRegistrant
import io.flutter.plugin.common.MethodChannel

import android.os.Build
import java.net.NetworkInterface
import java.util.*

class MainActivity: FlutterActivity() {
    private val CHANNEL = "fblue.getbtmac"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
      		if (call.method == "getmac") {
          		result.success(getBluetoothAddress())
      		} else {
        		result.notImplemented()
      		}
    	}
    }
    
    private fun getBluetoothAddress(): String {
        val interfaceName = "wlan0"
        val interfaces: List<NetworkInterface> = Collections
                .list(NetworkInterface.getNetworkInterfaces())
        for (intf in interfaces) {
            if (!intf.getName().equals(interfaceName, ignoreCase=true)) {
                continue
            }
            val mac: ByteArray = intf.getHardwareAddress() ?: return ""
            val buf = StringBuilder()
            for (aMac in mac) {
                buf.append(String.format("%02X:", aMac))
            }
            if (buf.length > 0) {
                buf.deleteCharAt(buf.length - 1)
            }

            return buf.toString()
        }
        return "";
    }
}
