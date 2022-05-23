package hu.bme.aut.naplo_2.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.naplo_2.R
import hu.bme.aut.naplo_2.databinding.DialogNewKepItemBinding
import hu.bme.aut.naplo_2.kepadat.KepItem

class NewKepItemDialogFragment(private val editItem: KepItem?) : DialogFragment() {
        interface NewKepItemDialogListener {
            fun onKepItemCreated(newItem: KepItem)
            fun onKepItemEdit(editItem: KepItem)
        }

        private lateinit var listener: NewKepItemDialogListener

        private lateinit var binding: DialogNewKepItemBinding

        override fun onAttach(context: Context) {
            super.onAttach(context)
            listener = context as? NewKepItemDialogListener
                ?: throw RuntimeException("Activity must implement the NewKepItemDialogListener interface!")
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            binding = DialogNewKepItemBinding.inflate(LayoutInflater.from(context))
            binding.etKep.adapter = ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                resources.getStringArray(R.array.kep_items)

            )
            if(editItem != null){
                binding.etName.setText(editItem.name)
                binding.etDescription.setText(editItem.description)
            }
            return AlertDialog.Builder(requireContext())
                .setTitle(R.string.new_kep_item)
                .setView(binding.root)
                .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                    if (isValid()) {
                        if(editItem == null) {
                            listener.onKepItemCreated(getKepItem())
                        }
                        else{
                            val item = getKepItem()
                            editItem.name = item.name
                            editItem.description = item.description
                            editItem.kepek = item.kepek
                            listener.onKepItemEdit(editItem)
                        }
                    }
                }
                .setNegativeButton(R.string.button_cancel, null)
                .create()
        }

        private fun isValid() = binding.etDescription.text.isNotEmpty()

        private fun getKepItem() = KepItem(
            name = binding.etName.text.toString(),
            description = binding.etDescription.text.toString(),
            kepek = KepItem.Kepek.getByOrdinal(binding.etKep.selectedItemPosition) ?: KepItem.Kepek.ALLAT
        )

        companion object {
            const val TAG = "NewKepItemDialogFragment"
        }
    }