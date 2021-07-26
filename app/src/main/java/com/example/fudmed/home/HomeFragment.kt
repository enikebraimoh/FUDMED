package com.example.fudmed.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.fudmed.R
import com.example.fudmed.databinding.FragmentHomeBinding
import com.example.fudmed.home.adoctor.BottomSheetDialogeClass
import com.example.fudmed.register.DoctorModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment(){

    private lateinit var binding: FragmentHomeBinding

    var listOfDataClasses = ArrayList<DoctorModel>()

    override fun onResume() {
        super.onResume()
        listOfDataClasses.clear()
    }


    // database Init
    private val database: DatabaseReference by lazy {
        Firebase.database.reference
    }
    private val userAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        looping()
        binding.shimmerViewContainer.startShimmer()
        binding.image.setOnClickListener {
            userAuth.signOut()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
        return binding.root
    }

    fun  looping (){
        database.child("Doctors Information").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(mydataSnapshot: DataSnapshot) {
                mydataSnapshot.children.forEach {
                    val email =  it.child("email").value.toString()
                    val name =  it.child("fullName").value.toString()
                    val gender =  it.child("gender").value.toString()
                    val location =  it.child("location").value.toString()
                    val phoneNumber =  it.child("phoneNumber").value.toString()
                    val doctorOccupation =  it.child("occupation").value.toString()
                    val title =  it.child("title").value.toString()
                    val password =  it.child("password").value.toString()

                    val classModel = DoctorModel(name,phoneNumber,email,title,doctorOccupation,location,gender,password)
                    //Log.d("testing_groups",classModel.groupDescription)
                    listOfDataClasses.add(classModel)
                    Log.d("testing", listOfDataClasses[0].FullName)
                   binding.shimmerViewContainer.stopShimmer()
                   binding.shimmerViewContainer.visibility = View.GONE


                    var adapter = HomeAdapter(listOfDataClasses,HomeAdapter.ClickListener{ classId ->
                       val sheet = BottomSheetDialogeClass(classId)
                        sheet.show(requireActivity().supportFragmentManager,"testing")
                    })

                    binding.reyclerview.adapter = adapter

                }

                }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                // invalid class
                Log.d("testing", "failed")
            }
        })

    }

}