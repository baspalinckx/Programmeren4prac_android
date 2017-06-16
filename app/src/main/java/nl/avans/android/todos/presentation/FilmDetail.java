package nl.avans.android.todos.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Film;

import static nl.avans.android.todos.presentation.FilmListActivity.FILMDATA;


public class FilmDetail extends AppCompatActivity {

    private TextView textTitle;
    private TextView textDescription;
    private TextView textFeature;
    private TextView textRating;
    private TextView textLength;
    private Button reserveerButton;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        textTitle = (TextView) findViewById(R.id.textDetailFilmTitle);
        textDescription = (TextView) findViewById(R.id.textDetailFilmDescription);
        textFeature = (TextView) findViewById(R.id.textDetailFilmSpecialFeature);
        textRating = (TextView) findViewById(R.id.textDetailFilmRating);
        textLength = (TextView) findViewById(R.id.textDetailFilmLength);

        reserveerButton = (Button) findViewById(R.id.ReserveerButton);

        Bundle extras = getIntent().getExtras();

        Film film = (Film) extras.getSerializable(FILMDATA);
        Log.i(TAG, film.toString());

        textTitle.setText(film.getTitle());
        textDescription.setText(film.getDescription());
        textFeature.setText(film.getSpecial_features());
        textRating.setText(film.getRating());
        textLength.setText(film.getLength() + " " + "Minutes");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish(); // or go to another activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}