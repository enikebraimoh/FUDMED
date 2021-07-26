package com.example.fudmed.home.adoctor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentOnAttachListener
import androidx.navigation.fragment.findNavController
import com.example.fudmed.R
import com.example.fudmed.databinding.BottomsheetDialogueBinding
import com.example.fudmed.home.HomeFragment
import com.example.fudmed.home.HomeFragmentDirections
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialogeClass(val email : String,val name : String) : BottomSheetDialogFragment() {

    lateinit var binding : BottomsheetDialogueBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding = DataBindingUtil.inflate(inflater, R.layout.bottomsheet_dialogue,container,false)
       binding.doctorname.text = name

        binding.bookAnAppointment.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_bookAnAppointmentFragment)
            dismiss()
        }

        binding.consultMe.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToConsultMeFragment(email))
            dismiss()
        }

        return binding.root
    }

}