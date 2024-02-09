package net.azarquiel.averetrofit.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import net.azarquiel.averetrofit.R
import net.azarquiel.averetrofit.adapter.AdapterZonas
import net.azarquiel.averetrofit.databinding.ActivityMainBinding
import net.azarquiel.averetrofit.model.Usuario
import net.azarquiel.averetrofit.model.Zona
import net.azarquiel.averetrofit.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private var usuario: Usuario? = null
    private lateinit var sh: SharedPreferences
    private lateinit var zona: List<Zona>
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: AdapterZonas
    private lateinit var rvzonas: RecyclerView
    private lateinit var titulo: String
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        titulo = title.toString()
        sh = getSharedPreferences("login", Context.MODE_PRIVATE)
        getUsuarioSH()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initRV()
        getZonas()

    }

    private fun getUsuarioSH() {
        val usuarioJSON = sh.getString("usuario", null)
         usuarioJSON?.let {
            usuario = Gson().fromJson(usuarioJSON, Usuario::class.java)
             showTitle()


        }
    }

    private fun getZonas() {
        viewModel.getZonas().observe(this, Observer {
            it?.let{
                zona = it
                adapter.setZonas(it)
            }
        })
    }

    private fun initRV() {
        adapter = AdapterZonas(this, R.layout.rowzonas)
        binding.cz.rvzonas.adapter = adapter
        binding.cz.rvzonas.layoutManager = LinearLayoutManager(this)
    }



    //Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_user -> {
                onClickLoginRegister()
                true
            }
            R.id.ic_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onClickLoginRegister() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Login/Register")
        val ll = LinearLayout(this)
        ll.setPadding(30,30,30,30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        lp.setMargins(0,50,0,50)

        val textInputLayoutNick = TextInputLayout(this)
        textInputLayoutNick.layoutParams = lp
        val etnick = EditText(this)
        etnick.setPadding(0, 80, 0, 80)
        etnick.textSize = 20.0F
        etnick.hint = "Nick"
        textInputLayoutNick.addView(etnick)

        val textInputLayoutPass = TextInputLayout(this)
        textInputLayoutPass.layoutParams = lp
        val etpass = EditText(this)
        etpass.setPadding(0, 80, 0, 80)
        etpass.textSize = 20.0F
        etpass.hint = "Pass"
        textInputLayoutPass.addView(etpass)


        ll.addView(textInputLayoutNick)
        ll.addView(textInputLayoutPass)
        builder.setView(ll)

        builder.setPositiveButton("Aceptar") { dialog, which ->
            if (etnick.text.isEmpty() || etpass.text.isEmpty())
                Toast.makeText(this, "Rellena los dos campos...", Toast.LENGTH_SHORT).show()
            else
                login(etnick.text.toString(), etpass.text.toString())
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
        }

        builder.show()
    }

    private fun login(nick: String, pass: String) {
        viewModel.getObtenerUsuario(nick, pass).observe(this, Observer {
            if ( it == null){ //Registrar
                viewModel.saveUsuario(Usuario(0, nick, pass)).observe(this, Observer {
                    it?.let {
                        usuario = it
                        saveSH(it)
                    }
                })
            }
            else{ //Guardar usuario logueado
                usuario = it
                saveSH(it)
            }
        })
    }

    private fun logout() {
        // Limpiar la información del usuario en SharedPreferences
        sh.edit().remove("usuario").apply()

        // Redirigir al usuario a la pantalla de inicio de sesión
        startActivity(Intent(this, MainActivity::class.java))
        finish() // Cierra la actividad actual para evitar que el usuario vuelva atrás con el botón de retroceso
    }
    
    private fun saveSH(usuario: Usuario) {
        // guardar usuario
        val editor = sh.edit()
        editor.putString("usuario", Gson().toJson(usuario))
        editor.commit()
        showTitle()
    }

    private fun showTitle() {
        title = "$titulo - ${usuario!!.nick}"
    }

    fun onClickZona(v: View) {
        val zona = v.tag as Zona
        val intent = Intent(this, ZonaDetailActivity::class.java)
        intent.putExtra("zonas", zona)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }
}