package cn.dlc.customizedemo.myapplication.kotlin
import com.google.gson.annotations.SerializedName


/**
 * Created by fengzimin  on  2019/04/29.
 * interface by
 */
data class KotlinBean(
    @SerializedName("parent")
    val parent: Parent
)

data class Parent(
    @SerializedName("child")
    val child: String
)

data class TestBean(var beanA: BeanA?)

data class BeanA(
        val str1: String?,
        val str2:String
)