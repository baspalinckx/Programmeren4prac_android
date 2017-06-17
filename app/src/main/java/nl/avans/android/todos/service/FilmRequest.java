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
public class FilmRequest {

    private Context context;
    public final String TAG = this.getClass().getSimpleName();

    // De aanroepende class implementeert deze interface.
    private FilmRequest.FilmListener listener;

    /**
     * Constructor
     *
     * @param context
     * @param listener
     */
    public FilmRequest(Context context, FilmRequest.FilmListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void handleGetAllFilms(int offset, int count) {

        Log.i(TAG, "handleGetAllToDos");

        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        if(token != null && !token.equals("dummy default token")) {

            Log.i(TAG, "Token gevonden, we gaan het request uitvoeren");
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, "https://progprac.herokuapp.com/api/v1/films?offset=" + offset + "&count=" + count, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response
                            Log.i(TAG, response.toString());
                            ArrayList<Film> result = FilmMapper.mapFilmList(response);
                            listener.onFilmsAvailable(result);
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

    //
    // Callback interface - implemented by the calling class (MainActivity in our case).
    //


    public interface FilmListener {
        // Callback function to return a fresh list of Films
        void onFilmsAvailable(ArrayList<Film> films);

        // Callback function to handle a single added Film.
        void onFilmAvailable(Film film);

        // Callback to handle serverside API errors
        void onFilmsError(String message);
    }

}


