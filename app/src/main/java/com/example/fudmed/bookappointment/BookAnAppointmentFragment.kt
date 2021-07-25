package com.example.fudmed.bookappointment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.fudmed.R
import com.example.fudmed.databinding.FragmentBookAnAppointmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BookAnAppointmentFragment : Fragment() {

    // database Init
    private val database: DatabaseReference by lazy {
        Firebase.database.reference
    }
    private val userAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    lateinit var name: String
    lateinit var date: String
    lateinit var time: String
    lateinit  var symthoms: String
    lateinit var reason: String

    private lateinit var binding: FragmentBookAnAppointmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_book_an_appointment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.book.setOnClickListener {
            name = binding.name.text.toString()
            date = binding.date.text.toString()
            time = binding.time.text.toString()
            symthoms = binding.syntoms.text.toString()
            reason = binding.reason.text.toString()

            if (name.isEmpty()||
                date.isEmpty()||
                time.isEmpty()||
                symthoms.isEmpty()||
                reason.isEmpty()){
                Toast.makeText(requireContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
            else{
                databaseOperations()
            }
        }

    }

    fun databaseOperations() {
        val model = Modd(date, time, reason)
                    database.child("Appointments").child(userAuth.currentUser?.uid!!).setValue(model)
                    Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_bookAnAppointmentFragment_to_homeFragment)

        }

    }