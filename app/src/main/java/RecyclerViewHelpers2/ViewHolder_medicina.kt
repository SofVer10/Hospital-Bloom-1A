package RecyclerViewHelpers2

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sofia.palacios.hospital_bloom.R

class ViewHolder_medicina (view: View): RecyclerView.ViewHolder(view){

    // mando a llamar a los elementos de la card

    val txtcardMedicina = view.findViewById<TextView>(R.id.txtcardMedicina)
    val imgDelete = view.findViewById<ImageView>(R.id.imgDelete)
    val imgEdit = view.findViewById<ImageView>(R.id.imgEdit)


}