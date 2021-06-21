package br.com.ifs.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
@Entity
@Table(name = "vw_avaliacao_aluno", schema = "public")
public class VWAvaliacaoAluno {
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
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "disciplina_id")
	private int disciplinaId;
	@Column(name = "data")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "America/Maceio")
	private Date data;
	

}
