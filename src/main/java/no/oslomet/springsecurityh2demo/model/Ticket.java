package no.oslomet.springsecurityh2demo.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor


public class Ticket {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String  date;
    private String film;
    private String cinema;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "User_id")
    private  User user;



    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }




    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", film='" + film + '\'' +
                ", cinema='" + cinema + '\'' +
                '}';
    }

}
