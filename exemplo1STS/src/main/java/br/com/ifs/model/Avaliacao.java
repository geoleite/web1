package br.com.ifs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
@Entity
@Table(name = "avaliacao", schema = "public")
public class Avaliacao {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	@Column(name = "aluno_id")
	private int alunoId;
	@Column(name = "prova_id")
	private int provaId;
	@Column(name = "nota")
	private double nota;

}
