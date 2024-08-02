package sofia.palacios.hospital_bloom

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class detalle_paciente : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_paciente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //Recibir los valores
        val txtNombreRecibido = intent.getStringExtra("nombres")
        val txtApellidoRecibido = intent.getStringExtra("apellidos")
        val txtEnfermedadRecibido = intent.getStringExtra("enfermedad")
        val txtNumHabitacionRecibido = intent.getStringExtra("numero_habitacion")
        val txtNumCamaRecibido = intent.getStringExtra("numero_cama")
        val txtNacimientoRecibido = intent.getIntExtra("fecha_nacimiento", 0)


        //llamo los elementos
        val txtNombrePaciente = findViewById<TextView>(R.id.txtNombrePaciente)
        val txtApellidoPaciente = findViewById<TextView>(R.id.txtApellidoPaciente)
        val txtEnfermedadPaciente = findViewById<TextView>(R.id.txtEnfermedadPaciente)
        val txtNumHabitacionPaciente = findViewById<TextView>(R.id.txtNumHabitacionPaciente)
        val txtNumCamaPaciente = findViewById<TextView>(R.id.txtNumCamaPaciente)
        val txtNacimientoPaciente = findViewById<TextView>(R.id.txtNacimientoPaciente)
        val btnRegresar = findViewById<Button>(R.id.btnVolver)

        //Asigarle los datos recibidos a mis TextView
        txtNombrePaciente.text = txtNombreRecibido
        txtApellidoPaciente.text = txtApellidoRecibido
        txtEnfermedadPaciente.text = txtEnfermedadRecibido
        txtNumHabitacionPaciente.text = txtNumHabitacionRecibido
        txtNumCamaPaciente.text = txtNumCamaRecibido
        txtNacimientoPaciente.text = txtNacimientoRecibido.toString()


        btnRegresar.setOnClickListener{

            GlobalScope.launch (Dispatchers.Main){
                //Tiempo de cambio 3 segundos
                delay(1000)

                //Cambio de pantalla
                val pantallaSiguiente = Intent(this@detalle_paciente, pacientes::class.java)
                startActivity(pantallaSiguiente)
                //Final del activity
                finish()
            }
        }

    }
}