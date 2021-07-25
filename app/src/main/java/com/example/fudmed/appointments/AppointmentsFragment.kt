package com.example.fudmed.appointments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.fudmed.R
import com.example.fudmed.bookappointment.Modd
import com.example.fudmed.databinding.FragmentAppointmentsBinding
import com.example.fudmed.home.adoctor.BottomSheetDialogeClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AppointmentsFragment : Fragment() {

    private lateinit var binding: FragmentAppointmentsBinding

    // database Init
    private val database: DatabaseReference by lazy {
        Firebase.database.reference
    }
    private val userAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    var listOfDataClasses = ArrayList<Modd>()

    override fun onResume() {
        super.onResume()
        listOfDataClasses.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        looping()
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointments, container, false)
        return binding.root
    }


    fun  looping (){
        database.child("Appointments").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(mydataSnapshot: DataSnapshot) {
                mydataSnapshot.children.forEach {
                    val date =  it.child("date").value.toString()
                    val time =  it.child("time").value.toString()
                    val description =  it.child("description").value.toString()

                    val classModel = Modd(date,time,description)
                    //Log.d("testing_groups",classModel.groupDescription)
                    listOfDataClasses.add(classModel)
                    Log.d("testing", listOfDataClasses[0].description)
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE


                    val adapter = AppointmentsAdapter(listOfDataClasses, AppointmentsAdapter.ClickListener{ classId ->
                        /*val sheet = BottomSheetDialogeClass(classId)
                        sheet.show(requireActivity().supportFragmentManager,"testing")*/
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