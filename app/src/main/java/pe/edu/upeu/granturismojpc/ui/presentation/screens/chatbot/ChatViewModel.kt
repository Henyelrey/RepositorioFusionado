    package pe.edu.upeu.granturismojpc.ui.presentation.screens.chatbot



    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import kotlinx.coroutines.flow.*
    import kotlinx.coroutines.launch
    import pe.edu.upeu.granturismojpc.data.remote.ChatWebSocketClient
    import pe.edu.upeu.granturismojpc.model.ChatMessage
    import pe.edu.upeu.granturismojpc.utils.ChatStateHolder
    import pe.edu.upeu.granturismojpc.utils.TokenUtils

    class ChatViewModel : ViewModel() {

        val mensajes = ChatStateHolder.mensajes

        private var cliente: ChatWebSocketClient? = null
        private var conectado = false

        init {
            conectarWebSocket()
        }

        private fun conectarWebSocket() {
            cliente = ChatWebSocketClient(
                onMessageReceived = { mensaje ->
                    viewModelScope.launch {
                        ChatStateHolder.addMessage(mensaje) // Agrega el mensaje al Singleton
                    }
                },
                onConnected = {
                    conectado = true
                    println("✅ El ViewModel fue notificado: WebSocket conectado")
                },
                onDisconnected = {
                    conectado = false
                    println("🔌 WebSocket desconectado.")
                    // Opcional: Podrías querer reconectar aquí si es un cierre inesperado
                },
                onError = { error ->
                    conectado = false
                    println("❌ Error en WebSocket: $error")
                },
                token = TokenUtils.TOKEN_CONTENT
            )
            cliente?.connect()
        }


        fun enviarMensaje(contenido: String) {
            val remitente = TokenUtils.USER_LOGIN
            if (contenido.isNotBlank()) {
                if (!conectado) {
                    println("❌ Aún no conectado. Espera unos segundos antes de enviar.")
                    // Podrías mostrar un Toast al usuario aquí
                    return
                }
                val mensaje = ChatMessage(remitente, contenido)
                cliente?.enviar(remitente, contenido)
                ChatStateHolder.addMessage(mensaje) // Agrega el mensaje al Singleton para que también lo vea el remitente
            }
        }
        // Opcional: Un método para desconectar el WebSocket cuando el ViewModel ya no es necesario
        override fun onCleared() {
            super.onCleared()
            cliente?.disconnect()
            println("🔌 WebSocket desconectado al limpiar ViewModel.")
        }

        /**
         * Este método debería llamarse desde tu lógica de cierre de sesión.
         * No desde este ViewModel, sino desde el ViewModel que gestiona la sesión de usuario.
         */
        fun clearChatHistory() {
            ChatStateHolder.clearMessages()
        }
    }

