package streetwalker.mobile.ui.marker.addmarker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddMarkerScreen(
    viewModel: MarkerViewModel = hiltViewModel(),
    navBack: () -> Unit,
    defaultLat: Double,
    defaultLon: Double
) {
    var title by rememberSaveable { mutableStateOf("") }
    var desc by rememberSaveable { mutableStateOf("") }
    var visibility by rememberSaveable { mutableStateOf("PUBLIC") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") })
        Spacer(Modifier.height(8.dp))
        // visibility selector simplified
        Row { Text("Visibility:"); Spacer(Modifier.width(8.dp)); Text(visibility) }

        Spacer(Modifier.weight(1f))
        Button(onClick = {
            if (title.isBlank()) {
                // show snackbar / validation
                return@Button
            }
            viewModel.createMarker(title, desc, defaultLat, defaultLon, visibility)
            navBack()
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Create marker")
        }
    }
}
