package com.example.assignment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.adapter.SearchAdapter
import com.example.assignment.api.Country
import com.example.assignment.api.RetrofitInstance
import com.example.assignment.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private lateinit var layout: LinearLayoutManager
    private lateinit var query: String
    private lateinit var listOfCountry: MutableList<Country>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        listOfCountry = mutableListOf()
        adapter = SearchAdapter(this, listOfCountry)
        recyclerView.adapter = adapter
        query = binding.search.text.toString()

        binding.searchBtn.setOnClickListener {
            listOfCountry.clear()
            layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            query = binding.search.text.toString()

            if (isNetworkConnected()) {
                retrieveData(query)
            } else {
                AlertDialog.Builder(this).setTitle("No Internet Connection")
                    .setMessage("Please check your internet connection and try again")
                    .setPositiveButton(R.string.ok) { _, _ -> }
                    .setIcon(R.drawable.baseline_assignment_late_24).show()
            }
        }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork

        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun retrieveData(query: String) {

        //Handle exceptions if any
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(this).setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(R.string.ok) { _, _ -> }
                .setIcon(R.drawable.baseline_assignment_late_24).show()
        }

        //get data from response and  add to list
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            val response = RetrofitInstance().getDetails(query)
            response.country.let {
                for(item in it){
                    listOfCountry.add(item)
                }
            }
            adapter = SearchAdapter(this@MainActivity, listOfCountry)
            recyclerView.layoutManager = layout
            adapter.notifyDataSetChanged()
        }
    }
}