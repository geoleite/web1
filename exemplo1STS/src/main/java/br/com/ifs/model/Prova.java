package br.com.ifs.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "prova", schema = "publico")
public class Prova {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "disciplina_id")
	private int disciplinaId;
	@Column(name = "data")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "America/Maceio")
	private Date data;
}
