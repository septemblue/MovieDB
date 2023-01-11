package com.example.moviecatalog.Main;

/*
Anggota :
Damario - 2440028792
TUASAMU, RAFFAEL HIZQYA BAKHTIAR ALI MAULANA - 2440117122
CHRISTOPHER CHANDRA WIDJAJA - 2440025292
*/

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.example.moviecatalog.API.ApiClient;
import com.example.moviecatalog.API.ApiInterface;
import com.example.moviecatalog.API.Response;
import com.example.moviecatalog.API.Result;
import com.example.moviecatalog.MovieAdapter.MovieAdapter;
import com.example.moviecatalog.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    //https://api.themoviedb.org/3/movie/popular?api_key=6e307e1ef79d40e161c9e94a5c818300&language=en-US&page=1

    private MovieAdapter adapter;
    private SearchView searchView;
    String API_KEY = "6e307e1ef79d40e161c9e94a5c818300";
    String LANGUAGE = "en-US";
    String CATEGORY = "popular";
    int PAGE = 1;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvMovies);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getRetrofit();
    }

    private void getRetrofit(){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Response> call = apiInterface.getMovies(CATEGORY, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                List<Result> mList = response.body().getResults();
                adapter = new MovieAdapter(MainActivity.this,mList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() > 1){
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<Response> call = apiInterface.getQuery(API_KEY, LANGUAGE, newText, PAGE);
                    call.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            List<Result> mList = response.body().getResults();
                            adapter = new MovieAdapter(MainActivity.this,mList);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {

                        }
                    });
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}