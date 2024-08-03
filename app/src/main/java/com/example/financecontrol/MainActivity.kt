package com.example.financecontrol

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.data.usecases.CheckTotalMoneyUseCase
import com.example.finance.FinanceFragment
import com.example.financecontrol.databinding.ActivityMainBinding
import com.example.financecontrol.navigation.ActivityRequired
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var activityRequiredSet: Set<@JvmSuppressWildcards ActivityRequired>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRequiredSet.forEach { it.onCreated(this) }
        inflateBinding()
        setOnApplyWindowInsetsListener()
        launchFragment()

    }

    private fun inflateBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        //        enableEdgeToEdge()
        setContentView(binding.root)
    }

    private fun setOnApplyWindowInsetsListener() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun launchFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_container, FinanceFragment())
            commit()
        }
    }

    override fun onDestroy() {
        activityRequiredSet.forEach { it.onDestroyed() }
        super.onDestroy()

    }
}