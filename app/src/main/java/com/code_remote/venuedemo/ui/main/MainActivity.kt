package com.code_remote.venuedemo.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.code_remote.venuedemo.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}