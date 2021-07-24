package com.example.fudmed.register

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
import com.example.fudmed.databinding.FragmentRegisterDoctorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterDoctorFragment : Fragment() {


    private lateinit var binding: FragmentRegisterDoctorBinding

    // database initialization
    private val userAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private var database: DatabaseReference = Firebase.database.reference

    lateinit var fullname: String
    lateinit var phonenumber: String
    lateinit var email: String
    lateinit  var gender: String
    lateinit var title: String
    lateinit var occupation: String
    lateinit var location: String
    lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
                binding.login.setOnClickListener {
                fullname = binding.fullname.text.toString()
                phonenumber = binding.phoneNumber.text.toString()
                email = binding.email.text.toString()
                gender = binding.gender.text.toString()
                title = binding.title.text.toString()
                occupation = binding.occupation.text.toString()
                location = binding.location.text.toString()
                password = binding.password.text.toString()

                if (fullname.isEmpty()||
                    phonenumber.isEmpty()||
                    email.isEmpty()||
                    gender.isEmpty()||
                    title.isEmpty()||
                    occupation.isEmpty()||
                    location.isEmpty()||
                    password.isEmpty()){
                    Toast.makeText(requireContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show()
                }
                else{
            databaseOperations()
               }
        }

    }


    fun databaseOperations() {
        val model = DoctorModel(
            fullname, phonenumber, email, title, occupation, location, gender, password
        )
        userAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d("eshoo", "createUserWithEmail:success")
                    database.child("Doctors Information")
                        .child(userAuth.currentUser?.uid!!).setValue(model)
                    Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerDoctorFragment_to_loginFragment)
                } else {
                    Log.d("eshoo", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(requireContext(), task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
    }

}