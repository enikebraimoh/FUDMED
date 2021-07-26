package com.example.fudmed.consultme

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fudmed.R
import com.example.fudmed.consultme.ConsultMeFragment.Companion.ANONYMOUS
import com.example.fudmed.databinding.MessageBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MessageAdapter(
    private val options: FirebaseRecyclerOptions<MessageModel>,
    private val currentUserName: String?) : FirebaseRecyclerAdapter<MessageModel, RecyclerView.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.message, parent, false)
            val binding = MessageBinding.bind(view)
           return MessageViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, model: MessageModel) {
         return  (holder as MessageViewHolder).bind(options.snapshots[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (options.snapshots[position].text != null) VIEW_TYPE_TEXT else VIEW_TYPE_IMAGE
    }

    inner class MessageViewHolder(private val binding: MessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MessageModel) {
            binding.messageTextView.text = item.text

            binding.messengerTextView.text = if (item.name == null) ANONYMOUS else item.name
            if (item.name == currentUserName){
                binding.messengerTextView.text = "Me"
            }
                binding.messengerImageView.setImageResource(R.drawable.ic_baseline_person)
        }
    }

    companion object {
        const val TAG = "MessageAdapter"
        const val VIEW_TYPE_TEXT = 1
        const val VIEW_TYPE_IMAGE = 2
    }
}
