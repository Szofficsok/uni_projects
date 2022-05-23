package hu.bme.aut.naplo_2
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.naplo_2.adapter.CelAdapter
import hu.bme.aut.naplo_2.adat.CelItem
import hu.bme.aut.naplo_2.adat.CelListDatabase
import hu.bme.aut.naplo_2.bejegyzesadat.BejegyzesItem
import hu.bme.aut.naplo_2.databinding.ActivityAdatokBinding
import hu.bme.aut.naplo_2.fragment.NewBejegyzesItemDialogFragment
import hu.bme.aut.naplo_2.fragment.NewCelItemDialogFragment
import kotlin.concurrent.thread

class AdatokActivity : AppCompatActivity(), CelAdapter.CelItemClickListener,  NewCelItemDialogFragment.NewCelItemDialogListener{
    private lateinit var binding: ActivityAdatokBinding

    private lateinit var database: CelListDatabase
    private lateinit var adapter: CelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdatokBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setSupportActionBar(binding.toolbar)

        database = CelListDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener{
            ShowEditDialog()
        }
        initScrollListener()
        initRecyclerView()
    }
    private fun initRecyclerView() {
        adapter = CelAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.celItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }
    override fun onItemChanged(item: CelItem) {
        thread {
            database.celItemDao().update(item)
            Log.d("AdatokActivity", "CelItem update was successful")
        }
    }
    override fun onCelItemCreated(newItem: CelItem) {
        thread {
            val insertId = database.celItemDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }

    override fun onCelItemEdit(editItem: CelItem) {
        thread {
            database.celItemDao().update(editItem)
            val item = database.celItemDao().getAll()
            runOnUiThread {
                adapter.update(item)
            }

        }
    }

    override fun onItemRemoved(delItem: CelItem) {
        thread {
            database.celItemDao().deleteItem(delItem)
            runOnUiThread {
                adapter.deleteItem(delItem)
            }
        }

    }
    override fun onItemEdited(item: CelItem) {
        ShowEditDialog(item)
    }
    private fun ShowEditDialog(editedItem: CelItem? = null){
        NewCelItemDialogFragment(editedItem).show(supportFragmentManager, NewCelItemDialogFragment.TAG)
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