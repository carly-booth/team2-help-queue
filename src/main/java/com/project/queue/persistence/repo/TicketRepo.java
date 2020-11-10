package com.project.queue.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.queue.persistence.domain.Ticket;

@Repository
public interface TicketRepo extends JpaRepository<Ticket,Long>  {

}
