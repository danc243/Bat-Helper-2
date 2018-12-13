package net.iplace.bat.login.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.iplace.bat.login.R
import net.iplace.iplacehelper.models.Login

/**
 * Created by ${DANavarro} on 06/12/2018.
 */

class RVAppListAdapter(val mcontext: Context) : RecyclerView.Adapter<RVAppListAdapter.ViewHolder>() {

    var array = ArrayList<Login.Aplicacion>()
    var onItemClick: ((Login.Aplicacion) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.rv_app_list_layout, parent, false));
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(array[position])
    }

    fun setElements(it: ArrayList<Login.Aplicacion>) {
        array = it
        reloadAll()
    }

    private fun reloadAll() {
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun setData(app: Login.Aplicacion) {

            itemView.setOnClickListener {
                onItemClick?.invoke(array[adapterPosition])
            }

            itemView.findViewById<TextView>(R.id.tv_rva_applist).let {
                it.text = app.nombre
            }
            //Todo poner imágenes.
        }
    }

    interface OnItemClickListener {
        fun onClick(o: Any)
    }
}