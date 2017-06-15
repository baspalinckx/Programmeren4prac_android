package nl.avans.android.todos.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Film;
import nl.avans.android.todos.domain.FilmAdapter;

public class FilmListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ArrayList<Film> filmList = new ArrayList<>();
    private FilmAdapter filmAdapter;
    private ListView filmListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);

        Film film = new Film();
        film.setTitle("TestFilm");
        filmList.add(film);

        filmListView = (ListView) findViewById(R.id.filmListView);
        filmAdapter = new FilmAdapter(this, filmList);
        filmListView.setAdapter(filmAdapter);
        filmListView.setOnItemClickListener(this);
        this.filmAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //niks
    }
}
