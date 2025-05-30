package br.com.alura.consultaTabelaFIPE.servicos;

import java.util.List;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);

    <T> List<T> obterDadosList(String json, Class<T> classe);
}
