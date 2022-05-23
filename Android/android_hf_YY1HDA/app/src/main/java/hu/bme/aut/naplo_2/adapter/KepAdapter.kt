package hu.bme.aut.naplo_2.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.naplo_2.BejegyzesAdatokActivity
import hu.bme.aut.naplo_2.KepLeirasActivity
import hu.bme.aut.naplo_2.R
import hu.bme.aut.naplo_2.bejegyzesadat.BejegyzesItem
import hu.bme.aut.naplo_2.databinding.KepListBinding
import hu.bme.aut.naplo_2.kepadat.KepItem

class KepAdapter(private val listener: KepItemClickListener) :
    RecyclerView.Adapter<KepAdapter.KepViewHolder>() {

    private val items = mutableListOf<KepItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = KepViewHolder(
        KepListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: KepViewHolder, position: Int) {
        val kepItem = items[position]

        holder.binding.tvName.text = kepItem.name
        listener.onItemChanged(kepItem)
        holder.binding.ibRemove.setOnClickListener{
            listener.onItemRemoved(kepItem)
        }
        holder.binding.btnKep.setImageResource(getImageResource(kepItem.kepek))
        holder.binding.ibEdit.setOnClickListener{
            listener.onItemEdited(kepItem)
        }
        holder.binding.btnKep.setOnClickListener {
            listener.onKepSelected(kepItem.description)
        }

    }
    fun addItem(item: KepItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(kepItems: List<KepItem>) {
        items.clear()
        items.addAll(kepItems)
        notifyDataSetChanged()
    }
    fun deleteItem(kepItem1: KepItem) {
        notifyItemRemoved(items.indexOf(kepItem1))
        items.remove(kepItem1)
    }
    override fun getItemCount(): Int = items.size

    interface KepItemClickListener {
        fun onItemChanged(item: KepItem)
        fun onItemRemoved(item: KepItem)
        fun onItemEdited(item: KepItem)
        fun onKepSelected(kepleiras: String?)
    }

    inner class KepViewHolder(val binding: KepListBinding) : RecyclerView.ViewHolder(binding.root)
    @DrawableRes()
    private fun getImageResource(kepek: KepItem.Kepek): Int {
       return when (kepek){
           KepItem.Kepek.ALLAT -> R.drawable.allat
           KepItem.Kepek.BARATOK -> R.drawable.baratok
           KepItem.Kepek.CSALAD -> R.drawable.csalad
           KepItem.Kepek.ETEL -> R.drawable.etel
           KepItem.Kepek.FILM -> R.drawable.film
           KepItem.Kepek.SPORT -> R.drawable.sport
           KepItem.Kepek.TANULAS -> R.drawable.tanulas
           KepItem.Kepek.ZENE -> R.drawable.zene
       }

    }
}