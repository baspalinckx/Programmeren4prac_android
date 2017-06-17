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

import java.util.HashMap;
import java.util.Map;

import nl.avans.android.todos.R;

/**
 * Created by Bas Palinckx on 17-6-2017.
 */

public class DeleteRentalRequest {
    private Context context;
    public final String TAG = this.getClass().getSimpleName();

    /**
     * Constructor
     *
     * @param context
     */
    public DeleteRentalRequest(Context context) {
        this.context = context;
    }

    public void handleDeleteRental (int userId, int inventoryId) {

        Log.i(TAG, "handleCreateRental");

        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        if(token != null && !token.equals("dummy default token")) {

            Log.i(TAG, "Token gevonden, we gaan het request uitvoeren");
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.DELETE, "http://progprac.herokuapp.com/api/v1/rentals/" + userId + "/" + inventoryId, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response
                            Log.i(TAG, response.toString());
//                            ArrayList<Rental> result = RentalMapper.mapRentalList(response);
//                            listener.onRentalsAvailable(result);
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


}