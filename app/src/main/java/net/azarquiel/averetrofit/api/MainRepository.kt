package net.azarquiel.averetrofit.api

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.averetrofit.model.Comentario
import net.azarquiel.averetrofit.model.Recurso
import net.azarquiel.averetrofit.model.Usuario
import net.azarquiel.averetrofit.model.Zona


/**
 * Created by Paco Pulido.
 */

class MainRepository() {
    val service = WebAccess.aveService

    suspend fun getZonas(): List<Zona> {
        val webResponse = service.getZonas().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.zonas
        }
        return emptyList()
    }

    suspend fun getRecursosByZona(idzona:Int): List<Recurso> {
        val webResponse = service.getRecursosByZona(idzona).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.recursos
        }
        return emptyList()
    }

    suspend fun getObtenerUsuario( nick:String, pass:String): Usuario? {
        val webResponse = service.getObtenerUsuario( nick, pass).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.usuario
        }
        return null
    }

    suspend fun saveUsuario(usuario: Usuario): Usuario? {
        var usuarioResponse:Usuario? = null
        val webResponse = service.saveUsuario(usuario).await()
        if (webResponse.isSuccessful) {
            usuarioResponse=webResponse.body()!!.usuario
        }
        return usuarioResponse
    }

    suspend fun getComentario(idrecurso:Int): List<Comentario> {
        val webResponse = service.getComentario(idrecurso).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.comentarios
        }
        return emptyList()
    }

    suspend fun saveComentario(idrecurso: Int, comentario: Comentario): Comentario? {
        var comentarioresponse: Comentario? = null
        val webResponse = service.saveComentario(idrecurso, comentario).await()
        if (webResponse.isSuccessful) {
            comentarioresponse = webResponse.body()!!.comentario
        }
        return comentarioresponse
    }



}
