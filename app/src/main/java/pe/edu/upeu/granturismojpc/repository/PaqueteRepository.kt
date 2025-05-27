package pe.edu.upeu.granturismojpc.repository

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import pe.edu.upeu.granturismojpc.data.remote.RestPaquete
import pe.edu.upeu.granturismojpc.model.PaqueteCreateDto
import pe.edu.upeu.granturismojpc.model.PaqueteDto
import pe.edu.upeu.granturismojpc.model.PaqueteResp
import pe.edu.upeu.granturismojpc.utils.MultipartUtils
import pe.edu.upeu.granturismojpc.utils.TokenUtils
import java.io.File
import javax.inject.Inject

interface PaqueteRepository{
    suspend fun deletePaquete(paquete: PaqueteDto): Boolean
    suspend fun reportarPaquetes(): List<PaqueteResp> // Cambiado
    suspend fun buscarPaqueteId(id: Long): PaqueteResp // Cambiado
    // Métodos con archivos - CREATE
    suspend fun insertarPaqueteConImagen(paquete: PaqueteCreateDto, imageFile: File): Boolean
    suspend fun insertarPaqueteConImagenUri(paquete: PaqueteCreateDto, imageUri: Uri, context: Context?): Boolean
    suspend fun insertarPaqueteSinImagen(paquete: PaqueteCreateDto, context: Context?): Boolean

    // Métodos con archivos - UPDATE
    suspend fun modificarPaqueteConImagen(paquete: PaqueteDto, imageFile: File): Boolean
    suspend fun modificarPaqueteConImagenUri(paquete: PaqueteDto, imageUri: Uri, context: Context?): Boolean
    suspend fun modificarPaqueteSinImagen(paquete: PaqueteDto): Boolean

