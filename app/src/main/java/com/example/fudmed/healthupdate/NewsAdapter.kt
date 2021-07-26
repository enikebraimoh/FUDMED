package com.example.fudmed.healthupdate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fudmed.bookappointment.Modd
import com.example.fudmed.databinding.AppointmentItemBinding
import com.example.fudmed.databinding.NewsItemBinding
import com.example.fudmed.register.DoctorModel

class NewsAdapter(var data : ArrayList<NewsModel>) : RecyclerView.Adapter<NewsAdapter.ClassItem>() {

    init {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassItem {
        return  ClassItem.from(parent)
    }

    override fun onBindViewHolder(holder: ClassItem, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    class ClassItem (var binding : NewsItemBinding ) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : NewsModel){
            binding.doctorItem = item
     }

        companion object {
            fun from(parent: ViewGroup) : ClassItem {
                val inflater = LayoutInflater.from(parent.context)
                val binding = NewsItemBinding.inflate(inflater,parent,false)
                return ClassItem(binding)
            }
        }
    }

}



