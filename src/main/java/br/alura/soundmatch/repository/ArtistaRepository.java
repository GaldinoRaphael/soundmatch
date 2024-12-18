package br.alura.soundmatch.repository;
import br.alura.soundmatch.model.Artista;
import br.alura.soundmatch.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    Optional<Artista> findFirstByNomeContainingIgnoreCase(String nome);

    @Query("SELECT m FROM Artista a Join a.musicas m")
    List<Musica> findAllMusicas();

    @Query("SELECT m FROM Musica m WHERE m.artista = :artista")
    List<Musica> findMusicasPorArtista(Artista artista);
}
