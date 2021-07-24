package com.example.fudmed.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fudmed.R
import com.example.fudmed.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    // database Init
    private val database: DatabaseReference by lazy {
        Firebase.database.reference
    }
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    lateinit var email: String
    lateinit  var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener {
            email = binding.email.text.toString()
            password = binding.password.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are Mandatory", Toast.LENGTH_SHORT).show()
            } else {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            Log.d("eshoo", "signInUserWithEmail:success")
                            whoAreYou(firebaseAuth.currentUser.uid)
                        } else {
                            Log.d("eshoo", "signInUserWithEmail:failure", task.exception)
                            Toast.makeText(requireContext(), "Login failed" + task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }

        binding.doctor.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerDoctorFragment)
        }
        binding.patient.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerPatientFragment)
        }
    }

    fun whoAreYou(id: String) {
        Log.d("eshoo", "am in whoAreYou method")
            database.child("Doctors Information").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (postSnapshot in dataSnapshot.children) {
                        if (dataSnapshot.hasChild(id)) {

                            // you are a Lecturer
                            Toast.makeText(requireContext(), "hello Doctor", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                            break
                        } else {

                            // you are a Student
                            Toast.makeText(requireContext(), "hello Patient", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            break
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Toast.makeText(requireContext(), databaseError.message, Toast.LENGTH_SHORT).show()
                    Log.w("TAG", "loadPost:onCancelled", databaseError.toException())

                }
            })
        }

    }