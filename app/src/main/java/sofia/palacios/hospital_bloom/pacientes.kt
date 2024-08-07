package sofia.palacios.hospital_bloom

import Modelos.Conexion
import Modelos.tbPacientes
import RicyclerViewHelpers.Adaptador
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class pacientes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pacientes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val imvPacientes = findViewById<ImageView>(R.id.imvPacientes)
        val imvMedicina = findViewById<ImageView>(R.id.imvMedicina)
        val imvsuministro = findViewById<ImageView>(R.id.imvSuministro)
        val txtNacimiento = findViewById<TextView>(R.id.txtNacimiento)
        val txtEnfermedad = findViewById<TextView>(R.id.txtEnfermedad)
        val txtNombre = findViewById<TextView>(R.id.txtNombre)
        val txtApellido = findViewById<TextView>(R.id.txtApellido)
        val txtNumCama = findViewById<TextView>(R.id.txtNumCama)
        val txtNumHabitacion = findViewById<TextView>(R.id.txtNumHabitacion)
        val txtMedicinaM = findViewById<TextView>(R.id.txtMedicinaM)
        val txtPacientesM = findViewById<TextView>(R.id.txtPacientesM)
        val txtSuministroM = findViewById<TextView>(R.id.txtSuministroM)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val rcvPacientes = findViewById<RecyclerView>(R.id.rcvPacientes)

        rcvPacientes.layoutManager = LinearLayoutManager(this)

        ///////////// TODO: Mostrar Datos

        fun obtenerPacientes(): List<tbPacientes> {
            val objConexion = Conexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("SELECT * FROM Pacientes")!!

            val listaPacientes = mutableListOf<tbPacientes>()

            while (resulSet.next()) {
                val id_paciente = resulSet.getInt("id_paciente")
                val nombres = resulSet.getString("nombres")
                val apellidos = resulSet.getString("apellidos")
                val enfermedad = resulSet.getString("enfermedad")
                val numero_habitacion = resulSet.getString("numero_habitacion")
                val numero_cama = resulSet.getString("numero_cama")
                val fecha_nacimiento = resulSet.getInt("fecha_nacimiento")

                val valoresJuntos = tbPacientes(id_paciente, nombres, apellidos, enfermedad, numero_habitacion, numero_cama, fecha_nacimiento)

                listaPacientes.add(valoresJuntos)
            }
            return listaPacientes
        }


        CoroutineScope(Dispatchers.IO).launch {
            val pacientesDB = obtenerPacientes()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(pacientesDB)
                rcvPacientes.adapter = adapter
            }
        }

        btnGuardar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                val objConexion = Conexion().cadenaConexion()

              //  if (objConexion == null) {
               //     Log.e("DatabaseError", "objConexion is null")
               // } else {
                    val addPaciente = objConexion?.prepareStatement("INSERT INTO Pacientes (nombres, apellidos, enfermedad, numero_habitacion, numero_cama, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?)")!!
                    addPaciente.setString(1, txtNombre.text.toString())
                    addPaciente.setString(2, txtApellido.text.toString())
                    addPaciente.setString(3, txtEnfermedad.text.toString())
                    addPaciente.setString(4, txtNumHabitacion.text.toString())
                    addPaciente.setString(5, txtNumCama.text.toString())
                    addPaciente.setInt(6, txtNacimiento.text.toString().toInt())


                    addPaciente.executeUpdate()
               // }
            }
        }




        imvPacientes.setOnClickListener {

            val pantallaSiguiente = Intent(this@pacientes, pacientes::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

        txtPacientesM.setOnClickListener {

            val pantallaSiguiente = Intent(this@pacientes, pacientes::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

        imvMedicina.setOnClickListener {

            val pantallaSiguiente = Intent(this@pacientes, medicina::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

        txtMedicinaM.setOnClickListener {

            val pantallaSiguiente = Intent(this@pacientes, medicina::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

        imvsuministro.setOnClickListener {

            val pantallaSiguiente = Intent(this@pacientes, suministro1::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

        txtSuministroM.setOnClickListener {

            val pantallaSiguiente = Intent(this@pacientes, suministro1::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }
    }
}