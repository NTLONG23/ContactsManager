package com.example.contacts

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.model.Contact

class ContactAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactComparator()) {

    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
        fun onCallClick(position: Int)
        fun onMessageClick(position: Int)
        fun onItemClick(position: Int)
    }

    fun submitSearchedList(list: List<Contact>) {
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    fun getContact(position: Int): Contact {
        return getItem(position)
    }

    class ContactViewHolder(itemView: View, private val listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvName)
        private val number: TextView = itemView.findViewById(R.id.tvNumber)
        private val email: TextView = itemView.findViewById(R.id.tvEmail)
        private val avatar: ImageView = itemView.findViewById(R.id.imageView)
        private val editImageView: ImageView = itemView.findViewById(R.id.ivEdit)
        private val deleteImageView: ImageView = itemView.findViewById(R.id.ivDelete)
        private val callImageView: ImageView = itemView.findViewById(R.id.ivCall)
        private val messageImageView: ImageView = itemView.findViewById(R.id.ivMessage)


        fun bind(contact: Contact) {
            name.text = contact.name
            number.text = contact.number
            email.text = contact.email
//            avatar.setImageURI(Uri.parse(contact.imagePath))
            val imageBitmap : Bitmap = base64ToBitmap(contact.imagePath)
            avatar.setImageBitmap(imageBitmap)
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
            editImageView.setOnClickListener {
                listener.onEditClick(adapterPosition)
            }

            deleteImageView.setOnClickListener {
                listener.onDeleteClick(adapterPosition)
            }

            callImageView.setOnClickListener {
                listener.onCallClick(adapterPosition)
            }

            messageImageView.setOnClickListener {
                listener.onMessageClick(adapterPosition)
            }

        }
        fun base64ToBitmap(base64String: String): Bitmap {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        }

        companion object {
            fun create(parent: ViewGroup, listener: OnItemClickListener): ContactViewHolder {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.each_rv, parent, false)
                return ContactViewHolder(view, listener)
            }
        }
    }

    class ContactComparator : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.name == newItem.name && oldItem.number == newItem.number && oldItem.email == newItem.email && oldItem.imagePath == newItem.imagePath
        }
    }

}
