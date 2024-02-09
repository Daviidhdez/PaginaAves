package net.azarquiel.averetrofit.model

import java.io.Serializable
import java.time.LocalDateTime

data class Zona(
    var id:Int,
    var nombre:String,
    var localizacion:String,
    var formaciones_principales:String,
    var presentacion:String,
    var geom_lat:String,
    var geom_lon:String): Serializable

data class Recurso(
    var id:Int,
    var zona:Int,
    var titulo:String,
    var url:String): Serializable

data class Usuario (
    var id:Int,
    var nick:String,
    var pass:String):Serializable

data class Comentario(
    var id:Int,
    var usuario:Int,
    var recurso:Int,
    var fecha: String,
    var comentario:String):Serializable

data class Respuesta(
    var zonas:List<Zona>,
    var recursos:List<Recurso>,
    var usuario:Usuario,
    var comentarios:List<Comentario>,
    var comentario:Comentario


)