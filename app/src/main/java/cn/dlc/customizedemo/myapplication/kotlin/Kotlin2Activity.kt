package cn.dlc.customizedemo.myapplication.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import cn.dlc.customizedemo.myapplication.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_kotlin2.*

class Kotlin2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin2)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            parseJsonWithGson("{\n" +
                    "\t\"parent\": {\n" +
                    "\t\t\n" +
                    "\t}\n" +
                    "}")
        }
    }

    fun parseJsonWithGson(jsonData: String) {
        var gson = Gson()
        var appList = gson.fromJson(jsonData, KotlinBean::class.javaObjectType)

        Log.e("kotlin:",appList.parent?.child)
    }
}
