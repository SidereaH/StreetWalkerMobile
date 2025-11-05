package streetwalker.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import streetwalker.mobile.nav.AppNavGraph
import streetwalker.mobile.ui.theme.StreetWalkerTheme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StreetWalkerTheme {
                AppNavGraph()
            }
        }
    }
}
