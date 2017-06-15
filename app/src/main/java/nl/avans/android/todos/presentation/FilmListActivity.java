package nl.avans.android.todos.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Film;
import nl.avans.android.todos.domain.FilmAdapter;
import nl.avans.android.todos.service.FilmRequest;

public class FilmListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, FilmRequest.FilmListener {

    private ArrayList<Film> filmList = new ArrayList<>();
    private FilmAdapter filmAdapter;
    private ListView filmListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);

//        Film film = new Film();
//        film.setTitle("TestFilm");
//        filmList.add(film);

        filmListView = (ListView) findViewById(R.id.filmListView);
        filmAdapter = new FilmAdapter(this, filmList);
        filmListView.setAdapter(filmAdapter);
        filmListView.setOnItemClickListener(this);
        this.filmAdapter.notifyDataSetChanged();
        getFilms();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //niks
    }

    @Override
    public void onFilmsAvailable(ArrayList<Film> filmArrayList) {
        filmList.clear();
        for(int i = 0; i < filmArrayList.size(); i++) {
            filmList.add(filmArrayList.get(i));
        }
        filmAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFilmAvailable(Film film) {
        filmList.add(film);
        filmAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFilmsError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void getFilms() {
        FilmRequest request = new FilmRequest(getApplicationContext(), this);
        request.handleGetAllFilms(0, 10);
    }
}
