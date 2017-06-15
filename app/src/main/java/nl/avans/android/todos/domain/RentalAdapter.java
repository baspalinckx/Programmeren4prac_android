package nl.avans.android.todos.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nl.avans.android.todos.R;

/**
 * Created by koend on 15-6-2017.
 */

public class RentalAdapter extends ArrayAdapter<Rental> {

    public RentalAdapter(Context context, ArrayList<Rental> rentals) {
        super(context, 0, rentals);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Rental rental = getItem(position);

        //zorgt ervoor dat de rows onder elkaar geladen worden in de listview

        if( convertView == null ) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_rentals_row, parent, false);
        }

        TextView gehuurdeFilm = (TextView)convertView.findViewById(R.id.gehuurdeFilm);
        gehuurdeFilm.setText(rental.getFilm().getTitle());

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