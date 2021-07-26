package com.example.fudmed.consultme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fudmed.R
import com.example.fudmed.databinding.FragmentConsultMeBinding
import com.example.fudmed.databinding.FragmentHomeBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ConsultMeFragment : Fragment() {

    private lateinit var binding : FragmentConsultMeBinding

    private lateinit var manager: LinearLayoutManager
    lateinit var adapter: MessageAdapter

    // database Init
    private val database: DatabaseReference by lazy {
        Firebase.database.reference
    }
    lateinit var arguments : ConsultMeFragmentArgs
    private lateinit var db: FirebaseDatabase

    private val userAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments = ConsultMeFragmentArgs.fromBundle(requireArguments())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_consult_me, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Firebase.database
        val messagesRef = db.reference.child(MESSAGES_CHILD).child(arguments.doctorname)

        val options = FirebaseRecyclerOptions.Builder<MessageModel>()
            .setQuery(messagesRef, MessageModel::class.java)
            .build()

        adapter = MessageAdapter(options, getUserName())
        binding.progressBar.visibility = ProgressBar.INVISIBLE
        manager = LinearLayoutManager(requireContext())
        manager.stackFromEnd = true

        binding.messageRecyclerView.layoutManager = manager
        binding.messageRecyclerView.adapter = adapter

        // See MyScrollToBottomObserver for details
        adapter.registerAdapterDataObserver(
            MyScrollToBottomObserver(binding.messageRecyclerView, adapter, manager)
        )

        // Disable the send button when there's no text in the input field
        // See MyButtonObserver for details
        binding.messageEditText.addTextChangedListener(MyButtonObserver(binding.sendButton))

        // When the send button is clicked, send a text message
        binding.sendButton.setOnClickListener {
            val friendlyMessage = MessageModel(
                binding.messageEditText.text.toString(),
                getUserName())
            db.reference.child(MESSAGES_CHILD).push().setValue(friendlyMessage)
            binding.messageEditText.setText("")
        }

    }

    private fun getUserName(): String {
        val user = userAuth.currentUser.email
        return if (user == arguments.doctorname) {
           "Doctor"
        }
        else{
            "patient"
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        const val MESSAGES_CHILD = "messages"
        const val ANONYMOUS = "anonymous"
        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
    }

    override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        adapter.startListening()
    }
}