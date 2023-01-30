package com.example.banderassqlite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class BanderaAdapter(
    var context: Context?,
    var textViewResourceId: Int,
    var elementos: MutableList<Bandera>?
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var vista = convertView
        val holder: ViewHolder
        if (vista == null) {
            val vi = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            vista = vi.inflate(R.layout.banderas, null)
            holder = ViewHolder()
            holder.texto = vista.findViewById<View>(R.id.nombreComunidad) as TextView
            holder.foto = vista.findViewById<View>(R.id.imagenComunidad) as ImageView
            vista.tag = holder
        } else {
            holder = vista.tag as ViewHolder
        }
        val bandera = elementos!![position]

        if (bandera != null) {
            holder.texto.text = bandera.nombre
            holder.foto.setImageDrawable(context!!.getDrawable(bandera.imagen))
        }
        return vista
    }

    override fun getCount(): Int {
        return elementos!!.size
    }

    override fun getItem(position: Int): Bandera {
        return elementos!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    internal class ViewHolder {
        lateinit var foto: ImageView
        lateinit var texto: TextView
    }
}