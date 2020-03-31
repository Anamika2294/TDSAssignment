package com.example.letsgetcheckedassignment

import com.example.tdsassignment.DataModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

object ApiClient{

var BASE_URL:String="http://dummy.restapiexample.com/api/v1/"
val getClient: ApiInterface
    get() {

        val gson = GsonBuilder()
            .setLenient()
            .create()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))

            .build()

        return retrofit.create(ApiInterface::class.java)

    }

    fun getDataApi(): Observable<DataModel> {

        return getClient.getData()
            .map({ respond -> respond })

    }

}