package com.example.point

import android.content.Intent
import android.os.Bundle
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

}
