package com.example.contacts

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contacts.databinding.ActivityAddContactBinding
import com.example.contacts.databinding.ActivityContactDetailBinding

class ContactDetail : AppCompatActivity() {
    private lateinit var binding: ActivityContactDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        title = "Update Contact"
        println(intent.getStringExtra("name"))
        setContentView(binding.root)
        binding.tvName.setText(intent.getStringExtra("name"))
        binding.tvNumber.setText(intent.getStringExtra("number"))
        binding.tvEmail.setText(intent.getStringExtra("email"))
        binding.imageView.setImageBitmap(base64ToBitmap(intent.getStringExtra("imagePath")!!))
        val replyIntent = Intent()
        setResult(0, replyIntent)
    }

    fun base64ToBitmap(base64String: String): Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
}