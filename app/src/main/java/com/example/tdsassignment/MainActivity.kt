package com.example.tdsassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.random.Random

import rx.Observable

import android.util.Log
import android.widget.Toast
import com.example.letsgetcheckedassignment.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import okhttp3.ResponseBody
import rx.Subscriber
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.provider.ContactsContract
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import rx.Observer


class MainActivity : AppCompatActivity() {


    lateinit var totalCount: TextView
    lateinit var youngCount: TextView
    lateinit var middleCount: TextView
    lateinit var oldCount: TextView
    lateinit var layout_no_emergency: LinearLayout
    lateinit var layout_emergency: LinearLayout

    var listLessEighteen : MutableList<Datum> = ArrayList()
    var listMiddleAge : MutableList<Datum> = ArrayList()
    var listOlderAge : MutableList<Datum> = ArrayList()






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       initialize()
        val r = Random
        val ranNum=r.nextInt(90-30)+30
        Log.v("Number","Range"+ranNum)
        Observable.interval(0,ranNum.toLong(),TimeUnit.SECONDS)
            .flatMap {
                return@flatMap Observable.create<Boolean> { emitter ->
                    Log.d("IntervalExample", "Create")
                    emitter.onNext(r.nextBoolean())

                }
            }
            .doOnError { error -> Log.e("Error!st",""+error.toString()) }
            .observeOn(AndroidSchedulers.mainThread())



            .subscribe {
                if (it === true) {

                    Log.v("ABC",""+it);
                    layout_no_emergency.visibility= View.GONE
                    layout_emergency.visibility= View.VISIBLE
                    getAPIData()


                }
                else{
                    layout_emergency.visibility=View.GONE
                    layout_no_emergency.visibility= View.VISIBLE
                    Log.v("ABC",""+it);

                }
            }


    }

   fun getAPIData(){

       Observable.interval(0, 5, TimeUnit.SECONDS)
           .flatMap{ApiClient.getDataApi()}
           .doOnError { error -> Log.v("Error",""+error.toString()) }
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(this::updateUI, this::handleError)

       }

    private fun initialize(){

        totalCount = findViewById(R.id.count)
        youngCount = findViewById(R.id.count_eighteen)
        middleCount = findViewById(R.id.count_middle)
        oldCount = findViewById(R.id.count_last)

        layout_emergency = findViewById(R.id.layout_emergency)
        layout_no_emergency = findViewById(R.id.layout_no_emergency)

    }

    private fun handleError(error: Throwable) {

        Log.e("ERRRor", error.localizedMessage)

        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }


    private fun updateUI(model : DataModel) {

        var dataList : MutableList<Datum> = ArrayList()

        dataList.addAll(model.getData()!!)
        totalCount.text= dataList.size.toString()


        Log.v("Update",""+model.getData())
        for (i in dataList) {
            if (i.employeeAge?.let { Integer.parseInt(it) }!! < 18) {
               listLessEighteen.add(i)
            }
            else if(i.employeeAge?.let { Integer.parseInt(it) }!! > 18
                && i.employeeAge?.let { Integer.parseInt(it) }!! < 60){
                listMiddleAge.add(i)

            }
            else{
               listOlderAge.add(i)

            }

        }

        setDataOnUI()



    }

    fun setDataOnUI(){
        Log.v("Young",""+listLessEighteen.size)
        youngCount.text= listLessEighteen.size.toString()
        listLessEighteen.clear()


        Log.v("MiddleAge",""+listMiddleAge.size)
        middleCount.text= listMiddleAge.size.toString()
        listMiddleAge.clear()

        Log.v("OlderAge",""+listOlderAge.size)
        oldCount.text= listOlderAge.size.toString()
        listOlderAge.clear()
    }







}








