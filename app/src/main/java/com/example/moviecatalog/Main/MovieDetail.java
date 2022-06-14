package com.example.moviecatalog.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviecatalog.API.Result;
import com.example.moviecatalog.R;

public class MovieDetail extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    TextView movieTitle, movieDescription;
    String title, description, poster;
    ImageView moviePoster;
    Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieTitle = findViewById(R.id.movieTitle);
        movieDescription = findViewById(R.id.movieDescription);
        moviePoster = findViewById(R.id.movieImage);

        result = getIntent().getParcelableExtra(EXTRA_MOVIE);

        title = result.getOriginalTitle();
        description = result.getOverview();
        poster = result.getPosterPath();

        movieTitle.setText(title);
        movieDescription.setText(description);
        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w185"+ poster)
                .into(moviePoster);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return super.onSupportNavigateUp();
    }
}