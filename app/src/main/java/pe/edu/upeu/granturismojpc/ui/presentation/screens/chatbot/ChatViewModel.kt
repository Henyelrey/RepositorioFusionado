    package pe.edu.upeu.granturismojpc.ui.presentation.screens.chatbot



    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import kotlinx.coroutines.flow.*
    import kotlinx.coroutines.launch
    import pe.edu.upeu.granturismojpc.data.remote.ChatWebSocketClient
    import pe.edu.upeu.granturismojpc.model.ChatMessage
    import pe.edu.upeu.granturismojpc.utils.TokenUtils

    class ChatViewModel : ViewModel() {

        private val _mensajes = MutableStateFlow<List<ChatMessage>>(emptyList())
        val mensajes: StateFlow<List<ChatMessage>> = _mensajes.asStateFlow()

        private var cliente: ChatWebSocketClient? = null
        private var conectado = false

        init {
            conectarWebSocket()
        }

        private fun conectarWebSocket() {
            cliente = ChatWebSocketClient(
                onMessageReceived = { mensaje ->
                    viewModelScope.launch {
                        _mensajes.update { it + mensaje }
                    }
                },
                onConnected = {
                    conectado = true
                    println("✅ El ViewModel fue notificado: WebSocket conectado")
                },
                onDisconnected = {
                    conectado = false
                    println("🔌 WebSocket desconectado.")
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
                    return
                }
                val mensaje = ChatMessage(remitente, contenido)
                cliente?.enviar(remitente, contenido)
                _mensajes.update { it + mensaje }
            }
        }
    }

