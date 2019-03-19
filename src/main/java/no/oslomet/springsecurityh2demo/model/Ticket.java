package no.oslomet.springsecurityh2demo.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Ticket {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date date;
    private String film;
    private String cinema;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private  User user;


    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getFilm() {
        return film;
    }

    public String getCinema() {
        return cinema;
    }

    public Ticket() {
    }


    public Ticket(Date date, String film, String cinema) {
        this.date = date;
        this.film = film;
        this.cinema = cinema;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", film='" + film + '\'' +
                ", cinema='" + cinema + '\'' +
                ", user=" + user +
                '}';
    }
}
