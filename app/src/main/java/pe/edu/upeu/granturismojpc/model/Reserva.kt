package pe.edu.upeu.granturismojpc.model



data class ReservaDto(
    var idReserva: Long = 0L,
    var fechaInicio: String,
    var fechaFin: String,
    var estado: String,
    var cantidadPersonas: Int,
    var observaciones: String?,
    var usuario: Long,
    var paquete: Long,
)


data class ReservaCreateDto(
    var fechaInicio: String,
    var fechaFin: String,
    var estado: String,
    var cantidadPersonas: Int,
    var observaciones: String?,
    var usuario: Long,
    var paquete: Long?,
)


data class ReservaResp(
    val idReserva: Long,
    val cantidadPersonas: Int,
    val fechaInicio: String,
    val fechaFin: String,
    val estado: String,
    val observaciones: String?,
    val usuario: UsuarioResp?,
    val paquete: PaqueteResp?,
)


fun ReservaResp.toDto(): ReservaDto {
    return ReservaDto(
        idReserva = this.idReserva,
        cantidadPersonas = this.cantidadPersonas,
        fechaInicio = this.fechaInicio,
        fechaFin = this.fechaFin,
        estado = this.estado,
        observaciones = this.observaciones,
        usuario = this.usuario?.idUsuario ?: 0L,
        paquete = this.paquete?.idPaquete ?: 0L
    )
}


fun ReservaDto.toCreateDto(): ReservaCreateDto {
    return ReservaCreateDto(
        fechaInicio = this.fechaInicio,
        fechaFin = this.fechaFin,
        estado = this.estado,
        cantidadPersonas = this.cantidadPersonas,
        observaciones = this.observaciones,
        usuario = this.usuario,
        paquete = this.paquete
    )
}
