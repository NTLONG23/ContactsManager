package com.example.contacts

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.widget.ImageView
import androidx.core.net.toUri
import com.example.contacts.databinding.ActivityAddContactBinding
import java.io.ByteArrayOutputStream

class AddContact : AppCompatActivity() {
    private lateinit var binding: ActivityAddContactBinding
    private var imageUri: Uri? = null
    private var imageBitmap : Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    val PICK_IMAGE_REQUEST = 10000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when (intent.getStringExtra("type")) {
            UPDATE_TYPE -> {
                title = "Update Contact"
                binding.etName.setText(intent.getStringExtra("name"))
                binding.etNumber.setText(intent.getStringExtra("number"))
                binding.etEmail.setText(intent.getStringExtra("email"))
                binding.imageViewAddContact.setImageBitmap(base64ToBitmap(intent.getStringExtra("imagePath")!!))
                val id: Int = intent.getIntExtra("id", 0)
                binding.add.text = "Update"
                binding.add.setOnClickListener {
                    val replyIntent = Intent()
                    if (TextUtils.isEmpty(binding.etName.text) || imageUri == null) {
                        setResult(0, replyIntent)
                    } else {
                        val title = binding.etName.text.toString()
                        val desc = binding.etNumber.text.toString()
                        val email = binding.etEmail.text.toString()
                        replyIntent.putExtra(EXTRA_REPLY, title)
                        replyIntent.putExtra(EXTRA_REPLY2, desc)
                        replyIntent.putExtra(EXTRA_REPLY3, email)
                        replyIntent.putExtra(EXTRA_REPLY4, imageUri.toString())
                        replyIntent.putExtra("id", id)
                        setResult(2, replyIntent)
                    }
                    finish()
                }
                binding.selectImage.setOnClickListener {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, PICK_IMAGE_REQUEST)
                }
            }

            else -> {
                title = "Add Contact"
                binding.add.setOnClickListener {
                    val replyIntent = Intent()
                    if (TextUtils.isEmpty(binding.etName.text) || imageUri == null) {
                        setResult(0, replyIntent)
                    } else {
                        val name = binding.etName.text.toString()
                        val number = binding.etNumber.text.toString()
                        val email = binding.etEmail.text.toString()
                        print("name $name")
                        print("number $number")
                        print("email $email")
                        println("imageUri $imageUri")
                        replyIntent.putExtra(EXTRA_REPLY, name)
                        replyIntent.putExtra(EXTRA_REPLY2, number)
                        replyIntent.putExtra(EXTRA_REPLY3, email)
                        replyIntent.putExtra(EXTRA_REPLY4, imageUri.toString())
                        setResult(Activity.RESULT_OK, replyIntent)
                    }
                    finish()
                }
                binding.selectImage.setOnClickListener {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, PICK_IMAGE_REQUEST)
                }
            }
        }

    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val imageView = findViewById<ImageView>(R.id.imageViewAddContact)
            imageUri = data?.data
//            imageView.setImageURI(imageUri)

            imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
            val newWidth = imageBitmap.width / 10
            val newHeight = imageBitmap.height / 10
            val resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, newWidth, newHeight, true)
            imageUri = bitmapToBase64(resizedBitmap).toUri()
            imageView.setImageBitmap(resizedBitmap)


        }
    }
    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }
    fun base64ToBitmap(base64String: String): Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
    companion object {
        const val EXTRA_REPLY = "com.example.android.contact.REPLY"
        const val EXTRA_REPLY2 = "com.example.android.contact.REPLY2"
        const val EXTRA_REPLY3 = "com.example.android.contact.REPLY3"
        const val EXTRA_REPLY4 = "com.example.android.contact.REPLY4"
        const val UPDATE_TYPE = "update"
        const val ADD_TYPE = "add"
    }


}