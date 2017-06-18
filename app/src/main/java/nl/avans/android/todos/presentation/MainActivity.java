package nl.avans.android.todos.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Customer;
import nl.avans.android.todos.domain.Film;
import nl.avans.android.todos.domain.Rental;
import nl.avans.android.todos.domain.RentalAdapter;
import nl.avans.android.todos.service.FilmRequest;
import nl.avans.android.todos.service.RentalRequest;

import static nl.avans.android.todos.presentation.FilmListActivity.FILMDATA;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemClickListener, RentalRequest.RentalListener {

    // Logging tag
    public final String TAG = this.getClass().getSimpleName();
    public final static String RENTALDATA = "RENTALS";

    // A request code for returning data from Intent - is supposed to be unique.
    //public static final int MY_REQUEST_CODE = 1234;

    // UI Elements
    private ListView listViewRentals;
    private RentalAdapter rentalAdapter;
    private ArrayList<Rental> rentals = new ArrayList<>();
    Intent intent;
    int customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = getIntent();
        customerId = (Integer) intent.getSerializableExtra("ID");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newFilm = new Intent(getApplicationContext(), FilmListActivity.class);
                newFilm.putExtra("ID", customerId);
                startActivity(newFilm);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listViewRentals = (ListView) findViewById(R.id.listViewRentals);
        listViewRentals.setOnItemClickListener(this);
        rentalAdapter = new RentalAdapter(this, rentals);
        listViewRentals.setAdapter(rentalAdapter);

        Log.d(TAG, "Token gevonden - Rentals ophalen!");

        getRentals();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settings);
            return true;
        } else if (id == R.id.action_logout) {
            // Logout - remove token from local settings and navigate to login screen.
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(getString(R.string.saved_token));
            editor.commit();

            // Empty the homescreen
            rentals.clear();
            rentalAdapter.notifyDataSetChanged();

            // Navigate to login screen
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(login);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "Position " + position + " is geselecteerd");

        Rental rental = rentals.get(position);
        Intent intent = new Intent(getApplicationContext(), RentalDetailActivity.class);
        intent.putExtra(RENTALDATA, rental);
        startActivity(intent);
    }

    @Override
    public void onRentalsAvailable(ArrayList<Rental> rentalArrayList) {
        rentals.clear();
        for (int i = 0; i < rentalArrayList.size(); i++) {
            rentals.add(rentalArrayList.get(i));
        }
        rentalAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRentalAvailable(Rental rental) {
        rentals.add(rental);
        rentalAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRentalsError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void getRentals() {

        RentalRequest request = new RentalRequest(getApplicationContext(), this);
        request.handleGetAllRentals(customerId);
    }
}
