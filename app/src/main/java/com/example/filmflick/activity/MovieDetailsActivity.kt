package com.example.filmflick.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.filmflick.databinding.ActivityMovieDetailsBinding

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }

        binding.backButton.setOnClickListener {
            finish() // Sayfayı kapat ve geri dön
        }
    }


    private fun populateDetails(extras: Bundle) {
        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(binding.movieBackdrop) // Doğru ID kullanıldı
        }

        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(binding.moviePoster) // Doğru ID kullanıldı
        }

        binding.movieTitle.text = extras.getString(MOVIE_TITLE, "")
        binding.movieRating.rating = extras.getFloat(MOVIE_RATING, 0f) / 2 // Hata düzeltildi
        binding.movieReleaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        binding.movieOverview.text = extras.getString(MOVIE_OVERVIEW, "")
    }
}
