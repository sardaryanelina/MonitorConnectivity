package ca.elina.monitorconnectivityusingxml

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var networkListener: NetworkListener
    private var networkStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val checkButton: Button = findViewById(R.id.buttonCheck)

        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(this@MainActivity)
                .collect { status ->
                    networkStatus = status
                    Log.d("NetworkListener", "Network status:$status")
                    displayNetworkStatus()
                    checkButton.setOnClickListener {
                        showNetworkStatus()
                    }
                }
        }

        // example from geeks fro geeks
        // register the UI element button
        val checkButton2: Button = findViewById(R.id.buttonCheck2)

        // handle the button click to trigger
        // checkForInternet function
        // and show the Toast message according to it.
        checkButton2.setOnClickListener {
            if (checkForInternetConnectivity(this)) {
                Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayNetworkStatus() {
        val textView = findViewById<TextView>(R.id.textDisplayCurrentInternetConnectionType)
        if (!networkStatus) {
            textView.text = getString(R.string.no_internet_connection)
        } else {
            textView.text = getString(R.string.internet_is_on)
        }
    }
}
