package br.com.ifs.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="aluno",  schema="public")
public class Aluno {

	@Id 
	@GeneratedValue
	@Column(name="id")
	private int id;
	@Column(name="nome")
	private String nome;
	@Column(name="matricula")
	private String matricula;
	
}
