package nl.avans.android.todos.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import nl.avans.android.todos.R;

/**
 * Created by koend on 15-6-2017.
 */

public class FilmAdapter extends ArrayAdapter<Film> {

    public FilmAdapter(Context context, ArrayList<Film> films) {
        super(context, 0, films);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Film film = getItem(position);

        //zorgt ervoor dat de rows onder elkaar geladen worden in de listview

        if( convertView == null ) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_rentals_row, parent, false);
        }

        //koppel albumName en id aan de id's uit de mainactivity

        //TextView albumName = (TextView) convertView.findViewById(R.id.nameTextView);
        //TextView id = (TextView) convertView.findViewById(R.id.idTextView);

        // Vult albumName en id in in de mainactivity
        //albumName.setText(spotifyItem.getAlbumName());
        //id.setText(spotifyItem.getSpotifyId());

        //laad image in de mainactivity

        //ImageView thumbnale = (ImageView) convertView.findViewById(R.id.avatarImageView);

        //new AsyncImageLoader(thumbnale).execute(spotifyItem.getImage_thumb_url());

        return convertView;
    }
}
