package com.example.motivation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.content.ContextCompat
import com.example.motivation.infra.MotivationConstants
import com.example.motivation.R
import com.example.motivation.data.Mock
import com.example.motivation.infra.SecurityPreferences
import com.example.motivation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private var filter: Int = MotivationConstants.FILTER.ALL
    private var mock: Mock = Mock()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Esconder barra de navegação
        supportActionBar?.hide()

        handleUserName()
        handleFilter(binding.imageAll.id)
        refreshPhrase()

        // Evento de Clique
        binding.buttonNewPhrase.setOnClickListener(this)
        binding.imageAll.setOnClickListener(this)
        binding.imageHappy.setOnClickListener(this)
        binding.imageSunny.setOnClickListener(this)
    }

    override fun onClick(view: View) {

        val listId = listOf(binding.imageAll.id, binding.imageHappy.id, binding.imageSunny.id)

        if (view.id == binding.buttonNewPhrase.id) {
            refreshPhrase()
        } else if (view.id in listId) {
            handleFilter(view.id)
        }

    }

    private fun handleUserName() {
        val name = SecurityPreferences(this).getString(MotivationConstants.KEY.USER_NAME)
        binding.textUserName.text = "Olá $name!"
    }

    private fun handleFilter(id: Int) {

        binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))
        binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))
        binding.imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))

        when (id) {
            binding.imageAll.id -> {
                binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.white))
                filter = MotivationConstants.FILTER.ALL
            }
            binding.imageHappy.id -> {
                binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.white))
                filter = MotivationConstants.FILTER.HAPPY

            }
            binding.imageSunny.id -> {
                binding.imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.white))
                filter = MotivationConstants.FILTER.SUNNY
            }
        }

    }

    private fun refreshPhrase() {
        binding.textPhrase.text = mock.getPhrase(filter)
    }


}