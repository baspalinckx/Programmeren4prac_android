package nl.avans.android.todos.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Film;
import nl.avans.android.todos.domain.Rental;

import static nl.avans.android.todos.presentation.FilmListActivity.FILMDATA;
import static nl.avans.android.todos.presentation.MainActivity.RENTALDATA;

public class RentalDetailActivity extends AppCompatActivity {

    private TextView textTitleRental;
    private TextView textDescriptionRental;
    private TextView textRatingRental;
    private TextView textDateRental;
    private TextView textLengthRental;
    private Button inleverButton;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_detail);
        textTitleRental = (TextView) findViewById(R.id.textDetailRentalTitle);
        textDescriptionRental = (TextView) findViewById(R.id.textDetailRentalDescription);
        textRatingRental = (TextView) findViewById(R.id.textDetailRentalRating);
        textLengthRental = (TextView) findViewById(R.id.textDetailRentalLength);
        textDateRental = (TextView) findViewById(R.id.textDetailRentalDate);

        inleverButton = (Button) findViewById(R.id.InleverButton);

        Bundle extras = getIntent().getExtras();

        Rental rental = (Rental) extras.getSerializable(RENTALDATA);
        Log.i(TAG, rental.toString());

        textTitleRental.setText(rental.getFilmTitle());
        textDescriptionRental.setText(rental.getFilmDescription());
//        textDateRental.setText(rental.getRental_date().toString());
        textRatingRental.setText(rental.getFilmRating());
        textLengthRental.setText(rental.getFilmLength() + " " + "Minutes");
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