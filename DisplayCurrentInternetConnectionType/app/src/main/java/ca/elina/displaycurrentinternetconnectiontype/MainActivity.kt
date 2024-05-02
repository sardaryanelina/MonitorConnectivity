package ca.elina.displaycurrentinternetconnectiontype

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.elina.displaycurrentinternetconnectiontype.ui.theme.DisplayCurrentInternetConnectionTypeTheme
import ca.elina.displaycurrentinternetconnectiontype.ui.theme.greenColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisplayCurrentInternetConnectionTypeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ConnectionChecker(LocalContext.current)
                }
            }
        }
    }
}

@Composable
fun ConnectionChecker(
    context: Context,
) {
    // creating a variable for connection status.
    val connectionType = remember { mutableStateOf("Not Connected") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        // A Thread that will continuously
        // monitor the Connection Type
        Thread {
            while (true) {

                // Invoking the Connectivity Manager
                val cm =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                // Fetching the Network Information
                val netInfo = cm.allNetworkInfo

                // finding if connection type is wifi or mobile data.
                for (ni in netInfo) {
                    if (ni.typeName.equals("WIFI", ignoreCase = true))
                        if (ni.isConnected) connectionType.value = "WIFI"
                    if (ni.typeName.equals("MOBILE", ignoreCase = true))
                        if (ni.isConnected) connectionType.value = "MOBILE DATA"
                }
            }
        }.start() // Starting the thread

        // adding a text for heading.
        Text(
            text = "Real-Time Internet Connectivity Checker in Android",
            color = greenColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // adding a spacer.
        Spacer(modifier = Modifier.height(20.dp))

        // adding a text for displaying connection type.
        Text(
            text = connectionType.value,
            color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold
        )
    }
}