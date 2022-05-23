package hu.bme.aut.naplo_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.naplo_2.adapter.BejegyzesAdapter
import hu.bme.aut.naplo_2.bejegyzesadat.BejegyzesItem
import hu.bme.aut.naplo_2.bejegyzesadat.BejegyzesListDatabase
import hu.bme.aut.naplo_2.databinding.ActivityBejegyzesAdatokBinding
import hu.bme.aut.naplo_2.fragment.NewBejegyzesItemDialogFragment
import kotlin.concurrent.thread

class BejegyzesAdatokActivity : AppCompatActivity(), BejegyzesAdapter.BejegyzesItemClickListener,  NewBejegyzesItemDialogFragment.NewBejegyzesItemDialogListener{
    private lateinit var binding: ActivityBejegyzesAdatokBinding

    private lateinit var database: BejegyzesListDatabase
    private lateinit var adapter: BejegyzesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBejegyzesAdatokBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = BejegyzesListDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener{
           ShowEditDialog()
        }
        initScrollListener()
        initRecyclerView()
    }
    private fun initRecyclerView() {
        adapter = BejegyzesAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.bejegyzesItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }
    override fun onItemChanged(item: BejegyzesItem) {
        thread {
            database.bejegyzesItemDao().update(item)
            Log.d("BejegyzesAdatokActivity", "BejegyzesItem update was successful")
        }
    }
    override fun onBejegyzesItemCreated(newItem: BejegyzesItem) {
        thread {
            val insertId = database.bejegyzesItemDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }

    override fun onBejegyzesItemEdit(editItem: BejegyzesItem) {
        thread {
            database.bejegyzesItemDao().update(editItem)
            val item = database.bejegyzesItemDao().getAll()
            runOnUiThread {
                adapter.update(item)
            }

        }
    }

    override fun onItemRemoved(delItem: BejegyzesItem) {
        thread {
            database.bejegyzesItemDao().deleteItem(delItem)
            runOnUiThread {
                adapter.deleteItem(delItem)
            }
        }

    }

    override fun onItemEdited(item: BejegyzesItem) {
        ShowEditDialog(item)
    }
    private fun ShowEditDialog(editedItem: BejegyzesItem? = null){
        NewBejegyzesItemDialogFragment(editedItem).show(supportFragmentManager, NewBejegyzesItemDialogFragment.TAG)
    }
    private fun initScrollListener() {
        binding.rvMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy > 0) {
                    binding.fab.hide()
                } else {
                    binding.fab.show()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

}

