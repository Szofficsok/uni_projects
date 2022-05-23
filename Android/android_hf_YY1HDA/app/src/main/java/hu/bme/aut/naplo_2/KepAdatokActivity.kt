package hu.bme.aut.naplo_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.naplo_2.adapter.KepAdapter
import hu.bme.aut.naplo_2.databinding.ActivityKepAdatokBinding
import hu.bme.aut.naplo_2.fragment.NewKepItemDialogFragment
import hu.bme.aut.naplo_2.kepadat.KepItem
import hu.bme.aut.naplo_2.kepadat.KepListDatabase
import kotlin.concurrent.thread

class KepAdatokActivity : AppCompatActivity(), KepAdapter.KepItemClickListener,  NewKepItemDialogFragment.NewKepItemDialogListener{
    private lateinit var binding: ActivityKepAdatokBinding

    private lateinit var database: KepListDatabase
    private lateinit var adapter: KepAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKepAdatokBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = KepListDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener{
            ShowEditDialog()
        }
        initScrollListener()
        initRecyclerView()
    }
    private fun initRecyclerView() {
        adapter = KepAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.kepItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }
    override fun onItemChanged(item: KepItem) {
        thread {
            database.kepItemDao().update(item)
            Log.d("KepAdatokActivity", "KepItem update was successful")
        }
    }
    override fun onKepItemCreated(newItem: KepItem) {
        thread {
            val insertId = database.kepItemDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }

    override fun onKepItemEdit(editItem: KepItem) {
        thread {
            database.kepItemDao().update(editItem)
            val item = database.kepItemDao().getAll()
            runOnUiThread {
                adapter.update(item)
            }

        }
    }

    override fun onItemRemoved(delItem: KepItem) {
        thread {
            database.kepItemDao().deleteItem(delItem)
            runOnUiThread {
                adapter.deleteItem(delItem)
            }
        }

    }
    override fun onItemEdited(item: KepItem) {
        ShowEditDialog(item)
    }
    private fun ShowEditDialog(editedItem: KepItem? = null){
        NewKepItemDialogFragment(editedItem).show(supportFragmentManager, NewKepItemDialogFragment.TAG)
    }

    override fun onKepSelected(kepleiras: String?) {
        val showDetailsIntent = Intent()
        showDetailsIntent.setClass(this, KepLeirasActivity::class.java)
        showDetailsIntent.putExtra(KepLeirasActivity.EXTRA_KEP_LEIRAS, kepleiras)
        startActivity(showDetailsIntent)
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

