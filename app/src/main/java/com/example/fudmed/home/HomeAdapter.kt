package com.example.fudmed.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fudmed.databinding.SingleDoctorItemBinding
import com.example.fudmed.register.DoctorModel

class HomeAdapter(var data : ArrayList<DoctorModel>, var listener : ClickListener) : RecyclerView.Adapter<HomeAdapter.ClassItem>() {

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
        fun onCLick(classgroup : DoctorModel){
            clickListener(classgroup.FullName)
        }
    }

    class ClassItem (var binding : SingleDoctorItemBinding ) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : DoctorModel, listener: ClickListener){
            binding.doctorItem = item
            binding.clicks = listener
            binding.groupName.setOnClickListener {

            }

        }

        companion object {
            fun from(parent: ViewGroup) : ClassItem {
                val inflater = LayoutInflater.from(parent.context)
                val binding = SingleDoctorItemBinding.inflate(inflater,parent,false)
                return ClassItem(binding)
            }
        }
    }

}



