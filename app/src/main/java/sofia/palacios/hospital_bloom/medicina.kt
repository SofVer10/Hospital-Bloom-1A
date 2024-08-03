package sofia.palacios.hospital_bloom

import Modelos.Conexion
import Modelos.tbMedicamentos
import RecyclerViewHelpers2.Adaptador_medicina
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        val txtMedicinaM = findViewById<TextView>(R.id.txtMedicinaM)
        val txtPacientesM = findViewById<TextView>(R.id.txtPacientesM)
        val txtSuministroM = findViewById<TextView>(R.id.txtSuministroM)
        val btnGuadar = findViewById<Button>(R.id.btnGuardar)
        val rvcMedicamentosblom = findViewById<RecyclerView>(R.id.rvcMedicamentosblom)

        // agrego un layout al recyclerView

        rvcMedicamentosblom.layoutManager= LinearLayoutManager(this )

        fun ObtenerMedicamentos(): List<tbMedicamentos>{
            //// 1- creo un objeto de clase conexion
            val objConexion = Conexion().cadenaConexion()

            /// 2- creo un Statement
            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("SELECT * FROM Medicamentos")!!

            val ListaMedicamentos = mutableListOf<tbMedicamentos>()

            while (resultSet.next()){
                val id_medicamento = resultSet.getInt("id_medicamento")

                val nombre_medicamento = resultSet.getString("nombre_medicamento")

                val todoslosvalores = tbMedicamentos(id_medicamento,nombre_medicamento)

                ListaMedicamentos.add(todoslosvalores)

            }

            return ListaMedicamentos
        }

        /// asignarle el adaptador al recyclerView

        CoroutineScope(Dispatchers.IO).launch {

            val tbMedicamentos = ObtenerMedicamentos()
            withContext(Dispatchers.Main){
                val adapter = Adaptador_medicina(tbMedicamentos)
                rvcMedicamentosblom.adapter = adapter
            }
        }











        // programe el boton de guadar medicamentos


        btnGuadar.setOnClickListener{

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

        txtPacientesM.setOnClickListener {

            val pantallaSiguiente = Intent(this@medicina, pacientes::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

        imvMedicina.setOnClickListener {

            val pantallaSiguiente = Intent(this@medicina, medicina::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

        txtMedicinaM.setOnClickListener {

            val pantallaSiguiente = Intent(this@medicina, medicina::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

        imvSuministro.setOnClickListener {

            val pantallaSiguiente = Intent(this@medicina, suministro1::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

        txtSuministroM.setOnClickListener {

            val pantallaSiguiente = Intent(this@medicina, suministro1::class.java)
            startActivity(pantallaSiguiente)

            finish()
        }

    }
}