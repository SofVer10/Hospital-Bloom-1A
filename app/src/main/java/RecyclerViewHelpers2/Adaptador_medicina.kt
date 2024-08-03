package RecyclerViewHelpers2

import Modelos.Conexion
import Modelos.tbMedicamentos
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sofia.palacios.hospital_bloom.R

class Adaptador_medicina(var Datos: List<tbMedicamentos>): RecyclerView.Adapter<ViewHolder>() {

    fun actualizarRecyclerView(nuevaLista: List<tbMedicamentos>){
        Datos = nuevaLista
        notifyDataSetChanged()
    //Notifica que hay datos nuevos
    }
    fun actualicePantalla(idPaciente: Int, nuevoNombre: String) {
        // Encuentra el índice del paciente con el id_paciente dado
        val index = Datos.indexOfFirst { it.id_medicamento == idPaciente }

        if (index != -1) {  // Verifica si se encontró el paciente
            // Actualiza el nombre del paciente en el índice encontrado
            Datos[index].nomnbre_medicamento = nuevoNombre

            // Notifica que los datos han cambiado para actualizar la vista (si estás usando un RecyclerView, por ejemplo)
            notifyDataSetChanged()
        } else {
            // Opcional: Maneja el caso donde el id_paciente no se encuentra en la lista
            println("Paciente con id $idPaciente no encontrado.")
        }
    }
    //1- Crear la funcion de eliminar
    fun eliminarRegistro(nombre_medicamento: String, posicion: Int){
        //Notificar al adaptador
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        //Quitar de la base de datos
        GlobalScope.launch(Dispatchers.IO){
            //Dos pasos para eliminar de la base de datos

            //1- Crear un objeto de la clase conexion
            val objConexion = Conexion().cadenaConexion()

            //2- Creo una variable que contenga un PrepareStatement
            val deleteMedicamento = objConexion?.prepareStatement("delete Medicamento where nombre_medicamento = ?")!!
            deleteMedicamento.setString(1,nombre_medicamento )
            deleteMedicamento.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()
        }

        //Notificamos el cambio para que refresque la lista
        Datos = listaDatos.toList()
        //Quito los datos de la lista
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }


    fun actualizarListadoDespuesDeEditar(nombre_medicamento: String , id_medicamento: Int){
        //Obtener el ID
        //-Creo una corrutina
        GlobalScope.launch(Dispatchers.IO){
            //1- Creo un objeto de la clase conexion
            val objConexion = Conexion().cadenaConexion()

            //2- Creo una variable que contenga un PrepareStatement
            val updateMedicamento = objConexion?.prepareStatement("update Medicamentos set nombre_medicamento = ? where id_medicamento = ?")!!
            updateMedicamento.setString(1, nombre_medicamento)
            updateMedicamento.setInt(2, id_medicamento)
            updateMedicamento.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()
        }
    }


    //Creamos la funcion de editar o actualizar en la base de datos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card_medicina, parent, false)
        return ViewHolder_medicina(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Datos[position]
        holder.itemView.findViewById<TextView>(R.id.txtcardMedicina).text=item.nomnbre_medicamento

        //Darle clic al icono de borrar
           holder.itemView.findViewById<ImageView>(R.id.imgDelete).setOnClickListener{
                //Creamos un Alert Dialog
                val context = holder.itemView.context
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Eliminar")
                builder.setMessage("¿Desea eliminar al Paciente?")
                //Botones
                builder.setPositiveButton("Si") { dialog, which ->
                    eliminarRegistro(item.nomnbre_medicamento, position)
                }
                builder.setNegativeButton("No"){dialog, which ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }

        //Todo: icono de editar
        holder.itemView.findViewById<ImageView>(R.id.imgEdit).setOnClickListener{

            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Actualizar")
            builder.setMessage("¿Desea actualizar el Ticket?")

            //Agregarle un cuadro de texto para
            //que el usuario escriba el nuevo nombre
            val cuadroTexto = EditText(context)
            cuadroTexto.setHint(item.nomnbre_medicamento)
            builder.setView(cuadroTexto)

            //Botones
            builder.setPositiveButton("Actualizar") { dialog, which ->
                actualizarListadoDespuesDeEditar(cuadroTexto.text.toString(),
                    item.id_medicamento
                )
            }

            builder.setNegativeButton("Cancelar"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }


}












