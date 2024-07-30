package sofia.palacios.hospital_bloom

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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


        imvPacientes.setOnClickListener {

            val pantallaSiguiente = Intent(this@pacientes, pacientes::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

        imvMedicina.setOnClickListener {

            val pantallaSiguiente = Intent(this@pacientes, medicina::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }


        imvsuministro.setOnClickListener {

            val pantallaSiguiente = Intent(this@pacientes, suministro1::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }
    }
}