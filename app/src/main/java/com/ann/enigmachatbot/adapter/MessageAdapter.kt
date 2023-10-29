package com.ann.enigmachatbot.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ann.enigmachatbot.R
import com.ann.enigmachatbot.model.MessageModel
import com.bumptech.glide.Glide

class MessageAdapter(val list : ArrayList<MessageModel>) : Adapter<MessageAdapter.MessageViewHolder>() {


    inner class MessageViewHolder(itemView: View) : ViewHolder(itemView){

        val msgText = itemView.findViewById<TextView>(R.id.showMessage)
        val imageContainer  = itemView.findViewById<LinearLayout>(R.id.imageCard)
        val image = itemView.findViewById<ImageView>(R.id.enigmaImage)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        val layoutId = if (viewType == 0) { // 0 means user, 1 means enigma
            R.layout.right_chat_cardview
        } else {
            R.layout.left_chat_cardview
        }

        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val message = list[position]
        return if(message.isUser) 0 else 1
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {

        val msg = list[position]

        if (!msg.isUser) {
            holder.imageContainer.visibility = View.GONE

        }
        if (msg.isImage) {
            holder.imageContainer.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(msg.message)
                .error(R.drawable.bot_img)
                .into(holder.image)

        } else {
            holder.msgText.text = msg.message
        }

    }
}