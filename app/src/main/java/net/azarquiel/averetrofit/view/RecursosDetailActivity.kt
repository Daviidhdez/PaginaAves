package net.azarquiel.averetrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import net.azarquiel.averetrofit.R
import net.azarquiel.averetrofit.adapter.AdapterComentarios
import net.azarquiel.averetrofit.model.Comentario
import net.azarquiel.averetrofit.model.Recurso
import net.azarquiel.averetrofit.model.Usuario
import net.azarquiel.averetrofit.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date

class RecursosDetailActivity : AppCompatActivity() {
    private lateinit var comentarios: List<Comentario>
    private lateinit var rvcomentarios: RecyclerView
    private lateinit var ivrecurso: ImageView
    private lateinit var tvrecurso: TextView
    private lateinit var viewModel: MainViewModel
    private var usuario: Usuario? = null
    private lateinit var recurso: Recurso
    private lateinit var adapter: AdapterComentarios

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recursos_detail)

        recurso = intent.getSerializableExtra("recursos") as Recurso
        usuario = intent.getSerializableExtra("usuario") as Usuario?

        findView()

        val fabcomentario = findViewById<FloatingActionButton>(R.id.fabcomentario)
        fabcomentario.setOnClickListener{
            dialogComentario()
        }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initRV()
        getComentarios()
        showRecurso()
    }

    private fun dialogComentario() {
        if (usuario==null) {
            msg("No login, no comentario Adios....")
            return
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Comentario")
        val ll = LinearLayout(this)
        ll.setPadding(30,30,30,30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        lp.setMargins(0,50,0,50)

        val textInputLayoutContent = TextInputLayout(this)
        textInputLayoutContent.layoutParams = lp
        val etcontenido = EditText(this)
        etcontenido.setPadding(0, 80, 0, 80)
        etcontenido.textSize = 20.0F
        etcontenido.hint = "Comentario"
        textInputLayoutContent.addView(etcontenido)

        ll.addView(textInputLayoutContent)

        builder.setView(ll)

        builder.setPositiveButton("Aceptar") { dialog, which ->
            if(!etcontenido.text.toString().isEmpty()) {
                addcomentario(etcontenido.text.toString())
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
        }

        builder.show()
    }

    private fun addcomentario(comentario: String) {
        val comentario = Comentario(0, usuario!!.id, recurso.id, LocalDateTime.now().toString(), comentario)
        viewModel.saveComentario(recurso.id, comentario).observe(this, Observer {
            it?.let{
                val comentariosaux = ArrayList(comentarios)
                comentariosaux.add(0, it)
                comentarios = comentariosaux
                adapter.setComentarios(comentarios)
            }
        })
    }

    private fun findView() {
        tvrecurso = findViewById<TextView>(R.id.tvrecurso)
        ivrecurso = findViewById<ImageView>(R.id.ivrecurso)
        rvcomentarios = findViewById<RecyclerView>(R.id.rvcomentarios)
    }

    private fun initRV() {
        adapter = AdapterComentarios(this, R.layout.rowcomentarios)
        rvcomentarios.adapter = adapter
        rvcomentarios.layoutManager = LinearLayoutManager(this)
    }
    private fun getComentarios() {
        viewModel.getComentario(recurso.id).observe(this, Observer { it ->
            it?.let{
                comentarios=it
                adapter.setComentarios(comentarios)
            }
        })
    }

    private fun showRecurso() {
        tvrecurso.text = recurso.titulo
        // foto de internet a traves de Picasso
        Picasso.get().load(recurso.url).into(ivrecurso)
    }

    private fun msg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}