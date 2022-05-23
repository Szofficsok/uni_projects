package hu.bme.aut.naplo_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.naplo_2.databinding.ActivityKepLeirasBinding

class KepLeirasActivity : AppCompatActivity() {
    private var kepleiras: String? = null
    private lateinit var binding: ActivityKepLeirasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKepLeirasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        kepleiras = intent.getStringExtra(EXTRA_KEP_LEIRAS)
        binding.tvDescription.setText(kepleiras)
    }
    companion object {
        private const val TAG = "KepLeirasActivity"
        const val EXTRA_KEP_LEIRAS = "extra.kep_leiras"
    }

}