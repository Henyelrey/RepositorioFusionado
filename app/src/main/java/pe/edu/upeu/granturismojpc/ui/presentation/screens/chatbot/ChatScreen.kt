package pe.edu.upeu.granturismojpc.ui.presentation.screens.chatbot

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
// No necesita cambios aquí, ya que el ViewModel se encarga de la fuente de los mensajes
// import pe.edu.upeu.granturismojpc.ui.presentation.screens.chatbot.ChatViewModel // Ya está importado por @Composable
import pe.edu.upeu.granturismojpc.utils.ChatStateHolder // Importa para posible uso, aunque el ViewModel lo maneja

@Composable
fun ChatScreen(viewModel: ChatViewModel = viewModel()) {
    // Sigue observando los mensajes del ViewModel, que ahora provienen del Singleton
    val mensajes by viewModel.mensajes.collectAsState()
    var texto by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("ChatBot", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(30.dp))

        LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth()) {
            items(mensajes) { msg ->
                Text("${msg.remitente}: ${msg.contenido}", modifier = Modifier.padding(4.dp))
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = texto,
                onValueChange = { texto = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Escribe...") }
            )
            Button(onClick = {
                viewModel.enviarMensaje(texto)
                texto = ""
            }) {
                Text("Enviar")
            }
        }
    }
}