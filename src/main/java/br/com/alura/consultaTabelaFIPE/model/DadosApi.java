package br.com.alura.consultaTabelaFIPE.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosApi(List<Dados> modelos) {
} 