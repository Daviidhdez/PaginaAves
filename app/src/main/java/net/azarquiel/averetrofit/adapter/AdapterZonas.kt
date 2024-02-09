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
import net.azarquiel.averetrofit.model.Zona


/**
 * Created by pacopulido on 9/10/18.
 */
class AdapterZonas(val context: Context,
                   val layout: Int
                    ) : RecyclerView.Adapter<AdapterZonas.ViewHolder>() {

    private var dataList: List<Zona> = emptyList()

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

    internal fun setZonas(zonas: List<Zona>) {
        this.dataList = zonas
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Zona){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvrownombre = itemView.findViewById(R.id.tvrownombre) as TextView
            val tvrowlocalizacion = itemView.findViewById(R.id.tvrowlocalizacion) as TextView

            tvrownombre.text = dataItem.nombre
            tvrowlocalizacion.text = dataItem.localizacion

            itemView.tag = dataItem

        }

    }
}