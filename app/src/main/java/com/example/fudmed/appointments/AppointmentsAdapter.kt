package com.example.fudmed.appointments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fudmed.bookappointment.Modd
import com.example.fudmed.databinding.AppointmentItemBinding
import com.example.fudmed.register.DoctorModel

class AppointmentsAdapter(var data : ArrayList<Modd>, var listener : ClickListener) : RecyclerView.Adapter<AppointmentsAdapter.ClassItem>() {

    init {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassItem {
        return  ClassItem.from(parent)
    }

    override fun onBindViewHolder(holder: ClassItem, position: Int) {
        val item = data[position]
        holder.bind(item,listener)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    class ClickListener(val clickListener : (classname:String) -> Unit){
        fun onCLick(doctor : Modd){
            clickListener(doctor.description)
        }
    }

    class ClassItem (var binding : AppointmentItemBinding ) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : Modd, listener: ClickListener){
            binding.doctorItem = item
            binding.clicks = listener
            binding.groupName.setOnClickListener {
         }
     }

        companion object {
            fun from(parent: ViewGroup) : ClassItem {
                val inflater = LayoutInflater.from(parent.context)
                val binding = AppointmentItemBinding.inflate(inflater,parent,false)
                return ClassItem(binding)
            }
        }
    }

}



