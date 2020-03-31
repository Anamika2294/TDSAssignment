package com.example.letsgetcheckedassignment

import com.example.tdsassignment.DataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import rx.Observable

interface ApiInterface {


//    @GET("employees")
//    fun getData(): Call<DataModel>

    @GET("employees")
    fun getData():  Observable<DataModel>

}