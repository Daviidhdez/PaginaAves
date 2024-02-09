package net.azarquiel.averetrofit.api
import kotlinx.coroutines.Deferred
import net.azarquiel.averetrofit.model.Comentario
import net.azarquiel.averetrofit.model.Respuesta
import net.azarquiel.averetrofit.model.Usuario
import retrofit2.Response
import retrofit2.http.*
/**
 * Created by Paco Pulido.
 */
interface AveService {
    // No necesita nada para trabajar
    @GET("zonas")
    fun getZonas(): Deferred<Response<Respuesta>>

    // variable idbar en la ruta de la url => @Path
    @GET("zona/{idzona}/recursos")
    fun getRecursosByZona(@Path("idzona") idzona: Int): Deferred<Response<Respuesta>>

    // nick y pass variables sueltas en la url?nick=paco&pass=paco => @Query
    @GET("usuario")
    fun getObtenerUsuario(
        @Query("nick") nick: String,
        @Query("pass") pass: String): Deferred<Response<Respuesta>>

    // post con objeto => @Body
    @POST("usuario")
    fun saveUsuario(@Body usuario: Usuario): Deferred<Response<Respuesta>>

    @GET("recurso/{idrecurso}/comentarios")
    fun getComentario(@Path("idrecurso") idrecurso:Int) : Deferred<Response<Respuesta>>

    // post con variables sueltas => @Field y Obligatorio @FormUrlEncoded
    @POST("recurso/{idrecurso}/comentario")
    fun saveComentario(@Path("idrecurso") idrecurso: Int,
                  @Body comentario: Comentario
    ): Deferred<Response<Respuesta>>
}

