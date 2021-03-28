package br.com.unamaproject.server.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class TransacaoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String title;
	private Double value;
	
	@NotEmpty
	private String date;
	
	public TransacaoNewDTO() {
	}
	
	public TransacaoNewDTO(String title, Double value, String date) {
		this.title = title;
		this.value = value;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}	
}