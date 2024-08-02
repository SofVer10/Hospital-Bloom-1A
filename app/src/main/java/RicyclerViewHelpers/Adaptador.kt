package RicyclerViewHelpers

import Modelos.Conexion
import Modelos.tbPacientes
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sofia.palacios.hospital_bloom.R

class Adaptador(var Datos:  List<tbPacientes>): RecyclerView.Adapter<ViewHolder>() {

    fun actualizarLista(nuevaLista: List<tbPacientes>) {
        Datos = nuevaLista
        notifyDataSetChanged() // Notificar al adaptador sobre los cambios
    }

    fun actualicePantalla(idPaciente: Int, nuevoNombre: String) {
        // Encuentra el índice del paciente con el id_paciente dado
        val index = Datos.indexOfFirst { it.id_paciente == idPaciente }

        if (index != -1) {  // Verifica si se encontró el paciente
            // Actualiza el nombre del paciente en el índice encontrado
            Datos[index].nombres = nuevoNombre

            // Notifica que los datos han cambiado para actualizar la vista (si estás usando un RecyclerView, por ejemplo)
            notifyDataSetChanged()
        } else {
            // Opcional: Maneja el caso donde el id_paciente no se encuentra en la lista
            println("Paciente con id $idPaciente no encontrado.")
        }
    }

    /////////////////// TODO: Eliminar datos
    fun eliminarDatos(nombres: String, posicion: Int){
        //Actualizo la lista de datos y notifico al adaptador
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO){
            //1- Creamos un objeto de la clase conexion
            val objConexion = Conexion().cadenaConexion()

            //2- Crear una variable que contenga un PrepareStatement
            val deletetbTickets = objConexion?.prepareStatement("delete from Pacientes where nombres = ?")!!
            deletetbTickets.setString(1, nombres)
            deletetbTickets.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        Datos = listaDatos.toList()
        // Notificar al adaptador sobre los cambios
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }

    //////////////////////TODO: Editar datos
    fun actualizarDato(nombres: String, id_paciente: Int){
        GlobalScope.launch(Dispatchers.IO){

            //1- Creo un objeto de la clase de conexion
            val objConexion = Conexion().cadenaConexion()

            //2- creo una variable que contenga un PrepareStatement
            val updatetbPacientes = objConexion?.prepareStatement("update Pacientes set nombres = ? where id_paciente = ?")!!
            updatetbPacientes.setString(1, nombres)
            updatetbPacientes.setInt(2, id_paciente)
            updatetbPacientes.executeUpdate()

            withContext(Dispatchers.Main){
                actualicePantalla(id_paciente, nombres)
            }

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return RicyclerViewHelpers.ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = Datos[position]
        holder.itemView.findViewById<TextView>(R.id.txtNombreCard).text = item.nombres


        //todo: clic al icono de eliminar
        holder.itemView.findViewById<ImageView>(R.id.imgBorrar).setOnClickListener{

            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Desea eliminar al Paciente?")

            //Botones
            builder.setPositiveButton("Si") { dialog, which ->
                eliminarDatos(item.nombres, position)
            }

            builder.setNegativeButton("No"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

        }

        //Todo: icono de editar
        holder.itemView.findViewById<ImageView>(R.id.imgEditar).setOnClickListener{

            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Actualizar")
            builder.setMessage("¿Desea actualizar el Ticket?")

            //Agregarle un cuadro de texto para
            //que el usuario escriba el nuevo nombre
            val cuadroTexto = EditText(context)
            cuadroTexto.setHint(item.nombres)
            builder.setView(cuadroTexto)

            //Botones
            builder.setPositiveButton("Actualizar") { dialog, which ->
                actualizarDato(cuadroTexto.text.toString(), item.id_paciente)
            }

            builder.setNegativeButton("Cancelar"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }

}