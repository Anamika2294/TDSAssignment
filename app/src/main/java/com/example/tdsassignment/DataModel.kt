package com.example.tdsassignment

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DataModel {

    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("data")
    @Expose
    private var data: List<Datum>? = null

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getData(): List<Datum>? {
        return data
    }

    fun setData(data: List<Datum>) {
        this.data = data
    }


}





