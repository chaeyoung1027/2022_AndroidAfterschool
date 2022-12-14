package com.example.weatherdustchecker

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.core.location.LocationListenerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var mPager : ViewPager
    private var lat : Double = 37.579876
    private var lon : Double = 126.976998

    lateinit var locationManager: LocationManager
    lateinit var locationListner: LocationListener
    val PERMISSIN_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        locationListner = LocationListener {
            lat = it.latitude
            lon = it.longitude
            Log.d("mytag",lat.toString())
            Log.d("mytag",lon.toString())
            locationManager.removeUpdates(locationListner)

            val pagerApater = MypagerApater(supportFragmentManager, lat, lon)
            mPager.adapter = pagerApater
        }

        if(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
            ==PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSIN_REQUEST_CODE)
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            locationListner
        )

        //?????? ?????? ????????? ?????????
        supportActionBar?.hide()

        mPager = findViewById(R.id.pager)

        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,              //0??????
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                if(position == 0){
                    Toast.makeText(applicationContext,
                        "?????? ????????? ?????????",
                        Toast.LENGTH_SHORT).show()
                }else if(position == 1){
                    Toast.makeText(applicationContext,
                        "???????????? ????????? ?????????.",
                        Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSIN_REQUEST_CODE) {
            var allPermissiononGranted = true
            for(result in grantResults) {
                allPermissiononGranted = (result == PackageManager.PERMISSION_GRANTED)
                if(!allPermissiononGranted) break
            }
            if(allPermissiononGranted) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0, 0f, locationListner
                )
            }else{
                Toast.makeText(applicationContext,
                    "?????? ?????? ?????? ????????? ???????????????",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    class MypagerApater(fm : FragmentManager, val lat : Double, val lon : Double) : FragmentStatePagerAdapter(fm){
        override fun getCount() = 2

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> WeatherPageFragment.newInstance(lat, lon)
                1 -> DustPageFragment.newInstance(lat, lon)
                else -> throw Exception("???????????? ???????????? ????????????.")
            }
        }


    }
}