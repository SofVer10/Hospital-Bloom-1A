package sofia.palacios.hospital_bloom

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class suministro1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_suministro1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imvPacientes = findViewById<ImageView>(R.id.imvPacientes)
        val imvMedicina = findViewById<ImageView>(R.id.imvMedicina)
        val imvSuministro = findViewById<ImageView>(R.id.imvSuministro)

        imvPacientes.setOnClickListener {

            val pantallaSiguiente = Intent(this@suministro1, pacientes::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

        imvMedicina.setOnClickListener {

            val pantallaSiguiente = Intent(this@suministro1, medicina::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

        imvSuministro.setOnClickListener {

            val pantallaSiguiente = Intent(this@suministro1, suministro1::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }
    }
}