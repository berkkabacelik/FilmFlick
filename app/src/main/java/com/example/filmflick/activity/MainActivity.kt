package com.example.filmflick.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmflick.R
import com.example.filmflick.databinding.ActivityMainBinding
import com.example.filmflick.model.Movie
import com.example.filmflick.service.MoviesRepository
import com.example.filmflick.ui.adapter.MoviesAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager

    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager

    private lateinit var upComingMoviesAdapter: MoviesAdapter
    private lateinit var upComingMoviesLayoutMgr: LinearLayoutManager

    private var topRatedMoviesPage = 1
    private var popularMoviesPage = 1
    private var upComingMoviesPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        popularMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topRatedMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        upComingMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        binding.popularMovies.adapter = popularMoviesAdapter

        binding.topRatedMovies.layoutManager = topRatedMoviesLayoutMgr
        topRatedMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        binding.topRatedMovies.adapter = topRatedMoviesAdapter

        binding.upcomingMovies.layoutManager = upComingMoviesLayoutMgr
        upComingMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        binding.upcomingMovies.adapter = upComingMoviesAdapter

        getPopularMovies()
        getTopRatedMovies()
        getUpComingMovies()
    }

    private fun getPopularMovies() {
        MoviesRepository.getPopularMovies(popularMoviesPage, onSuccess = ::onPopularMoviesFetched, onError = ::onError)
    }

    private fun getTopRatedMovies() {
        MoviesRepository.getTopRatedMovies(topRatedMoviesPage, onSuccess = ::onTopRatedMoviesFetched, onError = ::onError)
    }
    private fun getUpComingMovies() {
        MoviesRepository.getUpComingMovies(upComingMoviesPage, onSuccess = ::onUpComingMoviesFetched, onError = ::onError)
    }

    private fun attachUpComingMoviesOnScrollListener(){
        binding.upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upComingMoviesLayoutMgr.itemCount
                val visibleItemCount = upComingMoviesLayoutMgr.childCount
                val firstVisibleItem = upComingMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    binding.upcomingMovies.removeOnScrollListener(this)
                    upComingMoviesPage++
                    getUpComingMovies()
                }
            }
        })
    }


    private fun attachTopRatedMoviesOnScrollListener(){
        binding.topRatedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedMoviesLayoutMgr.itemCount
                val visibleItemCount = topRatedMoviesLayoutMgr.childCount
                val firstVisibleItem = topRatedMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    binding.topRatedMovies.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    getTopRatedMovies()
                }
            }
        })
    }

    private fun attachPopularMoviesOnScrollListener() {
        binding.popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutMgr.itemCount
                val visibleItemCount = popularMoviesLayoutMgr.childCount
                val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    binding.popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }

    private fun onUpComingMoviesFetched(movies: List<Movie>) {
        upComingMoviesAdapter.appendMovies(movies)
        attachUpComingMoviesOnScrollListener()
    }


    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
    }


    private fun onTopRatedMoviesFetched(movies: List<Movie>) {
        topRatedMoviesAdapter.appendMovies(movies)
        attachTopRatedMoviesOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }



}
