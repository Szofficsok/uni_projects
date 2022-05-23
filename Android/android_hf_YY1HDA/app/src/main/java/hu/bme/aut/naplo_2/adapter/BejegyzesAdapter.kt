package hu.bme.aut.naplo_2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.naplo_2.R
import hu.bme.aut.naplo_2.bejegyzesadat.BejegyzesItem
import hu.bme.aut.naplo_2.databinding.BejegyzesListBinding

class BejegyzesAdapter (private val listener: BejegyzesItemClickListener) :
    RecyclerView.Adapter<BejegyzesAdapter.BejegyzesViewHolder>() {

    private val items = mutableListOf<BejegyzesItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BejegyzesViewHolder(
        BejegyzesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: BejegyzesViewHolder, position: Int) {
        val bejegyzesItem = items[position]

        holder.binding.tvName.text = bejegyzesItem.name
        holder.binding.tvDescription.text = bejegyzesItem.description
        holder.binding.ivIcon.setImageResource(R.drawable.bejegyzes)
        holder.binding.ibRemove.setOnClickListener{
            listener.onItemRemoved(bejegyzesItem)
        }
        holder.binding.ibEdit.setOnClickListener{
            listener.onItemEdited(bejegyzesItem)
        }

    }
    fun addItem(item: BejegyzesItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(bejegyzesItems: List<BejegyzesItem>) {
        items.clear()
        items.addAll(bejegyzesItems)
        notifyDataSetChanged()
    }

    fun deleteItem(bejegyzesItem1: BejegyzesItem) {
        notifyItemRemoved(items.indexOf(bejegyzesItem1))
        items.remove(bejegyzesItem1)
    }

    override fun getItemCount(): Int = items.size

    interface BejegyzesItemClickListener {
        fun onItemChanged(item: BejegyzesItem)
        fun onItemRemoved(item: BejegyzesItem)
        fun onItemEdited(item: BejegyzesItem)
    }

    inner class BejegyzesViewHolder(val binding: BejegyzesListBinding) : RecyclerView.ViewHolder(binding.root)
}