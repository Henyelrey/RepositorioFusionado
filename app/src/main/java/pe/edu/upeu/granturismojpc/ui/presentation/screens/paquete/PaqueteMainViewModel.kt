package pe.edu.upeu.granturismojpc.ui.presentation.screens.paquete

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import pe.edu.upeu.granturismojpc.model.PaqueteDetalleDto
import pe.edu.upeu.granturismojpc.model.PaqueteDetalleResp
import pe.edu.upeu.granturismojpc.model.PaqueteDto
import pe.edu.upeu.granturismojpc.model.PaqueteResp
import pe.edu.upeu.granturismojpc.repository.PaqueteDetalleRepository
import pe.edu.upeu.granturismojpc.repository.PaqueteRepository
import javax.inject.Inject

@HiltViewModel
class PaqueteMainViewModel @Inject constructor(
    private val prodRepo: PaqueteRepository,
    private val detalleRepo: PaqueteDetalleRepository // Inyectamos el repositorio de detalles
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _deleteSuccess = MutableStateFlow<Boolean?>(null)
    val deleteSuccess: StateFlow<Boolean?> get() = _deleteSuccess

    private val _prods = MutableStateFlow<List<PaqueteResp>>(emptyList())
    val prods: StateFlow<List<PaqueteResp>> = _prods

    // Nuevo estado para los detalles de paquetes
    private val _detalles = MutableStateFlow<List<PaqueteDetalleResp>>(emptyList())
    val detalles: StateFlow<List<PaqueteDetalleResp>> = _detalles

    // Estado para controlar el éxito al eliminar un detalle
    private val _deleteDetalleSuccess = MutableStateFlow<Boolean?>(null)
    val deleteDetalleSuccess: StateFlow<Boolean?> get() = _deleteDetalleSuccess

    // ID del paquete cuyos detalles se están visualizando actualmente
    private val _currentPaqueteId = MutableStateFlow<Long?>(null)
    val currentPaqueteId: StateFlow<Long?> = _currentPaqueteId

    init {
        cargarPaquetes()
    }

    fun cargarPaquetes() {
        viewModelScope.launch {
            _isLoading.value = true
            _prods.value = prodRepo.reportarPaquetes()
            _isLoading.value = false
        }
    }

    fun buscarPorId(id: Long): Flow<PaqueteResp> = flow {
        emit(prodRepo.buscarPaqueteId(id))
    }

    fun eliminar(paquete: PaqueteDto) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val success = prodRepo.deletePaquete(paquete)
            if (success) {
                //eliminarPaqueteDeLista(paquete.idPaquete)
                cargarPaquetes()
                _deleteSuccess.value = true
            }else{ _deleteSuccess.value = false }
        } catch (e: Exception) {
            Log.e("PaqueteVM", "Error al eliminar paquete", e)
            _deleteSuccess.value = false
        } finally {
            _isLoading.value = false
        }
    }

    fun clearDeleteResult() {
        _deleteSuccess.value = null
    }

    fun eliminarPaqueteDeLista(id: Long) {
        _prods.value = _prods.value.filterNot { it.idPaquete == id }
    }

    // Nuevas funciones para gestionar detalles de paquetes

    /**
     * Carga los detalles de un paquete específico
     */
    fun cargarDetallesPorPaqueteId(paqueteId: Long) {
        _currentPaqueteId.value = paqueteId
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _detalles.value = detalleRepo.buscarPaqueteDetallesByPaqueteId(paqueteId)
            } catch (e: Exception) {
                Log.e("PaqueteVM", "Error al cargar detalles del paquete $paqueteId", e)
                _detalles.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Elimina un detalle de paquete
     */
    fun eliminarDetalle(detalle: PaqueteDetalleDto) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val success = detalleRepo.deletePaqueteDetalle(detalle)
            if (success) {
                eliminarDetalleDeLista(detalle.idPaqueteDetalle)
                _deleteDetalleSuccess.value = true
            } else {
                _deleteDetalleSuccess.value = false
            }
        } catch (e: Exception) {
            Log.e("PaqueteVM", "Error al eliminar detalle de paquete", e)
            _deleteDetalleSuccess.value = false
        } finally {
            _isLoading.value = false
        }
    }

    /**
     * Elimina un detalle de la lista local sin hacer una nueva petición al servidor
     */
    private fun eliminarDetalleDeLista(idDetalle: Long) {
        _detalles.value = _detalles.value.filterNot { it.idPaqueteDetalle == idDetalle }
    }

    /**
     * Limpia el resultado de eliminación de detalles
     */
    fun clearDeleteDetalleResult() {
        _deleteDetalleSuccess.value = null
    }

    /**
     * Limpia los detalles actuales
     */
    fun clearDetalles() {
        _detalles.value = emptyList()
        _currentPaqueteId.value = null
    }
}