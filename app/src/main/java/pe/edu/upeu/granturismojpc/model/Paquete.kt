package pe.edu.upeu.granturismojpc.model

import java.math.BigDecimal
import java.time.LocalDate

data class PaqueteDto(
    var idPaquete: Long,
    var titulo: String,
    var descripcion: String?,
    var precioTotal: Double,
    var estado: String?,
    var duracionDias: Int?,
    var localidad: String,
    var tipoActividad: String,
    var cuposMaximos: Int?,
    var proveedor: Long?,
    var fechaInicio: String,
    var fechaFin: String,
    var destino: Long?,
)

data class PaqueteResp(
    val idPaquete: Long,
    val titulo: String,
    val descripcion: String?,
    val precioTotal: BigDecimal,
    val estado: String?,
    val duracionDias: Int?,
    val localidad: String,
    val tipoActividad: String,
    val cuposMaximos: Int?,
    val proveedor: ProveedorResp?,
    val fechaInicio: String,
    val fechaFin: String,
    val destino: DestinoResp?,
    val imagenUrl: String?,
)

data class PaqueteCreateDto(
    var titulo: String,
    var descripcion: String?,
    var precioTotal: Double,
    var estado: String?,
    var duracionDias: Int?,
    var localidad: String,
    var tipoActividad: String,
    var cuposMaximos: Int?,
    var proveedor: Long?,
    var fechaInicio: String,
    var fechaFin: String,
    var destino: Long?,
)

fun PaqueteResp.toDto(): PaqueteDto {
    return PaqueteDto(
        idPaquete = this.idPaquete,
        titulo = this.titulo,
        descripcion = this.descripcion,
        precioTotal = this.precioTotal.toDouble(),
        estado = this.estado,
        duracionDias = this.duracionDias,
        localidad = this.localidad,
        tipoActividad = this.tipoActividad,
        cuposMaximos = this.cuposMaximos,
        proveedor = this.proveedor?.idProveedor,
        fechaInicio = this.fechaInicio,
        fechaFin = this.fechaFin,
        destino = this.destino?.idDestino
    )
}

fun PaqueteDto.toCreateDto(): PaqueteCreateDto {
    return PaqueteCreateDto(
        titulo = this.titulo,
        descripcion = this.descripcion,
        precioTotal = this.precioTotal,
        estado = this.estado,
        duracionDias = this.duracionDias,
        localidad = this.localidad,
        tipoActividad = this.tipoActividad,
        cuposMaximos = this.cuposMaximos,
        proveedor = this.proveedor,
        fechaInicio = this.fechaInicio,
        fechaFin = this.fechaFin,
        destino = this.destino
    )
}
