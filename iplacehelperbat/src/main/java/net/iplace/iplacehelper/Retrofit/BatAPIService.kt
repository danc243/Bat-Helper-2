package net.iplace.iplacehelper.Retrofit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Created by ${DANavarro} on 07/12/2018.
 */
interface BatAPIService {


//    @GET("wsaccess/interfaz.svc/ruta")
//    fun getRoutes(@Header("Authorization") header: String): Call<String>

    @Headers(
            "Content-Type:application/json"
    )
    @POST("wsaccess/interfaz.svc/login")

    fun login(@Body body: HashMap<String, String>): Call<String>


    companion object {
        fun create(): BatAPIService {
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://bat.iplace.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(BatAPIService::class.java)
        }
    }

}