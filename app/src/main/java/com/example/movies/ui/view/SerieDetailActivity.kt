package com.example.movies.ui.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.ActivitySerieDetailBinding
import com.example.movies.ui.viewmodel.SerieVideosViewModel

//import com.example.movies.ui.viewmodel.MovieVideosViewModel

class SerieDetailActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySerieDetailBinding

    private val serieVideosViewModel : SerieVideosViewModel by viewModels()

    private var videoSiteItem : String = ""
    private var videoKeyItem : String = ""
    private var videoNameItem : String = ""
    private var videoPublishedAtItem : String = ""
    private var videoTypeItem : String = ""

    private var idItem : String = ""
    private var titleItem : String = ""
    private var overviewItem : String = ""
    private var posterPathItem : String = ""
    private var firstAirDateItem : String = ""
    private var voteAverageItem : String = ""
    private var voteCountItem : String = ""




    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySerieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val toolbar: Toolbar = findViewById(R.id.toolbarLayout)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Serie"

        if(intent.extras != null){
            posterPathItem = intent.getStringExtra("poster_path") ?:""
            idItem = intent.getIntExtra("id",0).toString()
            titleItem = intent.getStringExtra("title") ?: ""
            overviewItem = intent.getStringExtra("overview") ?: ""
            firstAirDateItem = intent.getStringExtra("first_air_date") ?: ""
            voteAverageItem = intent.getDoubleExtra("vote_average",0.0).toString()
            voteCountItem = intent.getIntExtra("vote_count",0).toString()

        }


        if(isConnected(this))
        {
            //Use get data of API
            serieVideosViewModel.onCreate(idItem,this.getString(R.string.api_key_themovie))
            serieVideosViewModel.serieVideoResponse.observe(this, Observer{ currentSerieVideos ->
                if(currentSerieVideos.results.isNotEmpty()){
                    videoSiteItem = currentSerieVideos.results[0].site
                    videoKeyItem = currentSerieVideos.results[0].key
                    videoNameItem = currentSerieVideos.results[0].name
                    videoPublishedAtItem = currentSerieVideos.results[0].published_at
                    videoTypeItem = currentSerieVideos.results[0].type
                }
            })
            //Use get data of API
        }
        else
        {
            Toast.makeText(this,"Offline", Toast.LENGTH_SHORT).show()
        }


        Glide.with(binding.posterItem.context).load("https://image.tmdb.org/t/p/original/"+posterPathItem).into(binding.posterItem)
        binding.titleItem.text = titleItem
        binding.overviewItem.text = overviewItem
        binding.firstAirDateItem.text = firstAirDateItem
        binding.voteAverageItem.text = voteAverageItem
        binding.voteCountItem.text = voteCountItem


        binding.faVideoItem.setOnClickListener{

            if(videoSiteItem=="YouTube" && videoKeyItem != "")
            {
                val intent = Intent(this, MovieDetailVideoActivity::class.java)
                intent.putExtra("key",videoKeyItem)
                intent.putExtra("site",videoSiteItem)
                intent.putExtra("name",videoNameItem)
                intent.putExtra("published",videoPublishedAtItem)
                intent.putExtra("type",videoTypeItem)
                startActivity(intent)
            }else
            {
                Toast.makeText(this,"No Videos Available",Toast.LENGTH_SHORT).show()
            }

        }



    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}