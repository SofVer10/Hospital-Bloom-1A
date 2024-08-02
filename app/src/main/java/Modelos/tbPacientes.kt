package Modelos

data class tbPacientes(
    val id_paciente: Int,
    var nombres: String,
    var apellidos: String,
    var enfermedad: String,
    var numero_habitacion: String,
    var numero_cama: String,
    var fecha_nacimiento: Int

)
