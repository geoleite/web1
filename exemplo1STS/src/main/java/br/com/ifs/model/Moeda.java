package br.com.ifs.model;

import lombok.Setter;

import javax.persistence.Entity;

import lombok.Getter;

@Getter
@Setter
public class Moeda {

	private long id;
	private String nome;
	private String paridade;
}
