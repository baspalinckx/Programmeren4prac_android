package nl.avans.android.todos.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Film;
import nl.avans.android.todos.domain.FilmAdapter;
import nl.avans.android.todos.service.FilmRequest;

public class FilmListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, FilmRequest.FilmListener, Serializable {

    private ArrayList<Film> filmList = new ArrayList<>();
    private FilmAdapter filmAdapter;
    private ListView filmListView;
    private Button meerWeergevenknop;
    int customerId;
    Intent intent;
    int count = 10;

    public final static String FILMDATA = "FILMS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);
        intent = getIntent();
        customerId = (Integer) intent.getSerializableExtra("ID");

        filmListView = (ListView) findViewById(R.id.filmListView);
        filmAdapter = new FilmAdapter(this, filmList);
        filmListView.setAdapter(filmAdapter);
        filmListView.setOnItemClickListener(this);
        this.filmAdapter.notifyDataSetChanged();

        getFilms();
//        meerWeergevenknop = (Button) findViewById(R.id.meerWeergevenKnop);
//        meerWeergevenknop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        filmListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (filmListView.getLastVisiblePosition() - filmListView.getHeaderViewsCount() -
                        filmListView.getFooterViewsCount()) >= (filmAdapter.getCount() - 1)) {

                    count += 10;
                    getFilms();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Film film = filmList.get(position);
        Intent intent = new Intent(getApplicationContext(), FilmDetail.class);
        intent.putExtra(FILMDATA, film);
        intent.putExtra("ID", customerId);
        startActivity(intent);

    }

    @Override
    public void onFilmsAvailable(ArrayList<Film> filmArrayList) {
        filmList.clear();
        for (int i = 0; i < filmArrayList.size(); i++) {
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
        request.handleGetAllFilms(0, count);
    }
}
