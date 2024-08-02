package RicyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sofia.palacios.hospital_bloom.R

class ViewHolder (view: View): RecyclerView.ViewHolder(view){

    val txtNombreCard = view.findViewById<TextView>(R.id.txtNombreCard)
    val imgEditar: ImageView = view.findViewById(R.id.imgEditar)
    val imgBorrar: ImageView = view.findViewById(R.id.imgBorrar)

}