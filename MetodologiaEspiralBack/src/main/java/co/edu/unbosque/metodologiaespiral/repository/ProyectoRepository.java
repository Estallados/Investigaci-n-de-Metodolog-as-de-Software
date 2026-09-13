package co.edu.unbosque.metodologiaespiral.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.metodologiaespiral.entity.Proyecto;

@Repository
public interface ProyectoRepository
        extends JpaRepository<Proyecto, Long> {

    Optional<Proyecto> findByNombre(String nombre);
}