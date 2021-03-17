package com.example.awslocationservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserStateDetails
import com.amazonaws.services.geo.AmazonLocationClient
import com.amazonaws.services.geo.model.SearchPlaceIndexForTextRequest
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    val TAG = "QuickStart"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AWSMobileClient.getInstance().initialize(applicationContext, object :
            Callback<UserStateDetails> {
            override fun onResult(userStateDetails: UserStateDetails) {
                Log.i("QuickStart", "onResult: " + userStateDetails.userState)
            }

            override fun onError(error: Exception) {
                Log.e("QuickStart", "Initialization error: ", error)
            }
        })
        var btnSearch = findViewById<Button>(R.id.btn_search)
        btnSearch.setOnClickListener {
            Log.i(TAG, "OpenNewMap")
            openMap()
        }
    }

    fun openMap() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }
//
//        btnSearch.setOnClickListener(View.OnClickListener {
//            try {
//                Log.i(TAG, "Start request")
//                AWSMobileClient.getInstance()
//                    .initialize(applicationContext, object : Callback<UserStateDetails> {
//                        override fun onResult(userStateDetails: UserStateDetails) {
//                            val executorService = Executors.newFixedThreadPool(4)
//
//                            executorService.execute {
//                                val client = AmazonLocationClient(AWSMobileClient.getInstance())
//                                val request = SearchPlaceIndexForTextRequest()
//
//                                request.text = "Space Needle"
//                                request.indexName = "MapLocation"
//
//                                val response = client.searchPlaceIndexForText(request)
//
//                                for (result in response.results) {
//                                    Log.i("QuickStart", result.place.toString())
//                                }
//                            }
//                        }
//
//                        override fun onError(error: Exception) {
//                            Log.e("QuickStart", "Initialization error: ", error)
//                        }
//                    })
//            } catch (e:Exception) {
//            }
//        }
//        )

}