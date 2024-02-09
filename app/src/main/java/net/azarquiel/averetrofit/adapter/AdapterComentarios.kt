package net.azarquiel.averetrofit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.azarquiel.averetrofit.R
import net.azarquiel.averetrofit.model.Comentario
import net.azarquiel.averetrofit.model.Recurso
import net.azarquiel.averetrofit.model.Zona


/**
 * Created by pacopulido on 9/10/18.
 */
class AdapterComentarios(val context: Context,
                         val layout: Int
) : RecyclerView.Adapter<AdapterComentarios.ViewHolder>() {

    private var dataList: List<Comentario> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setComentarios(comentarios: List<Comentario>) {
        this.dataList = comentarios
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Comentario){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvusuariorowcomentario = itemView.findViewById<TextView>(R.id.tvusuariorowcomentario) as TextView
            val tvcomentariorowcomentario = itemView.findViewById<TextView>(R.id.tvcomentariorowcomentario) as TextView
            val tvfecharowcomentario = itemView.findViewById<TextView>(R.id.tvfecharowcomentario) as TextView

            tvusuariorowcomentario.text = dataItem.usuario.toString()
            tvfecharowcomentario.text = dataItem.fecha
            tvcomentariorowcomentario.text = dataItem.comentario

            itemView.tag = dataItem

        }

    }
}