    // Método de paginación (si lo necesitas)
    suspend fun reportarPaquetesPaginados(page: Int, size: Int): List<PaqueteResp>
}
class PaqueteRepositoryImp @Inject constructor(
    private val restPaquete: RestPaquete,
    //private val actividadDao: ActividadDao,
): PaqueteRepository{
    companion object {
        private const val TAG = "PaqueteRepository"
    }
    override suspend fun deletePaquete(paquete: PaqueteDto): Boolean {
        val response =
            restPaquete.deletePaquete(TokenUtils.TOKEN_CONTENT,
                paquete.idPaquete)
        return response.code() == 204 || response.isSuccessful
    }
    override suspend fun reportarPaquetes(): List<PaqueteResp> {
        val response =
            restPaquete.reportarPaquete(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?: emptyList()
        else emptyList()
    }
    override suspend fun buscarPaqueteId(id: Long): PaqueteResp {
        val response =
            restPaquete.getPaqueteId(TokenUtils.TOKEN_CONTENT, id)
        return response.body() ?: throw Exception("Paquete no encontrado")
    }
    override suspend fun insertarPaqueteConImagen(paquete: PaqueteCreateDto, imageFile: File): Boolean {
        Log.i(TAG, "Insertando paquete con imagen: $paquete")

        return try {
            val dtoRequestBody = MultipartUtils.createDtoRequestBody(paquete)
            val imagePart = MultipartUtils.createImagePart(imageFile)

            val response = restPaquete.insertarPaquete(
                token = TokenUtils.TOKEN_CONTENT,
                dto = dtoRequestBody,
                imagenFile = imagePart
            )

            Log.i(TAG, "Código de respuesta: ${response.code()}")

            if (response.isSuccessful) {
                Log.i(TAG, "Paquete insertado exitosamente")
                true
            } else {
                Log.e(TAG, "Error al insertar paquete: ${response.code()} - ${response.message()}")
                Log.e(TAG, "ErrorBody: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Excepción al insertar paquete con imagen", e)
            false
        }
    }

    override suspend fun insertarPaqueteConImagenUri(paquete: PaqueteCreateDto, imageUri: Uri, context: Context?): Boolean {
        Log.i(TAG, "Insertando paquete con imagen URI: $paquete")

        return try {
            val dtoRequestBody = MultipartUtils.createDtoRequestBody(paquete)
            val imagePart = MultipartUtils.createImagePartFromUri(context, imageUri)

            if (imagePart == null) {
                Log.e(TAG, "No se pudo crear la parte de imagen desde URI")
                return false
            }

            val response = restPaquete.insertarPaquete(
                token = TokenUtils.TOKEN_CONTENT,
                dto = dtoRequestBody,
                imagenFile = imagePart
            )

            Log.i(TAG, "Código de respuesta: ${response.code()}")

            if (response.isSuccessful) {
                Log.i(TAG, "Paquete insertado exitosamente")
                true
            } else {
                Log.e(TAG, "Error al insertar paquete: ${response.code()} - ${response.message()}")
                Log.e(TAG, "ErrorBody: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Excepción al insertar paquete con imagen URI", e)
            false
        }
    }
    override suspend fun insertarPaqueteSinImagen(paquete: PaqueteCreateDto, context: Context?): Boolean {
        Log.i(TAG, "Insertando paquete con imagen URI: $paquete")

        return try {
            val dtoRequestBody = MultipartUtils.createDtoRequestBody(paquete)
            val imagePart = MultipartUtils.createImagePart(getDefaultImageFile(context!!))

            if (imagePart == null) {
                Log.e(TAG, "No se pudo crear la parte de imagen desde URI")
                return false
            }

            val response = restPaquete.insertarPaquete(
                token = TokenUtils.TOKEN_CONTENT,
                dto = dtoRequestBody,
                imagenFile = imagePart
            )

            Log.i(TAG, "Código de respuesta: ${response.code()}")

            if (response.isSuccessful) {
                Log.i(TAG, "Paquete insertado exitosamente")
                true
            } else {
                Log.e(TAG, "Error al insertar paquete: ${response.code()} - ${response.message()}")
                Log.e(TAG, "ErrorBody: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Excepción al insertar paquete con imagen URI", e)
            false
        }
    }

    override suspend fun modificarPaqueteConImagen(paquete: PaqueteDto, imageFile: File): Boolean {
        Log.i(TAG, "Modificando paquete con imagen: ${paquete.idPaquete}")

        return try {
            val dtoRequestBody = MultipartUtils.createDtoRequestBody(paquete)
            val imagePart = MultipartUtils.createImagePart(imageFile)

            val response = restPaquete.actualizarPaquete(
                token = TokenUtils.TOKEN_CONTENT,
                id = paquete.idPaquete,
                dto = dtoRequestBody,
                imagenFile = imagePart
            )

            val isSuccess = response.isSuccessful && response.body()?.idPaquete != null

            if (isSuccess) {
                Log.i(TAG, "Paquete modificado exitosamente")
            } else {
                Log.e(TAG, "Error al modificar paquete: ${response.code()} - ${response.message()}")
                Log.e(TAG, "ErrorBody: ${response.errorBody()?.string()}")
            }

            isSuccess
        } catch (e: Exception) {
            Log.e(TAG, "Excepción al modificar paquete con imagen", e)
            false
        }
    }

    override suspend fun modificarPaqueteConImagenUri(paquete: PaqueteDto, imageUri: Uri, context: Context?): Boolean {
        Log.i(TAG, "Modificando paquete con imagen URI: ${paquete.idPaquete}")

        return try {
            val dtoRequestBody = MultipartUtils.createDtoRequestBody(paquete)
            val imagePart = MultipartUtils.createImagePartFromUri(context, imageUri)

            if (imagePart == null) {
                Log.e(TAG, "No se pudo crear la parte de imagen desde URI")
                return false
            }

            val response = restPaquete.actualizarPaquete(
                token = TokenUtils.TOKEN_CONTENT,
                id = paquete.idPaquete,
                dto = dtoRequestBody,
                imagenFile = imagePart
            )

            val isSuccess = response.isSuccessful && response.body()?.idPaquete != null

            if (isSuccess) {
                Log.i(TAG, "Paquete modificado exitosamente")
            } else {
                Log.e(TAG, "Error al modificar paquete: ${response.code()} - ${response.message()}")
                Log.e(TAG, "ErrorBody: ${response.errorBody()?.string()}")
            }

            isSuccess
        } catch (e: Exception) {
            Log.e(TAG, "Excepción al modificar paquete con imagen URI", e)
            false
        }
    }

    override suspend fun modificarPaqueteSinImagen(paquete: PaqueteDto): Boolean {
        Log.i(TAG, "Modificando paquete sin cambiar imagen: ${paquete.idPaquete}")

        return try {
            val dtoRequestBody = MultipartUtils.createDtoRequestBody(paquete)

            val response = restPaquete.actualizarPaquete(
                token = TokenUtils.TOKEN_CONTENT,
                id = paquete.idPaquete,
                dto = dtoRequestBody,
                imagenFile = null
            )

            val isSuccess = response.isSuccessful && response.body()?.idPaquete != null

            if (isSuccess) {
                Log.i(TAG, "Paquete modificado exitosamente (sin imagen)")
            } else {
                Log.e(TAG, "Error al modificar paquete: ${response.code()} - ${response.message()}")
                Log.e(TAG, "ErrorBody: ${response.errorBody()?.string()}")
            }

            isSuccess
        } catch (e: Exception) {
            Log.e(TAG, "Excepción al modificar paquete sin imagen", e)
            false
        }
    }


    override suspend fun reportarPaquetesPaginados(page: Int, size: Int): List<PaqueteResp> {
        return try {
            val response = restPaquete.getPaquetesPaginate(
                token = TokenUtils.TOKEN_CONTENT,
                page = page,
                size = size
            )

            if (response.isSuccessful) {
                response.body()?.content ?: emptyList()
            } else {
                Log.e(TAG, "Error al obtener paquetes paginados: ${response.code()} - ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Excepción al obtener paquetes paginados", e)
            emptyList()
        }
    }
}
