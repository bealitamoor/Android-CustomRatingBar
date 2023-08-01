package com.dn.ratingbar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kikt.view.CustomRatingBar

class MainActivity : AppCompatActivity(), CustomRatingBar.OnStarChangeListener {

    protected var mRb: CustomRatingBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRb = findViewById(R.id.rb)
        mRb!!.onStarChangeListener = this
    }

    override fun onStarChange(ratingBar: CustomRatingBar?, star: Float) {
        //Toast.makeText(this, "Rating: $star", Toast.LENGTH_SHORT).show()
    }
}