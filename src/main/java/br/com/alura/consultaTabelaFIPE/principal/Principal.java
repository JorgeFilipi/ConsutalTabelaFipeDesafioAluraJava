package br.com.alura.consultaTabelaFIPE.principal;

import br.com.alura.consultaTabelaFIPE.model.DadosApi;
import br.com.alura.consultaTabelaFIPE.model.Veiculo;
import br.com.alura.consultaTabelaFIPE.servicos.ConsumoApi;
import br.com.alura.consultaTabelaFIPE.servicos.ConverteDados;
import br.com.alura.consultaTabelaFIPE.model.Dados;
import br.com.alura.consultaTabelaFIPE.model.DadosApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner entrada = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String URL = "https://parallelum.com.br/fipe/api/v1";
    private String endereco;
    private Integer marca;
    private Integer modelo;
    private String ano;

    public void exibeMenu() {

        var menu = """
                *** OPÇÕES DE CONSULMO ***     
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consulta:
                """;

        System.out.println(menu);
        var opcao = entrada.nextLine();
        String endereco;

        if (opcao.toLowerCase().contains("carr")) {
            endereco = URL + "/carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL + "/motos/marcas";
        } else {
            endereco = URL + "/caminhoes/marcas";
        }

        var json = consumoApi.obterDados(endereco);
        var marcas = conversor.obterDadosList(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);



        System.out.println("\nEscolha o código da marca para consulta: ");
        marca = entrada.nextInt();
        entrada.nextLine();
        endereco = endereco + "/" + marca + "/modelos";
        json = consumoApi.obterDados(endereco);
        var dadosModelos = conversor.obterDados(json, DadosApi.class);
        dadosModelos.modelos().stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);



        System.out.println("\nDigite o código do modelo para consulta: ");
        modelo = entrada.nextInt();
        entrada.nextLine();
        endereco = endereco + "/" + modelo + "/anos";
        json = consumoApi.obterDados(endereco);
        List<Dados> anos = conversor.obterDadosList(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();
        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumoApi.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }
        System.out.println("\nTodos os veículos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);

    }
}
