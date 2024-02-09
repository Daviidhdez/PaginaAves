package net.azarquiel.averetrofit.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.averetrofit.R
import net.azarquiel.averetrofit.adapter.AdapterRecursos
import net.azarquiel.averetrofit.adapter.AdapterZonas
import net.azarquiel.averetrofit.databinding.ActivityRecursosBinding
import net.azarquiel.averetrofit.model.Recurso
import net.azarquiel.averetrofit.model.Usuario
import net.azarquiel.averetrofit.model.Zona
import net.azarquiel.averetrofit.viewmodel.MainViewModel

class RecursosActivity : AppCompatActivity() {

    private lateinit var recursos: List<Recurso>
    private lateinit var adapter: AdapterRecursos
    private lateinit var viewModel: MainViewModel
    private var usuario: Usuario? = null
    private lateinit var zona: Zona
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRecursosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecursosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        zona = intent.getSerializableExtra("zonas") as Zona
        usuario = intent.getSerializableExtra("usuario") as Usuario?

        title = "Recursos - ${zona.nombre}"

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initRV()
        getRecursos()
    }

    private fun initRV() {
        adapter = AdapterRecursos(this, R.layout.rowrecursos)
        binding.cr.rvrecurso.adapter = adapter
        binding.cr.rvrecurso.layoutManager = LinearLayoutManager(this)
    }

    private fun getRecursos() {
        viewModel.getRecursosByZona(zona.id).observe(this, Observer { it ->
            it?.let{
                recursos = it
                adapter.setRecursos(it)
            }
        })
    }

    fun onClickRecurso(v: View){
        val recurso = v.tag as Recurso
        val intent = Intent(this, RecursosDetailActivity::class.java)
        intent.putExtra("recursos", recurso)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }


}