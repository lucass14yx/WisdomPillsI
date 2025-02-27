@file:Suppress("DEPRECATION")
package com.maestre.wisdompills.View

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.maestre.wisdompills.Model.adapter.NoteAdapter
import com.maestre.wisdompills.R
import com.maestre.wisdompills.ViewModel.NoteViewModel
import com.maestre.wisdompills.databinding.ActivityEnterBinding
import com.maestre.wisdompills.databinding.DialogoPersonalizadoBinding


class EnterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnterBinding
    private lateinit var myAdapter: NoteAdapter
    private val viewmodel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {


        // Configurar el tema antes de inflar las vistas
        super.onCreate(savedInstanceState)
        binding = ActivityEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Vamos a extraer los parametros que pasamos desde la Activity anterior
        val userId = intent.getStringExtra("idUser")?:return
        val name = intent.getStringExtra("name")?:return

        //Ahora concatenamos el nombre del usuario con un mensaje de bienvenida y lo cargamos en el textView
        val mensaje = getString(R.string.welcome, name)
        binding.textView.setText(mensaje)

        //Nos creamos el toolbar
        val toolbar: MaterialToolbar = binding.materialToolbar
        setSupportActionBar(toolbar)

        //Configuramos e inicializamos el RecyclerView
        initRecyclerView()

        //Observamos los datos del LiveData del ViewModel
        viewmodel.notesLiveData.observe(this) { notes ->
            //Ahora filtramos por nombre de usuario
            val userNotes = notes.filter { it.idUser == userId }
            //Actualizamos los datos del adaptador
            myAdapter.updateData(userNotes)
        }

        //Funciones de los 2 botones
        binding.btnModify.setOnClickListener { showModifyAlertDialog() }
        binding.btnAdd.setOnClickListener { showAddAlertDialog(userId) }



    }

    //Metodo para inicializar y configurar el RecyclerView que muestra las notas de este usuario
    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        binding.notesRecyclerView.layoutManager = manager
        //Inicializamos el adaptador con una lista vacia
        myAdapter = NoteAdapter(mutableListOf())
        binding.notesRecyclerView.adapter = myAdapter

        //Decorador que divide los Items
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.notesRecyclerView.addItemDecoration(decoration)
    }
    private fun showModifyAlertDialog() {
        val builder = AlertDialog.Builder(this)
        val bindingDialog = DialogoPersonalizadoBinding.inflate(layoutInflater)
        val editText = bindingDialog.editTextData
        builder.setView(bindingDialog.root)
            .setPositiveButton(R.string.OK) { dialogInterface, i ->
                val inputText = editText.text.toString()
                if (inputText.isEmpty()) {
                    Toast.makeText(this, R.string.toast_enter_value, Toast.LENGTH_SHORT).show()
                } else {
                    // Continuar con la lógica
                    Toast.makeText(this, "Input: $inputText", Toast.LENGTH_SHORT).show()
                }// Process the input text

            }
            .setNegativeButton(R.string.Cancel, null)
            .show()
    }
    private fun showAddAlertDialog(userId: String) {
        AlertDialog.Builder(ContextThemeWrapper(this,R.style.CustomAlertDialog))
            .setTitle(R.string.text_alert_dia_confirmation)
            .setMessage(R.string.text_alert_dia_proceed)
            .setPositiveButton(R.string.OK) { dialog, which ->
                // Handle OK button click
                val title = binding.titleEditText.text.toString()
                val content = binding.contentEditText.text.toString()

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(this, R.string.toast_fill_all_fields, Toast.LENGTH_SHORT).show()
                } else {
                    // Si los campos son válidos, proceder
                    viewmodel.addNote(title, content, userId)
                    binding.titleEditText.text.clear()
                    binding.contentEditText.text.clear()
                    Toast.makeText(this, R.string.toast_added_successfully, Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(R.string.Cancel) { dialog, which ->
                // Handle Cancel button click
                Toast.makeText(this, R.string.toast_cancel_clicked, Toast.LENGTH_SHORT).show()
            }
            .show()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_info -> {
                val intent = Intent(this, PreferenceActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_search -> {
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_web -> {
                val url = "https://laemboscadura.com/?srsltid=AfmBOoqpVfh0w54MG2u5X0Tn2NSE3J_3l4wCY4qqW0tFfmXf8bKntvcd"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}