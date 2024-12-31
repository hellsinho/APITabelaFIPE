package br.com.hellsinho.tabelaFipe.Principal;

import br.com.hellsinho.tabelaFipe.model.Dados;
import br.com.hellsinho.tabelaFipe.model.Modelos;
import br.com.hellsinho.tabelaFipe.model.Veiculo;
import br.com.hellsinho.tabelaFipe.service.ConsumeApi;
import br.com.hellsinho.tabelaFipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumeApi consume = new ConsumeApi();
    private ConverteDados conversor = new ConverteDados();
    public void exibeMenu(){
        var menu = """
                **** OPÇÕES ****
                
                CARRO
                MOTO
                CAMINHÃO
                
                DIGITE UMA DAS OPÇÕES PARA CONSULTAR:
                """;

        System.out.println(menu);
        var escolha = leitura.nextLine();
        String endereco;

        if (escolha.toLowerCase().contains("carr")){
            endereco = URL_BASE + "carros/marcas/";
        } else if (escolha.toLowerCase().contains("mot")){
            endereco = URL_BASE + "motos/marcas/";
        } else {
            endereco = URL_BASE + "caminhoes/marcas/";
        }

        var json = consume.obterDados(endereco);
        System.out.println(json);

        var marcas = conversor.obterLista(json, Dados.class);

        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o código da marca para consulta: ");
        var codigoMarca = leitura.nextLine();

        endereco += codigoMarca+ "/modelos/";
        json = consume.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos da marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do carro a ser buscado: ");
        var nomeVeiculo = leitura.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\n Modelos filtrados");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nDigite por favor o código do modelo para obter os valores de avaliação: ");
        var codigoModeloFiltrado = leitura.nextLine();

        endereco += codigoModeloFiltrado + "/anos/";
        json = consume.obterDados(endereco);

        List<Dados> anos = conversor.obterLista(json, Dados.class);

        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++){
            var enderecoAnos = endereco + anos.get(i).codigo();
            json = consume.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nVeículos filtrados por avaliação por ano");
        veiculos.forEach(System.out::println);
    }


}
