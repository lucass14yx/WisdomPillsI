package com.maestre.wisdompills.View


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.maestre.wisdompills.databinding.ActivityPreferenceBinding

class PreferenceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreferenceBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        // Aplicar el tema antes de cargar la UI
        //aplicarModoOscuro()

        super.onCreate(savedInstanceState)
        binding = ActivityPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }


}