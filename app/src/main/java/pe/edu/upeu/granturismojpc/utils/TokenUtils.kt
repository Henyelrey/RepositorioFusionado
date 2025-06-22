package pe.edu.upeu.granturismojpc.utils

import android.content.Context

object TokenUtils {
    private var rawToken: String = "Aqui va el Token" // Valor original (puede venir con "Bearer ")

    var TOKEN_CONTENT: String
        get() = rawToken.removePrefix("Bearer ").trim()
        set(value) {
            rawToken = value
        }

    var API_URL="http://localhost:8080/"
    lateinit var CONTEXTO_APPX: Context
    var USER_LOGIN=""
    var USER_ID:Long=0
    var ID_ASIS_ACT=0L
}