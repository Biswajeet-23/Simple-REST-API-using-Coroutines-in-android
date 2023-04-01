package com.example.assignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.api.Country
import com.example.assignment.api.PersonModel
import com.example.assignment.databinding.ItemLayoutBinding

class SearchAdapter(private val context: Context, private val list: MutableList<Country>): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    inner class SearchViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bindData(data: Country) {
            binding.country.text = data.countryId
            binding.probability.text = data.probability
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val data = list[position]
        holder.bindData(data)
    }
}