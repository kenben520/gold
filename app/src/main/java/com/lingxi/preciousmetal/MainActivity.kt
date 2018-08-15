package com.lingxi.preciousmetal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import javax.security.auth.callback.Callback
import com.lingxi.preciousmetal.ui.activity.QuestionActivity
import android.content.Intent



/**
 * Created by zhangwei on 2018/3/26.
 */
data class User(val name: String, val id: Int)

class MainActivity : AppCompatActivity(),Callback {
    fun initTab() {

    }
    val b: Byte = 1 // OK, 字面值是静态检测的
//    val f: Long? = b // 错误
    val i=0
    var a=0
    val boxedA: Int? = null
    fun sum(a: Int, b: Int): Int {
        return a + b
    }
    fun describe(obj: Any):Any =
            when (obj) {
                1          ->{

                }
                "Hello"    -> "Greeting"
                is Long    -> "Long"
                !is String -> "Not a string"
                else       -> "Unknown"
            }
    fun main(args: Array<String>) {
        val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
        fruits
                .filter { it.startsWith("a") }
                .sortedBy { it }
                .map { it.toUpperCase() }
                .forEach { println(it) }
        val user = getUser()
        val map = hashMapOf<String, Int>()
        map.put("one", 1)
        map.put("two", 2)

        for ((key, value) in map) {
            println("key = $key, value = $value")
        }    }

    fun getUser(): User {
        return User("Alex", 1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     //   setSupportActionBar(toolbar)
        val intent = Intent(this, QuestionActivity::class.java)
        startActivity(intent)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
