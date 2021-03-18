package br.com.unamaproject.server.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "avaliacao")
public class Avaliacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer qtdEstrelas;
	private String comentario;
	private LocalDate dateComentario;

	private Usuario usuario;

	public Avaliacao() {
	}

	public Avaliacao(Integer id, Integer qtdEstrelas, String comentario, LocalDate dateComentario) {
		this.id = id;
		this.qtdEstrelas = qtdEstrelas;
		this.comentario = comentario;
		this.dateComentario = dateComentario;
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

	public LocalDate getDateComentario() {
		return dateComentario;
	}

	public void setDateComentario(LocalDate dateComentario) {
		this.dateComentario = dateComentario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Avaliacao other = (Avaliacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}