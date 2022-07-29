package com.example.jsondeserializationstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

//첫 번째
data class MyJSONDataClass(
    val data1: Int,
    val data2: String,
    val list: List<Int>
)

//두 번째
data class PersonStringClass(
    val name: String,
    val age: Int,
    val favorites: List<String>,
    val address: Address //주소가 여러개일 때 : Address를 List로
)

data class Address (
    val city:String,
    val lat : Double,
    val lon : Double,
)

//세 번째(중첩을 원하지 않을 때! 활용도가 더 높다!)
@JsonDeserialize(using=ComplexJSONDataDeserializer::class)
data class ComplexJSONData(
    val innerData:String,
    val data1:Int,
    val data2:String,
    val list:List<Int>
)
class ComplexJSONDataDeserializer : StdDeserializer<ComplexJSONData>(
    ComplexJSONData::class.java
) {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): ComplexJSONData {
        val node = p?.codec?.readTree<JsonNode>(p)

        val nestedNode = node?.get("nested")
        val innerDataValue = nestedNode?.get("inner_data")?.asText()

        //TODO : data1이랑 data2 가져오기
        val innerNestNode = nestedNode?.get("inner_nested")
        val innerNestData1 = innerNestNode?.get("data1")?.asInt()
        val innerNestData2 = innerNestNode?.get("data2")?.asText()

        val list = mutableListOf<Int>()
        innerNestNode?.get("list")?.elements()?.forEach {
            list.add(it.asInt())
        }
        return ComplexJSONData(
            innerDataValue!!,//!!가 없으면 에러가 나는 이유 : innerDataValue가 nullable타입이기 때문에!
            innerNestData1!!,
            innerNestData2!!,
            list
        )
    }
}


data class MyJSONNestedDataClass(val nested: MyJSONDataClass)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mapper = jacksonObjectMapper()
        val jsonString =  """
            {
                "data1":1234,
                "data2":"Hello",
                "list":[1, 2, 3]
            }
        """.trimIndent()

        val d1 = mapper?.readValue<MyJSONDataClass>(jsonString)
        Log.d("mytag", d1.toString())

        val jsonString2 =  """
            {
                "nested":{
                    "data1":1234,
                    "data2":"Hello",
                    "list":[1, 2, 3]
                }
            }
        """.trimIndent()

        val d2 = mapper?.readValue<MyJSONNestedDataClass>(jsonString2)
        Log.d("mytag", d2.toString())

        val PersonString =  """
            {
               "name":"John",
               "age":20,
               "favorites":["study", "game"],
               "address":{
                   "city":"Seoul",
                   "lat":0.0,
                   "lon":1.0
               }
            }
        """.trimIndent()

        val d3 = mapper?.readValue<PersonStringClass>(PersonString)
        Log.d("mytag", d3.toString())

        val complexJsonString="""
            {
                "nested":{
                    "inner_data":"Hello from inner",
                    "inner_nested":{
                        "data1":1234,
                        "data2":"Hello",
                        "list":[1, 2, 3]
                    }
                }
            }
        """.trimIndent()

        val complex = mapper?.readValue<ComplexJSONData>(complexJsonString)
        Log.d("mytag", complex.toString())

    }
}