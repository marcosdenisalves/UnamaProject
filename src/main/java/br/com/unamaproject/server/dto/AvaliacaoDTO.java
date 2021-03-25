package br.com.unamaproject.server.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AvaliacaoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotNull
	@Min(value = 1, message = "Valor minimo é de 1 estrela")
	@Max(value = 5, message = "Valor maximo é de 5 estrelas")
	private Integer qtdEstrelas;
	private String comentario;

	public AvaliacaoDTO() {
	}

	public AvaliacaoDTO(Integer id ,Integer qtdEstrelas, String comentario) {
		this.id = id;
		this.qtdEstrelas = qtdEstrelas;
		this.comentario = comentario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
