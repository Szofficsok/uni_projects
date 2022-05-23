package hu.bme.aut.naplo_2.fragment
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.naplo_2.R
import hu.bme.aut.naplo_2.adat.CelItem
import hu.bme.aut.naplo_2.bejegyzesadat.BejegyzesItem
import hu.bme.aut.naplo_2.databinding.DialogNewCelItemBinding

class NewCelItemDialogFragment(private val editItem: CelItem?) : DialogFragment() {
    interface NewCelItemDialogListener {
        fun onCelItemCreated(newItem: CelItem)
        fun onCelItemEdit(editItem: CelItem)
    }

    private lateinit var listener: NewCelItemDialogListener

    private lateinit var binding: DialogNewCelItemBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewCelItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewCelItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewCelItemBinding.inflate(LayoutInflater.from(context))

        if(editItem != null){
            binding.etDescription.setText(editItem.description)
            binding.etName.setText(editItem.name)
            binding.cbAlreadyPurchased.setSelectAllOnFocus(editItem.isBought)
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_cel_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    if(editItem == null) {
                        listener.onCelItemCreated(getCelItem())
                    }
                    else{
                        val item = getCelItem()
                        editItem.description = item.description
                        editItem.name = item.name
                        editItem.isBought = item.isBought
                        listener.onCelItemEdit(editItem)
                    }
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    private fun isValid() = binding.etName.text.isNotEmpty()

    private fun getCelItem() = CelItem(
        name = binding.etName.text.toString(),
        description = binding.etDescription.text.toString(),
        isBought = binding.cbAlreadyPurchased.isChecked
    )

    companion object {
        const val TAG = "NewCelItemDialogFragment"
    }
}