package co.edu.unbosque.metodologiaespiral.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.metodologiaespiral.entity.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    Optional<Tarea> findByNombre(String nombre);
}