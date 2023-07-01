package com.example.parcial2camilosilva

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JokesAdapter (val categories: List<String>): RecyclerView.Adapter<JokesAdapter.ViewHolder>() {

    private var onItemClick: ((String) -> Unit)? = null
    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClick = listener}
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val tvCat: TextView = view.findViewById(R.id.textViewCategory)

        fun bind(categoria: String) {
            tvCat.text = categoria

            itemView.setOnClickListener{
                onItemClick?.invoke(categoria)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = categories[position]
        holder.bind(image)
    }
}