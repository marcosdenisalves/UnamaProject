package br.com.unamaproject.server.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AvaliacaoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Min(value = 1, message = "Valor minimo é de 1 estrela")
	@Max(value = 5, message = "Valor maximo é de 5 estrelas")
	private Integer qtdEstrelas;
	private String comentario;

	public AvaliacaoNewDTO() {
	}

	public AvaliacaoNewDTO( Integer qtdEstrelas, String comentario) {
		this.qtdEstrelas = qtdEstrelas;
		this.comentario = comentario;
	}

	public Integer getQtdEstrelas() {
		return qtdEstrelas;
	}

	public void setQtdEstrelas(Integer qtdEstrelas) {
		this.qtdEstrelas = qtdEstrelas;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}