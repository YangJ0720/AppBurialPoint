package com.example.point

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author YangJ
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initView()
    }

    private fun initData() {

    }

    private fun initView() {
        btn_click.setOnClickListener {
            startActivity(Intent(this, ShowActivity::class.java))
        }
        btn_long_click.setOnLongClickListener {
            finish()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_add -> {
                Toast.makeText(this, R.string.menu_add, Toast.LENGTH_SHORT).show()
            }
            R.id.menu_del -> {
                Toast.makeText(this, R.string.menu_del, Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}
