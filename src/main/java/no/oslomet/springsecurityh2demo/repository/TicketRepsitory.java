package no.oslomet.springsecurityh2demo.repository;

import no.oslomet.springsecurityh2demo.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface TicketRepsitory extends JpaRepository<Ticket, Long> {
        Optional<Ticket> findById(int Id);
}
