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
import net.azarquiel.averetrofit.model.Recurso
import net.azarquiel.averetrofit.model.Zona


/**
 * Created by pacopulido on 9/10/18.
 */
class AdapterRecursos(val context: Context,
                      val layout: Int
                    ) : RecyclerView.Adapter<AdapterRecursos.ViewHolder>() {

    private var dataList: List<Recurso> = emptyList()

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

    internal fun setRecursos(recursos: List<Recurso>) {
        this.dataList = recursos
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Recurso){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvrownombrerecurso = itemView.findViewById(R.id.tvrownombrerecurso) as TextView
            val ivfotorecurso = itemView.findViewById(R.id.ivfotorecurso) as ImageView

            tvrownombrerecurso.text = dataItem.titulo

            // foto de internet a traves de Picasso
            Picasso.get().load(dataItem.url).into(ivfotorecurso)

            itemView.tag = dataItem

        }

    }
}