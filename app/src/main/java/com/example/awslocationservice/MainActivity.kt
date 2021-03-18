package com.example.awslocationservice

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserStateDetails
import com.amazonaws.mobileconnectors.geo.tracker.*


class MainActivity : AppCompatActivity() {
    val TAG = "QuickStart"
    private lateinit var tracker: AWSLocationTracker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AWSMobileClient.getInstance()
            .initialize(applicationContext, object : Callback<UserStateDetails?> {
                override fun onResult(userStateDetails: UserStateDetails?) {
                    tracker = AWSLocationTracker("TestTracker", AWSMobileClient.getInstance())

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            startTracking()
                        } else {
                            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
                        }
                    } else {
                        startTracking()
                    }
                }

                override fun onError(error: Exception?) {
                    // Handle AWSMobileClient initialization error
                }
            })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 0 && grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startTracking()
        }
    }

    fun startTracking() {
        val listener: TrackingListener = object : TrackingListener {
            override fun onStop() {
                // Handle tracked stopped event.
            }

            override fun onDataPublished(trackingPublishedEvent: TrackingPublishedEvent) {
                // Handle a successful publishing event for a batch of locations.
            }

            override fun onDataPublicationError(trackingError: TrackingError) {
                // Handle an unsuccessful publishing event for a batch of locations.
            }
        }
        val options = TrackingOptions.builder()
            .customDeviceId("MyTracker")
            .retrieveLocationFrequency(1000L) // Retrieve the current location every 30 seconds
            .emitLocationFrequency(5000L) // Emit a batch of locations to Amazon Location every 5 minutes
            .build()

        tracker.startTracking(this, options, listener)
    }


}