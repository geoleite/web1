package br.com.ifs.model;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@Table(name = "disciplina", schema = "public")
public class Disciplina
{
    @Id
    @GeneratedValue
    @Column(name = "disc_id")
    private int id;
    @Column(name = "disc_nome")
    private String nome;
    @Transient
    @ManyToMany(mappedBy = "disciplinas", fetch = FetchType.LAZY)
    Set<Aluno> alunos;
}