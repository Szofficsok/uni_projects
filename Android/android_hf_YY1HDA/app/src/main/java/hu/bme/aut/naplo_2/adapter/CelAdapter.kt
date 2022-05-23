package hu.bme.aut.naplo_2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.naplo_2.R
import hu.bme.aut.naplo_2.adat.CelItem
import hu.bme.aut.naplo_2.bejegyzesadat.BejegyzesItem
import hu.bme.aut.naplo_2.databinding.CelListBinding

class CelAdapter(private val listener: CelItemClickListener) :
    RecyclerView.Adapter<CelAdapter.CelViewHolder>() {

    private val items = mutableListOf<CelItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CelViewHolder(
        CelListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CelViewHolder, position: Int) {
        val celItem = items[position]

        holder.binding.cbIsBought.isChecked = celItem.isBought
        holder.binding.cbIsBought.text = celItem.name
        holder.binding.tvDescription.text = celItem.description
        holder.binding.ivIcon.setImageResource(R.drawable.celok)
        holder.binding.cbIsBought.setOnCheckedChangeListener { buttonView, isChecked ->
            celItem.isBought = isChecked
            listener.onItemChanged(celItem)
        }
        holder.binding.ibRemove.setOnClickListener{
            listener.onItemRemoved(celItem)
        }
        holder.binding.ibEdit.setOnClickListener{
            listener.onItemEdited(celItem)
        }

    }
    fun addItem(item: CelItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(celItems: List<CelItem>) {
        items.clear()
        items.addAll(celItems)
        notifyDataSetChanged()
    }
    fun deleteItem(celItem1: CelItem) {
        notifyItemRemoved(items.indexOf(celItem1))
        items.remove(celItem1)
    }
    override fun getItemCount(): Int = items.size

    interface CelItemClickListener {
        fun onItemChanged(item: CelItem)
        fun onItemRemoved(item: CelItem)
            fun onItemEdited(item: CelItem)
    }

    inner class CelViewHolder(val binding: CelListBinding) : RecyclerView.ViewHolder(binding.root)
}