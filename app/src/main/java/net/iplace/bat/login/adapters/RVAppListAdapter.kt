package net.iplace.bat.login.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import net.iplace.bat.login.R

/**
 * Created by ${DANavarro} on 06/12/2018.
 */

class RVAppListAdapter(val mcontext: Context, val listener: RVAppListAdapter.onItemClickListener) : RecyclerView.Adapter<RVAppListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.rv_app_list_layout, parent, false));
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun setData() {

        }
    }

    interface onItemClickListener {
        fun onClick(o: Any)
    }
}