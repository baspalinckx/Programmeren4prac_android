package nl.avans.android.todos.domain;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by koend on 15-6-2017.
 */

public class   RentalMapper {

    public static ArrayList<Rental> mapRentalList(JSONObject response){

        ArrayList<Rental> result = new ArrayList<>();

        try{
            JSONArray jsonArray = response.getJSONArray("result");

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonProduct = jsonArray.getJSONObject(i);

                // Convert stringdate to Date
                String title = jsonProduct.getString("title");
                String description = jsonProduct.getString("description");
                int releaseYear = jsonProduct.getInt("release_year");
                int length = jsonProduct.getInt("length");
                String rating = jsonProduct.getString("rating");
                String customerFirstName = jsonProduct.getString("first_name");
                String customerLastName = jsonProduct.getString("last_name");
                int customerId = jsonProduct.getInt("customer_id");
                int filmId = jsonProduct.getInt("film_id");

                //DateTime todoDateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(timestamp);

                Rental rental = new Rental();
                rental.setFilmTitle(title);
                rental.setFilmId(filmId);
                rental.setFilmDescription(description);
                rental.setReleaseYear(releaseYear);
                rental.setFilmRating(rating);
                rental.setFilmLength(length);
                rental.setCustomerFirstName(customerFirstName);
                rental.setCustomerLastName(customerLastName);
                rental.setCustomerId(customerId);
                result.add(rental);
            }
        } catch( JSONException ex) {
            Log.e("FilmMapper", "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}
