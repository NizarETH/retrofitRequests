package com.app.retrofitrequests

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

lateinit var  apiInterface : ApiInterface
lateinit var  idUpdate: String

    class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         apiInterface =  RetrofitInstance.getInstance().create(ApiInterface::class.java)

        findViewById<Button>(R.id.button_get).setOnClickListener {
            getData()
        }
        findViewById<Button>(R.id.button_get_id).setOnClickListener {
            getIdData("3")
        }
        findViewById<Button>(R.id.button_post).setOnClickListener {
            sendData()
        }
        findViewById<Button>(R.id.button_update).setOnClickListener {
            updateData(idUpdate)
        }
        findViewById<Button>(R.id.button_delete).setOnClickListener {
           deleteData(idUpdate)
        }
    }

        private fun deleteData(id : String) {

            val call = apiInterface.deleteObject(id)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {

                        findViewById<TextView>(R.id.data_textView).setText("Objet supprimé !")

                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("MainActivity ",t.message.toString())

                }

            })
        }

        private fun updateData(id : String) {

            val myData = Data(description   = "my description", color = "my color")
            val obj = Object(id = id, name = "Hanna Jo", data = myData)

            val call = apiInterface.updateObject(id,obj)
            call.enqueue(object : Callback<Object> {
                override fun onResponse(call: Call<Object>, response: Response<Object>) {
                    if (response.isSuccessful && response.body() != null) {
                        val data = response.body() as Object

                        findViewById<TextView>(R.id.data_textView).setText("Nom " +data?.name)

                    }
                }

                override fun onFailure(call: Call<Object>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("MainActivity ",t.message.toString())

                }

            })
        }
        private fun sendData() {

            val myData = Data(description = "my description", color = "my color")
            val obj = Object(id = "100", name = "John Doe", data = myData)

            val call = apiInterface.createObject(obj)
            call.enqueue(object : Callback<Object> {
                override fun onResponse(call: Call<Object>, response: Response<Object>) {
                    if (response.isSuccessful && response.body() != null) {
                       val data = response.body() as Object

                        findViewById<TextView>(R.id.data_textView).setText("Nom " +data?.name)
                        idUpdate = data.id
                        Log.d("MainActivity ","idUpdate : "+idUpdate)

                    }
                }

                override fun onFailure(call: Call<Object>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("MainActivity ",t.message.toString())

                }

            })
        }

        private fun  displayData(data : Object, size : Int)
        {
            findViewById<TextView>(R.id.data_textView).setText("Nom " +data?.name+", size : "+size)
        }

        private fun getData()
        {
            val call = apiInterface.getObjects()
            call?.enqueue(object : Callback<MutableList<Object>> {
                override fun onResponse(call: Call<MutableList<Object>>, response: Response<MutableList<Object>>) {

                    if (response.isSuccessful && response.body() != null) {
                        val datas = response.body() as MutableList<Object>
                        for (data in datas)
                        {

                            displayData(data, datas.size)
                        }

                    }
                }
                override fun onFailure(call: Call<MutableList<Object>>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("MainActivity ",t.message.toString())
                }

            })

        }
        private fun getIdData(id : String)
        {
            val call = apiInterface.getObjectsById(id)
            call?.enqueue(object : Callback<Object> {
                override fun onResponse(call: Call<Object>, response: Response<Object>) {

                    if (response.isSuccessful && response.body() != null) {
                        val data = response.body() as Object

                        findViewById<TextView>(R.id.data_textView).setText("Nom " +data?.name)

                    }
                }
                override fun onFailure(call: Call<Object>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("MainActivity ",t.message.toString())
                }

            })

        }
}