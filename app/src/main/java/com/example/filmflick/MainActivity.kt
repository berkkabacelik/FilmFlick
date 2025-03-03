package com.example.filmflick

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.filmflick.model.Movie
import com.example.filmflick.service.MoviesRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MoviesRepository.getPopularMovies(
            onSuccess = :: onPopularMoviesFetched,
            onError = :: onError    // ::  bir sınıf veya fonksiyondan referans almak için kullanılır.

        )

        }

    private fun onPopularMoviesFetched(movies: List<Movie>){

        Log.d("MainActivity", "Movies: $movies")
    }

    private fun onError(){
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

}