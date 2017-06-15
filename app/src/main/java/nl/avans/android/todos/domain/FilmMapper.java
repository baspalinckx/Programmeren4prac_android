package nl.avans.android.todos.domain;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by koend on 15-6-2017.
 */

public class FilmMapper {

    /**
     * Map het JSON response op een arraylist en retourneer deze.
     */
    public static ArrayList<Film> mapFilmList(JSONObject response){

        ArrayList<Film> result = new ArrayList<>();

        try{
            JSONArray jsonArray = response.getJSONArray("result");

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonProduct = jsonArray.getJSONObject(i);

                // Convert stringdate to Date
                String title = jsonProduct.getString("title");
                String description = jsonProduct.getString("description");
                int releaseYear = jsonProduct.getInt("release_year");
                double rentalRate = jsonProduct.getDouble("rental_rate");
                int lenght = jsonProduct.getInt("length");
                String rating = jsonProduct.getString("rating");
                String specialFeatures = jsonProduct.getString("special_features");

                //DateTime todoDateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(timestamp);

                Film film = new Film();
                film.setTitle(title);
                film.setDescription(description);
                film.setRelease_year(releaseYear);
                film.setRental_rate(rentalRate);
                film.setLength(lenght);
                film.setRating(rating);
                film.setSpecial_features(specialFeatures);
                result.add(film);
            }
        } catch( JSONException ex) {
            Log.e("ToDoMapper", "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}