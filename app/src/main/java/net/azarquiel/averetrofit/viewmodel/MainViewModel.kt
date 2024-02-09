package net.azarquiel.averetrofit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.averetrofit.api.MainRepository
import net.azarquiel.averetrofit.model.Comentario
import net.azarquiel.averetrofit.model.Recurso
import net.azarquiel.averetrofit.model.Usuario
import net.azarquiel.averetrofit.model.Zona

// ……

/**
 * Created by Paco Pulido.
 */
class MainViewModel : ViewModel() {

    private var repository: MainRepository = MainRepository()

    fun getZonas(): MutableLiveData<List<Zona>> {
        val zonas = MutableLiveData<List<Zona>>()
        GlobalScope.launch(Main) {
            zonas.value = repository.getZonas()
        }
        return zonas
    }

    fun getRecursosByZona(idzona:Int): MutableLiveData<List<Recurso>> {
        val recursos = MutableLiveData<List<Recurso>>()
        GlobalScope.launch(Main) {
            recursos.value = repository.getRecursosByZona(idzona)
        }
        return recursos
    }


    fun getObtenerUsuario(nick:String, pass:String): MutableLiveData<Usuario> {
        val usuariorecibido = MutableLiveData<Usuario>()
        GlobalScope.launch(Main) {
            usuariorecibido.value = repository.getObtenerUsuario(nick, pass)
        }
        return usuariorecibido
    }

    fun saveUsuario(usuario: Usuario): MutableLiveData<Usuario> {
        val usuarioresponde = MutableLiveData<Usuario>()
        GlobalScope.launch(Main) {
            usuarioresponde.value = repository.saveUsuario(usuario)
        }
        return usuarioresponde
    }

    fun getComentario(idrecurso:Int): MutableLiveData<List<Comentario>> {
        val comentarios = MutableLiveData<List<Comentario>>()
        GlobalScope.launch(Main) {
            comentarios.value = repository.getComentario(idrecurso)
        }
        return comentarios
    }

    fun saveComentario(idrecurso: Int, comentario: Comentario): MutableLiveData<Comentario> {
        val comentarioresponde = MutableLiveData<Comentario>()
        GlobalScope.launch(Main) {
            comentarioresponde.value = repository.saveComentario(idrecurso, comentario)
        }
        return comentarioresponde
    }




}
