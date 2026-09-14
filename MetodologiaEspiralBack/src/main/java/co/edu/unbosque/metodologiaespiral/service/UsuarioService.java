package co.edu.unbosque.metodologiaespiral.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.metodologiaespiral.dto.UsuarioDTO;
import co.edu.unbosque.metodologiaespiral.entity.Usuario;
import co.edu.unbosque.metodologiaespiral.repository.UsuarioRepository;

@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO> {

	@Autowired
	private UsuarioRepository usuarioRepo;

	public UsuarioService() {
	}

	@Override
	public int create(UsuarioDTO data) {
		try {
			if (data.getCorreo() == null || data.getCorreo().trim().isEmpty()) {
				return 2;
			}

			if (data.getContrasenia() == null || data.getContrasenia().trim().isEmpty()) {
				return 2;
			}

			Usuario usuario = new Usuario();

			usuario.setNombre(data.getNombre());
			usuario.setCorreo(data.getCorreo());
			usuario.setContrasenia(data.getContrasenia());

			if (data.getRole() != null) {
				usuario.setRole(data.getRole());
			}

			if (findUsernameAlreadyTaken(usuario)) {
				return 1;
			} else {
				usuarioRepo.save(usuario);
				return 0;
			}

		} catch (Exception e) {
			return 2;
		}
	}
	

	@Override
	public List<UsuarioDTO> getAll() {
		List<Usuario> entityList = usuarioRepo.findAll();
		List<UsuarioDTO> dtoList = new ArrayList<>();

		for (Usuario entity : entityList) {
			UsuarioDTO dto = new UsuarioDTO();

			dto.setId(entity.getId());
			dto.setNombre(entity.getNombre());
			dto.setCorreo(entity.getCorreo());
			dto.setContrasenia(entity.getContrasenia());
			dto.setRole(entity.getRole());

			dtoList.add(dto);
		}

		return dtoList;
	}

	@Override
	public UsuarioDTO getById(Long id) {
		Optional<Usuario> optionalUsuario = usuarioRepo.findById(id);

		if (optionalUsuario.isEmpty()) {
			return null;
		}

		Usuario entity = optionalUsuario.get();

		UsuarioDTO dto = new UsuarioDTO();

		dto.setId(entity.getId());
		dto.setNombre(entity.getNombre());
		dto.setCorreo(entity.getCorreo());
		dto.setContrasenia(entity.getContrasenia());
		dto.setRole(entity.getRole());

		return dto;
	}

	@Override
	public int deleteById(Long id) {
		if (!usuarioRepo.existsById(id)) {
			return 1;
		}

		usuarioRepo.deleteById(id);
		return 0;
	}

	@Override
	public int updateById(Long id, UsuarioDTO newData) {
		Optional<Usuario> usuarioOpt = usuarioRepo.findById(id);

		if (usuarioOpt.isEmpty()) {
			return 1;
		}

		try {
			Usuario usuario = usuarioOpt.get();

			if (newData.getNombre() != null) {
				usuario.setNombre(newData.getNombre());
			}

			if (newData.getCorreo() != null) {
				usuario.setCorreo(newData.getCorreo());
			}

			if (newData.getContrasenia() != null) {
				usuario.setContrasenia(newData.getContrasenia());
			}

			if (newData.getRole() != null) {
				usuario.setRole(newData.getRole());
			}

			usuarioRepo.save(usuario);

			return 0;

		} catch (Exception e) {
			return 2;
		}
	}

	@Override
	public long count() {
		return usuarioRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return usuarioRepo.existsById(id);
	}

	public int autenticarUsuario(String correo, String password) {
		try {
			if (correo == null || correo.trim().isEmpty()) {
				return 1;
			}

			if (password == null || password.trim().isEmpty()) {
				return 2;
			}

			Optional<Usuario> usuarioFound = usuarioRepo.findByCorreo(correo);

			if (usuarioFound.isEmpty()) {
				return 1;
			}

			Usuario usuario = usuarioFound.get();

			if (password.equals(usuario.getContrasenia())) {
				return 0;
			} else {
				return 2;
			}

		} catch (Exception e) {
			return 3;
		}
	}

	public boolean findUsernameAlreadyTaken(Usuario newUser) {
		try {
			Optional<Usuario> found = usuarioRepo.findByCorreo(newUser.getCorreo());

			return found.isPresent();

		} catch (Exception e) {
			return false;
		}
	}

	public boolean encontrarCorreoExitente(String username) {
		Optional<Usuario> found = usuarioRepo.findByCorreo(username);

		return found.isPresent();
	}

	public Usuario obtenerUsuarioPorCorreo(String correo) {
		return usuarioRepo.findByCorreo(correo).orElse(null);
	}
}
