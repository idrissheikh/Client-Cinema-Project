package no.oslomet.springsecurityh2demo;

import no.oslomet.springsecurityh2demo.model.Ticket;
import no.oslomet.springsecurityh2demo.repository.TicketRepsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringSecurityH2DemoApplication implements CommandLineRunner {

    @Autowired
    private TicketRepsitory ticketRepsitory;

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityH2DemoApplication.class, args);

    }

    public void run(String... args) throws Exception {

        List<Ticket> ticketList = new ArrayList<>();

        Ticket ticket1 = new Ticket("Date", "finasjonaltiaterm", "cineme");
        ticketList.add(ticket1);



        Ticket ticket2 = new Ticket("date", "fimm", "cineme");

        ticketList.add(ticket2);

        Ticket ticket3 = new Ticket("date", "fimm", "cineme");
        ticketList.add(ticket3);

        ticketRepsitory.save(ticket1);
        ticketRepsitory.save(ticket2);
        ticketRepsitory.save(ticket3);


    }

}
