package nl.avans.android.todos.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Film;
import nl.avans.android.todos.domain.Rental;
import nl.avans.android.todos.service.CreateRentalRequest;
import nl.avans.android.todos.service.DeleteRentalRequest;

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

        Intent intent = getIntent();

        final Rental rental = (Rental) intent.getSerializableExtra(RENTALDATA);
        final int customerId = (Integer) intent.getSerializableExtra("ID");


        inleverButton = (Button) findViewById(R.id.InleverButton);
        inleverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteRentalRequest request = new DeleteRentalRequest(getApplicationContext());
                request.handleDeleteRental(customerId, rental.getInventoryId());
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                intent.putExtra("ID", customerId);
                finish();
                startActivity(intent);
            }
        });


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