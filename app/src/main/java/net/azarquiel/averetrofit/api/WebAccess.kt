package net.azarquiel.averetrofit.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Paco Pulido.
 */

object WebAccess {

    val baseUrl = "http://www.ies-azarquiel.es/paco/apiaves/"
    val aveService : AveService by lazy {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(baseUrl)
            .build()

        return@lazy retrofit.create(AveService::class.java)
    }
}
