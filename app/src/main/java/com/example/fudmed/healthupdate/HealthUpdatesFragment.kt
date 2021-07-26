package com.example.fudmed.healthupdate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.fudmed.R
import com.example.fudmed.appointments.AppointmentsAdapter
import com.example.fudmed.bookappointment.Modd
import com.example.fudmed.databinding.FragmentAppointmentsBinding
import com.example.fudmed.databinding.FragmentHealthUpdatesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HealthUpdatesFragment : Fragment() {

    private lateinit var binding: FragmentHealthUpdatesBinding

    // database Init
    private val database: DatabaseReference by lazy {
        Firebase.database.reference
    }
    private val userAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    var listOfDataClasses = ArrayList<NewsModel>()

    override fun onResume() {
        super.onResume()
        listOfDataClasses.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        looping()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_health_updates, container, false)
        binding.shimmerViewContainer.startShimmer()
        return binding.root
    }


    fun  looping (){
        database.child("News Updates").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(mydataSnapshot: DataSnapshot) {
                mydataSnapshot.children.forEach {
                    val imageUrl =  it.child("imageUrl").value.toString()
                    val Title =  it.child("Title").value.toString()
                    val Description =  it.child("Description").value.toString()

                    val classModel = NewsModel(imageUrl,Title,Description)
                    //Log.d("testing_groups",classModel.groupDescription)
                    listOfDataClasses.add(classModel)
                    Log.d("testing", listOfDataClasses[0].Title)

                    val adapter = NewsAdapter(listOfDataClasses)
                    binding.reyclerview.adapter = adapter
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE

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