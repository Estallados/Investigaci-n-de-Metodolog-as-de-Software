package co.edu.unbosque.metodologiaespiral.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.metodologiaespiral.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findBynombre(String username);

	public Optional<Usuario> findByCorreo(String correo);

	boolean existsBynombre(String username);

	public void deleteByCorreo(String correo);

	public boolean existsByCorreo(String correo);
}
