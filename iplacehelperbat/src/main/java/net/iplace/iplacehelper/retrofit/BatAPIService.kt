package net.iplace.iplacehelper.retrofit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by ${DANavarro} on 07/12/2018.
 */
interface BatAPIService {

    @Headers(
            "Content-Type:application/json"
    )
    @POST("wsaccess/interfaz.svc/login")

    fun login(@Body body: HashMap<String, String>): Call<String>

    @Headers(
            "Content-Type:application/json"
    )
    @POST("wsaccess/interfaz.svc/catalogos")
    fun getCatalogos(@Body body: HashMap<String, String>): Call<String>








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