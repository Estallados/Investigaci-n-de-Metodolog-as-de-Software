package co.edu.unbosque.metodologiaespiral.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.metodologiaespiral.dto.UsuarioDTO;
import co.edu.unbosque.metodologiaespiral.entity.Usuario;
import co.edu.unbosque.metodologiaespiral.exception.ExceptionCheker;
import co.edu.unbosque.metodologiaespiral.exception.CharacterException;
import co.edu.unbosque.metodologiaespiral.exception.NumberException;
import co.edu.unbosque.metodologiaespiral.exception.SymbolException;
import co.edu.unbosque.metodologiaespiral.exception.MailException;
import co.edu.unbosque.metodologiaespiral.exception.TextException;
import co.edu.unbosque.metodologiaespiral.exception.NegativeNumberException;
import co.edu.unbosque.metodologiaespiral.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    public UsuarioController() {

    }

    @GetMapping("/ListarUsuarios")
    public ResponseEntity<List<UsuarioDTO>> listarTodas() {

        List<UsuarioDTO> lista = usuarioService.getAll();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Long id) {

        try {
            ExceptionCheker.checkerNegativeNumber(id.intValue());
        } catch (NegativeNumberException e) {
            return ResponseEntity.badRequest().build();
        }

        UsuarioDTO usuario = usuarioService.getById(id);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/obtenercorreo")
    public ResponseEntity<Usuario> obtenerUsuarioPorCorreo(
            @RequestParam String correo) {

        try {
            ExceptionCheker.checkerMail(correo);
        } catch (MailException e) {
            return ResponseEntity.badRequest().build();
        }

        Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(correo);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/crear")
    public ResponseEntity<String> crearUsuario(
            @RequestBody UsuarioDTO usuario) {

        try {

            ExceptionCheker.checkerMail(usuario.getCorreo());

            ExceptionCheker.checkerPasword(usuario.getContrasenia());

            ExceptionCheker.checkerText(usuario.getNombre());

        } catch (MailException e) {

            return ResponseEntity.badRequest()
                    .body("El correo no tiene un formato válido");

        } catch (CharacterException e) {

            return ResponseEntity.badRequest()
                    .body("La contraseña debe tener mínimo 8 caracteres");

        } catch (NumberException e) {

            return ResponseEntity.badRequest()
                    .body("La contraseña debe contener al menos un número");

        } catch (SymbolException e) {

            return ResponseEntity.badRequest()
                    .body("La contraseña debe contener al menos un símbolo");

        } catch (TextException e) {

            return ResponseEntity.badRequest()
                    .body("El nombre solo debe contener letras");

        }

        int resultado = usuarioService.create(usuario);

        if (resultado == 0) {
            return ResponseEntity.ok("Usuario creado correctamente");
        }

        if (resultado == 1) {
            return ResponseEntity.badRequest()
                    .body("El correo ya está registrado");
        }

        return ResponseEntity.badRequest()
                .body("No se pudo crear el usuario");
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarUsuario(
            @PathVariable Long id,
            @RequestBody UsuarioDTO usuario) {

        try {

            ExceptionCheker.checkerNegativeNumber(id.intValue());

            ExceptionCheker.checkerMail(usuario.getCorreo());

            ExceptionCheker.checkerPasword(usuario.getContrasenia());

            ExceptionCheker.checkerText(usuario.getNombre());

        } catch (NegativeNumberException e) {

            return ResponseEntity.badRequest()
                    .body("El ID no puede ser negativo");

        } catch (MailException e) {

            return ResponseEntity.badRequest()
                    .body("El correo no tiene un formato válido");

        } catch (CharacterException e) {

            return ResponseEntity.badRequest()
                    .body("La contraseña debe tener mínimo 8 caracteres");

        } catch (NumberException e) {

            return ResponseEntity.badRequest()
                    .body("La contraseña debe contener al menos un número");

        } catch (SymbolException e) {

            return ResponseEntity.badRequest()
                    .body("La contraseña debe contener al menos un símbolo");

        } catch (TextException e) {

            return ResponseEntity.badRequest()
                    .body("El nombre solo debe contener letras");
        }

        int resultado = usuarioService.updateById(id, usuario);

        if (resultado == 0) {
            return ResponseEntity.ok("Usuario actualizado correctamente");
        }

        if (resultado == 1) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.badRequest()
                .body("No se pudo actualizar el usuario");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(
            @PathVariable Long id) {

        try {

            ExceptionCheker.checkerNegativeNumber(id.intValue());

        } catch (NegativeNumberException e) {

            return ResponseEntity.badRequest()
                    .body("El ID no puede ser negativo");
        }

        int resultado = usuarioService.deleteById(id);

        if (resultado == 0) {
            return ResponseEntity.ok("Usuario eliminado correctamente");
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Integer> login(
            @RequestBody Usuario datos) {

        try {

            ExceptionCheker.checkerMail(datos.getCorreo());

            ExceptionCheker.checkerPasword(datos.getContrasenia());

        } catch (MailException e) {

            return ResponseEntity.badRequest().build();

        } catch (CharacterException e) {

            return ResponseEntity.badRequest().build();

        } catch (NumberException e) {

            return ResponseEntity.badRequest().build();

        } catch (SymbolException e) {

            return ResponseEntity.badRequest().build();
        }

        int resultado = usuarioService.autenticarUsuario(
                datos.getCorreo(),
                datos.getContrasenia());

        return ResponseEntity.ok(resultado);
    }
}