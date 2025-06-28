package pe.edu.upeu.granturismojpc.ui.presentation.screens.chatbot

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pe.edu.upeu.granturismojpc.ui.presentation.components.ChatBubble
// No necesita cambios aquí, ya que el ViewModel se encarga de la fuente de los mensajes
// import pe.edu.upeu.granturismojpc.ui.presentation.screens.chatbot.ChatViewModel // Ya está importado por @Composable
import pe.edu.upeu.granturismojpc.utils.ChatStateHolder // Importa para posible uso, aunque el ViewModel lo maneja

@Composable
fun ChatScreen(viewModel: ChatViewModel = viewModel(), sesionId: Long = 1L) {
    // Sigue observando los mensajes del ViewModel, que ahora provienen del Singleton
    val mensajes by viewModel.mensajes.collectAsState()
    var texto by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    // Scroll automático al final cuando hay un nuevo mensaje
    LaunchedEffect(mensajes.size, Unit) {
        listState.animateScrollToItem(mensajes.lastIndex)
        //viewModel.cargarHistorial(sesionId)
    }



    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("ChatBot", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(30.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            items(mensajes) { msg ->
                ChatBubble(msg)
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = texto,
                onValueChange = { texto = it },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                placeholder = { Text("Escribe un mensaje...") },
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    viewModel.enviarMensaje(texto)
                    texto = ""
                },
                shape = RoundedCornerShape(12.dp),
                enabled = texto.isNotBlank()
            ) {
                Text("Enviar")
            }
        }

    }
}