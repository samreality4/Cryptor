package com.example.sxg.myapplication

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import javax.crypto.Cipher

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var string : String = "String"
        var string1 : String = "String2"
        val iv = Crytor().Iv
        var text: EditText = this.findViewById(R.id.password)
        var output: TextView = findViewById(R.id.output)
        val encryButton = findViewById<Button>(R.id.encry)
        val decryButton = findViewById<Button>(R.id.decry)

        encryButton?.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                output.setText(Crytor().encryptText(string, string1).toString())
            }

            decryButton?.setOnClickListener {
                ///Todo why iv is not intialized
                Crytor().decryptData(string, output.toString().toByteArray(),iv!!)

            }

        }

    }

}


