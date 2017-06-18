package nl.avans.android.todos.presentation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Film;
import nl.avans.android.todos.domain.Rental;
import nl.avans.android.todos.service.CreateRentalRequest;
import nl.avans.android.todos.service.RentalRequest;

import static nl.avans.android.todos.presentation.FilmListActivity.FILMDATA;


public class FilmDetail extends AppCompatActivity implements RentalRequest.RentalListener {

    private TextView textTitle;
    private TextView textDescription;
    private TextView textFeature;
    private TextView textRating;
    private TextView textLength;
    private Button reserveerButton;

    public final String TAG = this.getClass().getSimpleName();

    Intent intent;
    int customerId;
    Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        intent = getIntent();

        film = (Film) intent.getSerializableExtra(FILMDATA);
        customerId = (Integer) intent.getSerializableExtra("ID");

        textTitle = (TextView) findViewById(R.id.textDetailFilmTitle);
        textDescription = (TextView) findViewById(R.id.textDetailFilmDescription);
        textFeature = (TextView) findViewById(R.id.textDetailFilmSpecialFeature);
        textRating = (TextView) findViewById(R.id.textDetailFilmRating);
        textLength = (TextView) findViewById(R.id.textDetailFilmLength);

        reserveerButton = (Button) findViewById(R.id.ReserveerButton);
        reserveerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateRentalRequest request = new CreateRentalRequest(getApplicationContext());
                request.handleCreateRental(customerId, film.getInventoryId());
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                intent.putExtra("ID", customerId);
                finish();
                startActivity(intent);
            }
        });

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

    @Override
    public void onRentalsAvailable(ArrayList<Rental> rentals) {

    }

    @Override
    public void onRentalAvailable(Rental rental) {

    }

    @Override
    public void onRentalsError(String message) {

    }
}