package nl.avans.android.todos.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by koend on 15-6-2017.
 */

public class Rental implements Serializable {

    private Film film;
    private Date rental_date;
    private Customer customer;

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Date getRental_date() {
        return rental_date;
    }

    public void setRental_date(Date rental_date) {
        this.rental_date = rental_date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
