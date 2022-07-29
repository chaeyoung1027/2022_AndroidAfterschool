package com.example.weatherdustchecker

import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

@JsonDeserialize(using=MyDeserializer::class)
data class OpenWeatherAPIJSONResponse(val temp: Double, val id: Int)

class MyDeserializer : StdDeserializer<OpenWeatherAPIJSONResponse>(
    OpenWeatherAPIJSONResponse::class.java
) {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?
    ): OpenWeatherAPIJSONResponse {
        val node = p?.codec?.readTree<JsonNode>(p)

//        val weather = node?.get("weather")
//        val firstWeather = weather?.elements()?.next()
//        val id = firstWeather?.get("id")?.asInt()

//        val main = node?.get("main")
//        val temp = main?.get("temp")?.asDouble()

        val id = node?.get("weather")?.elements()?.next()?.get("id")?.asInt()
        val temp = node?.get("main")?.get("temp")?.asDouble()

        return OpenWeatherAPIJSONResponse(temp!!, id!!)
    }
}

class WeatherPageFragment : Fragment() {
    lateinit var weatherImage : ImageView
    lateinit var statusText : TextView
    lateinit var temperatureText : TextView
    var APP_ID = "0d30adfc8ad70b893e99fc30039fdbbe"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater
            .inflate(R.layout.weather_page_fragment,
                container, false)

        //TODO : arguments 값 참조해서 두 개 값 가져오고, 해당하는 뷰에 출력해주기
        statusText = view.findViewById<TextView>(R.id.weather_status_text)
        temperatureText = view.findViewById<TextView>(R.id.weather_temp_text)
        weatherImage = view.findViewById<ImageView>(R.id.weather_icon)

//        statusText.text = arguments?.getString("status")
//        temperatureText.text = arguments?.getString("temperature").toString()

        //TODO : ImageView 가져와서 sun 이미지 출력하기
//        weatherImage.setImageResource(R.drawable.sun);
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lat = arguments?.getDouble("lat")
        val lon = arguments?.getDouble("lon")
        //val url = "https://api.openweathermap.org/data/2.5/weather?units=metric&lat=37.58&lon=126.98&appid=${APP_ID}&lat=${lat}&lon${lon}"

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(WeatherAPIService::class.java)
        val apiCallForData = apiService.getWeatherStatusInfo(APP_ID, lat!!, lon!!)
        apiCallForData.enqueue(object : Callback<OpenWeatherAPIJSONResponseGSON> {
            override fun onResponse(
                call: Call<OpenWeatherAPIJSONResponseGSON>,
                response: Response<OpenWeatherAPIJSONResponseGSON>
            ) {
                val data = response.body()
                Log.d("mytag", data.toString())
                //TODO : 다시 날씨 화면 나오도록 코드 작성
                val temp = data?.main?.get("temp")
                val id = data?.weather?.get(0)?.get("id")
                Log.d("mytag", temp.toString())
                Log.d("mytag", id.toString())

            }

            override fun onFailure(call: Call<OpenWeatherAPIJSONResponseGSON>, t: Throwable) {
                Toast.makeText(activity,
                    "예외 발생 : ${t.message}",
                    Toast.LENGTH_SHORT).show()
            }
        })

    }

    companion object {
        fun newInstance(lat: Double, lon: Double)
                : WeatherPageFragment
        {
            val fragment = WeatherPageFragment()
            //번들 객체에 필요한 정보를 저장
            val args = Bundle()
            args.putDouble("lat", lat)
            args.putDouble("lon", lon)
            fragment.arguments = args

            return fragment
        }
    }
}