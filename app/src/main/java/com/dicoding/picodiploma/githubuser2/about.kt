package com.dicoding.picodiploma.githubuser2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class about: AppCompatActivity() {

    private var title : String = "Developer's Profile"

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)
        setActionBarTitle(title)
    }

    private fun setActionBarTitle(title : String){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
        return super.onSupportNavigateUp()
    }
}