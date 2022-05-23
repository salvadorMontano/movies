package com.example.movies.ui.view


import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.movies.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import org.w3c.dom.Text

class MovieDetailVideoActivity : YouTubeBaseActivity() {



    var YOUTUBE_API_KEY =  ""


    private var videoSiteItem : String = ""
    private var videoKeyItem : String = ""
    private var videoNameItem : String = ""
    private var videoPublishedAtItem : String = ""
    private var videoTypeItem : String = ""


    private lateinit var youtubePlayer: YouTubePlayerView
    private lateinit var btnPlay: Button

    private lateinit var nameItem: TextView
    private lateinit var publishedAtItem: TextView
    private lateinit var siteItem: TextView
    private lateinit var typeItem: TextView


    lateinit var youtubePlayerInit : YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail_video)

        YOUTUBE_API_KEY = this.getString(R.string.api_key_youtube)

        if(intent.extras != null){
            videoKeyItem = intent.getStringExtra("key") ?:""
            videoSiteItem = intent.getStringExtra("site") ?:""
            videoNameItem = intent.getStringExtra("name") ?:""
            videoPublishedAtItem = intent.getStringExtra("published") ?:""
            videoTypeItem = intent.getStringExtra("type") ?:""
        }

        youtubePlayer = findViewById(R.id.youtubePlayer)
        btnPlay = findViewById(R.id.btnPlay)
        nameItem = findViewById(R.id.videoNameItem)
        publishedAtItem = findViewById(R.id.videoPublishedAtItem)
        siteItem = findViewById(R.id.videoSiteItem)
        typeItem = findViewById(R.id.videoTypeItem)


        var dataDate : List<String> = videoPublishedAtItem.split("T")
        videoPublishedAtItem = dataDate[0].toString()

        nameItem.text = videoNameItem
        publishedAtItem.text = videoPublishedAtItem
        siteItem.text = videoSiteItem
        typeItem.text = videoTypeItem

        youtubePlayerInit = object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(videoKeyItem,5)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
              Toast.makeText(applicationContext,"Failed",Toast.LENGTH_SHORT).show()
            }

        }

        youtubePlayer.initialize(YOUTUBE_API_KEY,youtubePlayerInit)


        btnPlay.setOnClickListener{
            onBackPressed()
            //youtubePlayer.initialize(YOUTUBE_API_KEY,youtubePlayerInit)
        }


    }
}