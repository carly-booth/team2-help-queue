package com.project.queue.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.queue.persistence.domain.Ticket;
import com.project.queue.services.TicketService;

@RestController
public class TicketController {
	
	private TicketService service;

	public TicketController(TicketService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/read/{id}")
	public ResponseEntity<Ticket> readDog(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.getTicketByID(id));
	}
	
	@PostMapping("/create")
	public ResponseEntity<Ticket> createDog(@RequestBody Ticket ticket) {
		return new ResponseEntity<Ticket>(this.service.createTicket(ticket),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?>deleteTicket(@PathVariable Long id) {
		boolean deleted = this.service.deleteTicketById(id); 
		if (deleted) {
			return ResponseEntity.ok(deleted);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/read")
	public ResponseEntity<List<Ticket>> getTickets() {
		return ResponseEntity.ok(this.service.getTickets());
	}
	
	/*@PutMapping("/update/{id}/{answer}")
	public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @PathVariable String answer) {
		return new ResponseEntity<Ticket>(this.service.updateTicketById(id, answer), HttpStatus.ACCEPTED);

	}*/
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket newData) {
		return new ResponseEntity<Ticket>(this.service.updateTicketById(id, newData), HttpStatus.ACCEPTED);

	}
	
	

}
