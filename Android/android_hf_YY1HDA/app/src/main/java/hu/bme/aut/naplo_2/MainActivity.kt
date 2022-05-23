package hu.bme.aut.naplo_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.naplo_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1000)
        setTheme(R.style.Theme_Naplo_2)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBejegyzes.setOnClickListener {
            val intent = Intent(this, BejegyzesAdatokActivity::class.java)
            startActivity(intent)
        }
        binding.btnKepek.setOnClickListener {
            val intent = Intent(this, KepAdatokActivity::class.java)
            startActivity(intent)
        }
        binding.btnCelok.setOnClickListener {
            val intent = Intent(this, AdatokActivity::class.java)
            startActivity(intent)
        }
    }
}