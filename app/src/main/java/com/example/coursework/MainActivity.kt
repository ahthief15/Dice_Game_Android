package com.example.coursework

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
    }

    // function to create a pop up
    fun About(view: View) {
        val popup = PopupWindow(this)
        val tv = TextView(this)
        tv.text= "I (Ahthief 20211506) confirm that I understand what plagiarism is and have read andunderstood the section on Assessment Offences in the EssentialInformation for Students. The work that I have submitted isentirely my own. Any work from other authors is duly referenced and acknowledged"
        tv.setPadding(10,10,10,10)
        tv.setTextColor(Color.parseColor("#ffffff"))


        popup.width = ViewGroup.LayoutParams.WRAP_CONTENT
        popup.height = ViewGroup.LayoutParams.WRAP_CONTENT


        popup.contentView = tv


        popup.showAtLocation(view, Gravity.CENTER,0 ,800)



    }
    // function to open the game window when new game button is pressed
    fun openNewActivity(view: View) {
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }

    override fun onStop() {
        super.onStop()

        // Clear the shared preferences when the app is closed
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }


    }

