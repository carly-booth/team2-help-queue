package com.project.queue.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.queue.persistence.domain.Ticket;
import com.project.queue.persistence.repo.TicketRepo;

@Service
public class TicketService {
	
	// CRUD - Create Read Update Delete
	private TicketRepo repo;

	// Autowiring - Spring automatically inserts a dependency (dogRepo in this case)
	public TicketService(TicketRepo repo) {
		super();
		this.repo = repo;
	}

	public Ticket createTicket(Ticket ticket) {
		return this.repo.save(ticket);
	}

	public Ticket getDogByID(Long id) {
		Optional<Ticket> ticket = this.repo.findById(id);
		return ticket.get();

	}

	public List<Ticket> getDogs() {
		return this.repo.findAll();
	}


	public boolean deleteTicketById(Long id) {
		this.repo.deleteById(id);
		boolean found = this.repo.existsById(id); 
		return !found;
	}


}
