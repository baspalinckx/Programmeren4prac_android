package nl.avans.android.todos.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Film;
import nl.avans.android.todos.domain.FilmMapper;
import nl.avans.android.todos.domain.Rental;
import nl.avans.android.todos.domain.RentalMapper;

/**
 * Created by koend on 15-6-2017.
 */

public class RentalRequest {

    private Context context;
    public final String TAG = this.getClass().getSimpleName();

    // De aanroepende class implementeert deze interface.
    private RentalRequest.RentalListener listener;

    /**
     * Constructor
     *
     * @param context
     * @param listener
     */
    public RentalRequest(Context context, RentalRequest.RentalListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void handleGetAllRentals(int customerId) {

        Log.i(TAG, "handleGetAllToDos");

        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        if(token != null && !token.equals("dummy default token")) {

            Log.i(TAG, "Token gevonden, we gaan het request uitvoeren");
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, "https://progprac.herokuapp.com/api/v1/rentals/" + customerId, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response
                            Log.i(TAG, response.toString());
                            ArrayList<Rental> result = RentalMapper.mapRentalList(response);
                            listener.onRentalsAvailable(result);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //handleErrorResponse(error);
                            Log.e(TAG, error.toString());
                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
        }
    }


    //deze methode moet een rental gaan toevoegen, maar snap niet helemaal waar we inventory id vandaan halen.
    


    public interface RentalListener {
        // Callback function to return a fresh list of Films
        void onRentalsAvailable(ArrayList<Rental> rentals);

        // Callback function to handle a single added Film.
        void onRentalAvailable(Rental rental);

        // Callback to handle serverside API errors
        void onRentalsError(String message);
    }

}
