package com.example.movies.ui.view

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.movies.R
import com.example.movies.data.database.Movie
import com.example.movies.data.database.MovieDb
import com.example.movies.data.database.Serie
import com.example.movies.data.model.MovieModel
import com.example.movies.data.model.SerieModel
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.ui.viewmodel.*

class MainActivity : AppCompatActivity(), MovieAdapter.OnMovieClickListener,SerieAdapter.OnSerieClickListener,SearchView.OnQueryTextListener,
    AdapterView.OnItemClickListener {

    private lateinit var  database: MovieDb

    private lateinit var binding: ActivityMainBinding

    private val moviePopularViewModel : MoviePopularViewModel by viewModels()
    private val movieTopRatedViewModel : MovieTopRatedViewModel by viewModels()
    private val movieSearchViewModel : MovieSearchViewModel by viewModels()
    private lateinit var adapterMovie:MovieAdapter
    private val moviesInit = mutableListOf<MovieModel>()

    private val seriePopularViewModel : SeriePopularViewModel by viewModels()
    private val serieTopRatedViewModel : SerieTopRatedViewModel by viewModels()
    private val serieSearchViewModel : SerieSearchViewModel by viewModels()
    private lateinit var adapterSerie:SerieAdapter
    private val seriesInit = mutableListOf<SerieModel>()


    private var typeFilter = "Movies"
    private var optionFilter = "Popular"
    private var currentPage = 1



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //DB
        database = Room.databaseBuilder(
            application,MovieDb::class.java,MovieDb.DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
        //DB


        // Get item for bind options of filter
        val optionsFilter = resources.getStringArray(R.array.options_of_filter)
        val adapterOptionsFilter = ArrayAdapter(
            this,
            R.layout.options_filter,
            optionsFilter
        )
        with(binding.acOptionsFilter){
            setAdapter(adapterOptionsFilter)
            onItemClickListener = this@MainActivity
        }
        // Get item for bind options of filter

        binding.svMovies.setOnQueryTextListener(this)
        binding.tvOptionFilterMovies.paintFlags = binding.tvOptionFilterMovies.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.rvMovies.layoutManager = GridLayoutManager(this,3)

        getDataApi()

        binding.rvMovies.addOnScrollListener(object:RecyclerView.OnScrollListener(){

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onScrolled(recyclerView: RecyclerView, dx:Int, dy:Int){
                val lastCompletelyVisibleItem: Int = (binding.rvMovies.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                val firstCompleteVisibleItem:Int = (binding.rvMovies.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition()


                    if(dy<=-10 && dy!=0 && firstCompleteVisibleItem == 3){
                        if(currentPage>1){
                            currentPage -=1
                            getDataApi()
                            binding.tvCurrentPage.text = "Page "+currentPage.toString()
                        }
                    }

                    if(dy>=10 && lastCompletelyVisibleItem == 19){
                        currentPage +=1
                        getDataApi()
                        binding.tvCurrentPage.text = "Page "+currentPage.toString()
                    }


            }
        })


        // Loadings
        moviePopularViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })
        movieTopRatedViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })
        movieSearchViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })

        seriePopularViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })
        serieTopRatedViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })
        serieSearchViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })
        // Loadings



        // Detect click in option Movies
        binding.tvOptionFilterMovies.setOnClickListener{
            typeFilter = "Movies"
            currentPage = 1
            binding.tvOptionFilterMovies.paintFlags = binding.tvOptionFilterMovies.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            binding.tvOptionFilterSeries.paintFlags = binding.tvOptionFilterSeries.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
            getDataApi()
            binding.tvCurrentPage.text = "Page "+currentPage.toString()

        }
        // Detect click in option Movies

        // Detect click in option Series
        binding.tvOptionFilterSeries.setOnClickListener{
            typeFilter = "Series"
            currentPage = 1
            binding.tvOptionFilterSeries.paintFlags = binding.tvOptionFilterSeries.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            binding.tvOptionFilterMovies.paintFlags = binding.tvOptionFilterMovies.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
            getDataApi()
            binding.tvCurrentPage.text = "Page "+currentPage.toString()
        }
        // Detect click in option Series

    }


    override fun onMovieClick(movie: MovieModel) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("id",movie.id)
        intent.putExtra("overview",movie.overview)
        intent.putExtra("poster_path",movie.poster_path)
        intent.putExtra("release_date",movie.release_date)
        intent.putExtra("title",movie.title)
        intent.putExtra("vote_average",movie.vote_average)
        intent.putExtra("vote_count",movie.vote_count)
        startActivity(intent)
    }

    override fun onSerieClick(serie: SerieModel) {
        val intent = Intent(this, SerieDetailActivity::class.java)
        intent.putExtra("id",serie.id)
        intent.putExtra("overview",serie.overview)
        intent.putExtra("poster_path",serie.poster_path)
        intent.putExtra("first_air_date",serie.first_air_date)
        intent.putExtra("title",serie.name)
        intent.putExtra("vote_average",serie.vote_average)
        intent.putExtra("vote_count",serie.vote_count)
        startActivity(intent)
    }


    // Listener of optionFilter (Popular, Top Rated)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position).toString()
        optionFilter = item
        currentPage = 1
        getDataApi()
        binding.tvCurrentPage.text = "Page "+currentPage.toString()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            currentPage = 1

            if(typeFilter == "Movies")
            {
                if(isConnected(this))
                {
                    movieSearchViewModel.onCreate(this.getString(R.string.api_key_themovie),currentPage,query)
                    movieSearchViewModel.movieResponse.observe(this, Observer{ currentMovies ->
                        moviesInit.clear()
                        moviesInit.addAll(currentMovies.results as Collection<MovieModel>)
                        adapterMovie = MovieAdapter(moviesInit, this@MainActivity)
                        binding.rvMovies.adapter = adapterMovie
                    })
                }
                else
                {
                    //Use get data of SQL Lite
                    Toast.makeText(this,"Offline",Toast.LENGTH_SHORT).show()
                    var movieLocal = database.movieDao.getMoviesBySearchTitle(query)
                    val listMovieModel = mutableListOf<MovieModel>()
                    movieLocal.forEach{movieLocal ->
                        val movieLocalTmp = MovieModel(
                            movieLocal.id,
                            movieLocal.overview,
                            movieLocal.poster_path,
                            movieLocal.release_date,
                            movieLocal.title,
                            movieLocal.vote_average,
                            movieLocal.vote_count
                        )
                        listMovieModel.add(movieLocalTmp)
                    }
                    moviesInit.clear()
                    moviesInit.addAll(listMovieModel as Collection<MovieModel>)
                    adapterMovie = MovieAdapter(moviesInit,this)
                    binding.rvMovies.adapter = adapterMovie
                }

            }
            else if(typeFilter == "Series")
            {

                if(isConnected(this))
                {
                    serieSearchViewModel.onCreate(this.getString(R.string.api_key_themovie),currentPage,query)
                    serieSearchViewModel.serieResponse.observe(this, Observer{ currentSeries ->
                        seriesInit.clear()
                        seriesInit.addAll(currentSeries.results as Collection<SerieModel>)
                        adapterSerie = SerieAdapter(seriesInit, this@MainActivity)
                        binding.rvMovies.adapter = adapterSerie
                    })
                }
                else
                {
                    //Use get data of SQL Lite
                    Toast.makeText(this,"Offline",Toast.LENGTH_SHORT).show()
                    var serieLocal = database.serieDao.getSeriesBySearchName(query)
                    val listSerieModel = mutableListOf<SerieModel>()
                    serieLocal.forEach{serieLocal ->
                        val serieLocalTmp = SerieModel(
                            serieLocal.id,
                            serieLocal.overview,
                            serieLocal.poster_path,
                            serieLocal.first_air_date,
                            serieLocal.name,
                            serieLocal.vote_average,
                            serieLocal.vote_count
                        )
                        listSerieModel.add(serieLocalTmp)
                    }
                    seriesInit.clear()
                    seriesInit.addAll(listSerieModel as Collection<SerieModel>)
                    adapterSerie = SerieAdapter(seriesInit,this)
                    binding.rvMovies.adapter = adapterSerie

                }


            }

            binding.tvCurrentPage.text = "Page "+currentPage.toString()

        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }




    @RequiresApi(Build.VERSION_CODES.M)
    private fun getDataApi(){


            if(typeFilter == "Movies")
            {

                if(isConnected(this))
                {
                    if(optionFilter == "Popular")
                    {
                        //Use get data of API
                        moviePopularViewModel.onCreate(this.getString(R.string.api_key_themovie),currentPage)
                        moviePopularViewModel.movieResponse.observe(this, Observer{ currentMovies ->
                            saveMovies(currentMovies.results,optionFilter)
                            moviesInit.clear()
                            moviesInit.addAll(currentMovies.results as Collection<MovieModel>)
                            adapterMovie = MovieAdapter(moviesInit, this@MainActivity)
                            binding.rvMovies.adapter = adapterMovie
                        })
                        //Use get data of API
                    }
                    else if(optionFilter == "Top Rated")
                    {
                        movieTopRatedViewModel.onCreate(this.getString(R.string.api_key_themovie),currentPage)
                        movieTopRatedViewModel.movieResponse.observe(this@MainActivity, Observer{ currentMovies ->
                            saveMovies(currentMovies.results,optionFilter)
                            moviesInit.clear()
                            moviesInit.addAll(currentMovies.results as Collection<MovieModel>)
                            adapterMovie = MovieAdapter(moviesInit, this@MainActivity)
                            binding.rvMovies.adapter = adapterMovie
                        })
                    }
                }
                else
                {
                        //Use get data of SQL Lite
                        binding.progress.isVisible = true
                        Toast.makeText(this,"Offline",Toast.LENGTH_SHORT).show()
                        var movieLocal = database.movieDao.getMoviesByOptionFilter(optionFilter)
                        val listMovieModel = mutableListOf<MovieModel>()
                        movieLocal.forEach{movieLocal ->
                            val movieLocalTmp = MovieModel(
                                movieLocal.id,
                                movieLocal.overview,
                                movieLocal.poster_path,
                                movieLocal.release_date,
                                movieLocal.title,
                                movieLocal.vote_average,
                                movieLocal.vote_count
                            )
                            listMovieModel.add(movieLocalTmp)
                        }
                        moviesInit.clear()
                        moviesInit.addAll(listMovieModel as Collection<MovieModel>)
                        adapterMovie = MovieAdapter(moviesInit,this)
                        binding.rvMovies.adapter = adapterMovie
                    binding.progress.isVisible = false
                }

            }
            else if(typeFilter == "Series")
            {

                if(isConnected(this))
                {
                    if(optionFilter == "Popular")
                    {
                        //Use get data of API
                        seriePopularViewModel.onCreate(this.getString(R.string.api_key_themovie),currentPage)
                        seriePopularViewModel.serieResponse.observe(this, Observer{ currentSeries ->
                            saveSeries(currentSeries.results,optionFilter)
                            seriesInit.clear()
                            seriesInit.addAll(currentSeries.results as Collection<SerieModel>)
                            adapterSerie = SerieAdapter(seriesInit, this@MainActivity)
                            binding.rvMovies.adapter = adapterSerie
                        })
                        //Use get data of API
                    }
                    else if(optionFilter == "Top Rated")
                    {
                        serieTopRatedViewModel.onCreate(this.getString(R.string.api_key_themovie),currentPage)
                        serieTopRatedViewModel.serieResponse.observe(this@MainActivity, Observer{ currentSeries ->
                            saveSeries(currentSeries.results,optionFilter)
                            seriesInit.clear()
                            seriesInit.addAll(currentSeries.results as Collection<SerieModel>)
                            adapterSerie = SerieAdapter(seriesInit, this@MainActivity)
                            binding.rvMovies.adapter = adapterSerie
                        })
                    }
                }
                else
                {
                    //Use get data of SQL Lite
                    binding.progress.isVisible = true
                    Toast.makeText(this,"Offline",Toast.LENGTH_SHORT).show()
                    var serieLocal = database.serieDao.getSeriesByOptionFilter(optionFilter)
                    val listSerieModel = mutableListOf<SerieModel>()
                    serieLocal.forEach{serieLocal ->
                        val serieLocalTmp = SerieModel(
                            serieLocal.id,
                            serieLocal.overview,
                            serieLocal.poster_path,
                            serieLocal.first_air_date,
                            serieLocal.name,
                            serieLocal.vote_average,
                            serieLocal.vote_count
                        )
                        listSerieModel.add(serieLocalTmp)
                    }
                    seriesInit.clear()
                    seriesInit.addAll(listSerieModel as Collection<SerieModel>)
                    adapterSerie = SerieAdapter(seriesInit,this)
                    binding.rvMovies.adapter = adapterSerie
                    binding.progress.isVisible = false
                }

            }


    }


    //Save data
    private fun saveMovies(currentMovies:Collection<MovieModel>,optionFilter:String){
        currentMovies.forEach{
            val movieTmp = Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                poster_path = it.poster_path,
                release_date = it.release_date,
                vote_average = it.vote_average,
                vote_count = it.vote_count,
                option_filter = optionFilter
            )
            database.movieDao.insert(movieTmp)
        }
    }
    private fun saveSeries(currentSeries:Collection<SerieModel>,optionFilter:String){
        currentSeries.forEach{
            val serieTmp = Serie(
                id = it.id,
                name = it.name,
                overview = it.overview,
                poster_path = it.poster_path,
                first_air_date = it.first_air_date,
                vote_average = it.vote_average,
                vote_count = it.vote_count,
                option_filter = optionFilter
            )
            database.serieDao.insert(serieTmp)
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


}