package com.project.queue.persistence.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ticket {
	@Id
	@GeneratedValue
	private long id;

	private String name;

	private String problemDescription;

	private String solution = "";

	private LocalDateTime create = LocalDateTime.now();

	public Ticket() {
		// TODO Auto-generated constructor stub
	}

	public Ticket(String name, String problemDescription, String solution) {
		super();
		this.name = name;
		this.problemDescription = problemDescription;
		this.solution = solution;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public LocalDateTime getCreate() {
		return create;
	}

	public void setCreate(LocalDateTime create) {
		this.create = create;
	}

}
