package br.alura.soundmatch.principal;

import br.alura.soundmatch.model.Artista;
import br.alura.soundmatch.model.Musica;
import br.alura.soundmatch.model.TipoArtista;
import br.alura.soundmatch.repository.ArtistaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {
    private final ArtistaRepository repositorio;
    Scanner leitura = new Scanner(System.in);

    public Principal(ArtistaRepository repositorio) {;
        this.repositorio = repositorio;
    }

    public void menu() {
        var opcao = -1;

        while(opcao != 9) {
            System.out.println("""
                    1- Cadastrar artistas
                    
                    2- Cadastrar músicas
                    
                    3- Listar músicas
                    
                    4- Buscar músicas por artistas
                    
                    9- Sair
                    """);

            System.out.println("Escolha uma opção:");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtista();
                    break;
            }
        }
    }

    private void buscarMusicasPorArtista() {
        Optional<Artista> artista = buscarArtista();

        if(artista.isPresent()){
            List<Musica> musicas = repositorio.findMusicasPorArtista(artista.get());
            musicas.forEach(System.out::println);
        }else{
            System.out.println("Artista não encontrado!");
            System.out.println("\n");
        }
    }

    private void listarMusicas() {
        List<Musica> musicas = repositorio.findAllMusicas();
        musicas.forEach(System.out::println);
    }

    private void cadastrarMusicas() {
        Optional<Artista> artista = buscarArtista();

        if(artista.isPresent()){
           cadastrarMusica(artista.get());
        }else{
            System.out.println("Artista não encontrado!");
            System.out.println("\n");
        }
    }

    private void cadastrarMusica(Artista artista) {
        System.out.println("\n");
        System.out.println("Digite o nome da música: ");
        var titulo = leitura.nextLine();

        var musica = new Musica(titulo, artista);
        var musicas = new ArrayList<Musica>();

        musicas.add(musica);

        artista.setMusicas(musicas);

        repositorio.save(artista);
    }

    private Optional<Artista> buscarArtista() {
        System.out.println("Digite o nome do artista: ");
        var nome = leitura.nextLine();

        return repositorio.findFirstByNomeContainingIgnoreCase(nome);
    }

    private void cadastrarArtistas() {
        System.out.println("Digite o nome do artista: ");
        var nome = leitura.nextLine();

        System.out.println("Informe o tipo desse artista: (solo, dupla, banda): ");
        var tipo = leitura.nextLine();

        try{
            var tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome, tipoArtista);
            repositorio.save(artista);
        }catch (IllegalArgumentException e){
            System.out.println("Tipo de artista inválido. Valores aceitos: solo, dupla, banda.");
        }
    }
}
