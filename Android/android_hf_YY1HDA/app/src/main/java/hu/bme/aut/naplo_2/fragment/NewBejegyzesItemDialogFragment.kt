package hu.bme.aut.naplo_2.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.naplo_2.R
import hu.bme.aut.naplo_2.bejegyzesadat.BejegyzesItem
import hu.bme.aut.naplo_2.databinding.DialogNewBejegyzesItemBinding
import java.util.*

class NewBejegyzesItemDialogFragment(private val editItem: BejegyzesItem?) : DialogFragment() {
    interface NewBejegyzesItemDialogListener {
        fun onBejegyzesItemCreated(newItem: BejegyzesItem)
        fun onBejegyzesItemEdit(editItem: BejegyzesItem)
    }

    private lateinit var listener: NewBejegyzesItemDialogListener

    private lateinit var binding: DialogNewBejegyzesItemBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewBejegyzesItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewBejegyzesItemDialogListener interface!")
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewBejegyzesItemBinding.inflate(LayoutInflater.from(context))

        if(editItem != null){
            binding.etDescription.setText(editItem.description)
            binding.etName.setText(editItem.name)
        }
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_bejegyzes_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    if(editItem == null) {
                        listener.onBejegyzesItemCreated(getBejegyzesItem())
                    }
                    else{
                        val item = getBejegyzesItem()
                        editItem.description = item.description
                        editItem.name = item.name
                        listener.onBejegyzesItemEdit(editItem)
                    }
                }

            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    private fun isValid() = binding.etName.text.isNotEmpty()

    private fun getBejegyzesItem() = BejegyzesItem(
        name = binding.etName.text.toString(),
        description = binding.etDescription.text.toString(),
    )
    companion object {
        const val TAG = "NewBejegyzesItemDialogFragment"
    }

}
