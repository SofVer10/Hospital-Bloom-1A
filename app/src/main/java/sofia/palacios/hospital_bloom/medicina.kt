package sofia.palacios.hospital_bloom

import Modelos.Conexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class medicina : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_medicina)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imvPacientes = findViewById<ImageView>(R.id.imvPacientes)
        val imvMedicina = findViewById<ImageView>(R.id.imvMedicina)
        val imvSuministro = findViewById<ImageView>(R.id.imvSuministro)
        val txtMedicamento = findViewById<EditText>(R.id.txtMedicamento)
        val btnGuadarM = findViewById<Button>(R.id.btnGuardarM)




        // programe el boton de guadar medicamentos


        btnGuadarM.setOnClickListener{

          CoroutineScope(Dispatchers.IO).launch {

              // cree el objeto de clase conexion

              val  objConexion = Conexion().cadenaConexion()

              // cree la variable que tiene el prePARE STATEMENT

              val addMedicamentos = objConexion?.prepareStatement("insert into Medicamentos (nombre_medicamento) values(?)")!!
              addMedicamentos.setString(1,txtMedicamento.text.toString())
              addMedicamentos.executeUpdate()
          }

        }


        imvPacientes.setOnClickListener {

            val pantallaSiguiente = Intent(this@medicina, pacientes::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

        imvMedicina.setOnClickListener {

            val pantallaSiguiente = Intent(this@medicina, medicina::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

        imvSuministro.setOnClickListener {

            val pantallaSiguiente = Intent(this@medicina, suministro1::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

    }
}