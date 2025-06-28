    package pe.edu.upeu.granturismojpc.ui.presentation.screens.chatbot



    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import kotlinx.coroutines.flow.*
    import kotlinx.coroutines.launch
    //import pe.edu.upeu.granturismojpc.data.remote.ApiClient
    import pe.edu.upeu.granturismojpc.data.remote.ChatApi
    //import pe.edu.upeu.granturismojpc.data.remote.ChatApiClient
    import pe.edu.upeu.granturismojpc.data.remote.ChatWebSocketClient
    import pe.edu.upeu.granturismojpc.model.ChatMessage

    import pe.edu.upeu.granturismojpc.utils.ChatStateHolder
    import pe.edu.upeu.granturismojpc.utils.TokenUtils

    import pe.edu.upeu.granturismojpc.model.toChatMessages




    class ChatViewModel : ViewModel() {

        val mensajes = ChatStateHolder.mensajes

        private var cliente: ChatWebSocketClient? = null
        private var conectado = false

        init {

            ChatStateHolder.clearMessages()

            viewModelScope.launch {
                try {
                    val token = "Bearer ${TokenUtils.TOKEN}"
                    val sesionDTO = ChatApi.ApiClient.chatApi.obtenerSesionActiva(token)

                    val historial = ChatApi.ApiClient.chatApi.obtenerMensajesPorSesion(token, sesionDTO.sesionId)


                    historial.forEach { mensaje ->
                        ChatStateHolder.addMessage(mensaje)
                    }





                } catch (e: Exception) {
                    println("❌ Error cargando historial: ${e.message}")
                }

            }


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
                token = TokenUtils.TOKEN
            )
            cliente?.connect()
        }


        fun enviarMensaje(contenido: String) {
            val mensaje = ChatMessage("usuario", contenido)
            cliente?.enviar("usuario", contenido)
            ChatStateHolder.addMessage(mensaje)
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

