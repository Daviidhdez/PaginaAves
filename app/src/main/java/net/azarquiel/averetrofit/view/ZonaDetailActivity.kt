package net.azarquiel.averetrofit.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import net.azarquiel.averetrofit.R
import net.azarquiel.averetrofit.model.Usuario
import net.azarquiel.averetrofit.model.Zona

class ZonaDetailActivity : AppCompatActivity() {
    private lateinit var zona: Zona
    private var usuario: Usuario? = null
    private lateinit var tvrowpresentacionzona: TextView
    private lateinit var tvrowformacionprincipaleszona: TextView
    private lateinit var tvrowlocalizacionzona: TextView
    private lateinit var tvrownombrezona: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zona_detail)

        zona = intent.getSerializableExtra("zonas") as Zona
        usuario = intent.getSerializableExtra("usuario") as Usuario?

        title = "Zona"

        findView()

        //Cuando pulsas se va a los recursos
        val fabrecursos = findViewById<FloatingActionButton>(R.id.fabrecursos)
        fabrecursos.setOnClickListener{
            openRecursos()
        }

        if (usuario!=null ) msg(usuario!!.nick)
        showDetail()
    }

    private fun findView() {
        tvrownombrezona = findViewById<TextView>(R.id.tvrownombrezona)
        tvrowlocalizacionzona = findViewById<TextView>(R.id.tvrowlocalizacionzona)
        tvrowformacionprincipaleszona = findViewById<TextView>(R.id.tvrowformacionprincipaleszona)
        tvrowpresentacionzona = findViewById<TextView>(R.id.tvrowpresentacionzona)
    }

    private fun msg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun openRecursos() {
        val intent = Intent(this, RecursosActivity::class.java)
        intent.putExtra("zonas", zona)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }

    private fun showDetail() {
        tvrownombrezona.text = zona.nombre
        tvrowlocalizacionzona.text = "${zona.localizacion} (${zona.geom_lat} , ${zona.geom_lon})"
        tvrowformacionprincipaleszona.text = zona.formaciones_principales
        tvrowpresentacionzona.text = zona.presentacion
    }
}