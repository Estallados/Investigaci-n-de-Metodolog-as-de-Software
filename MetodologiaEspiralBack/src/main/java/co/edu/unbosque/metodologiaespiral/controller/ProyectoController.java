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

import co.edu.unbosque.metodologiaespiral.dto.ProyectoDTO;
import co.edu.unbosque.metodologiaespiral.exception.ExceptionCheker;
import co.edu.unbosque.metodologiaespiral.exception.NegativeNumberException;
import co.edu.unbosque.metodologiaespiral.service.ProyectoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/proyectos")
@CrossOrigin(origins = "http://investigacionsoftware.netlify.app")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    public ProyectoController() {
    }

    @GetMapping("/ListarProyectos")
    public ResponseEntity<List<ProyectoDTO>>
    listarTodos() {

        List<ProyectoDTO> lista =
                proyectoService.getAll();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<ProyectoDTO>
    obtenerProyectoPorId(
            @PathVariable Long id) {

        try {

            ExceptionCheker
                    .checkerNegativeNumber(
                            id.intValue());

        } catch (NegativeNumberException e) {

            return ResponseEntity
                    .badRequest()
                    .build();
        }

        ProyectoDTO proyecto =
                proyectoService.getById(id);

        if (proyecto == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(proyecto);
    }

    @GetMapping("/obtenernombre")
    public ResponseEntity<ProyectoDTO>
    obtenerProyectoPorNombre(
            @RequestParam String nombre) {

        ProyectoDTO proyecto =
                proyectoService
                        .obtenerProyectoPorNombre(
                                nombre);

        if (proyecto == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(proyecto);
    }

    @PostMapping("/crear")
    public ResponseEntity<String>
    crearProyecto(
            @RequestBody ProyectoDTO proyecto) {

        int resultado =
                proyectoService.create(proyecto);

        if (resultado == 0) {

            return ResponseEntity.ok(
                    "Proyecto creado correctamente");
        }

        if (resultado == 1) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "Ya existe un proyecto con ese nombre");
        }

        return ResponseEntity
                .badRequest()
                .body(
                        "No se pudo crear el proyecto");
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String>
    actualizarProyecto(
            @PathVariable Long id,
            @RequestBody ProyectoDTO proyecto) {

        try {

            ExceptionCheker
                    .checkerNegativeNumber(
                            id.intValue());

        } catch (NegativeNumberException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "El ID no puede ser negativo");
        }

        int resultado =
                proyectoService.updateById(
                        id,
                        proyecto);

        if (resultado == 0) {

            return ResponseEntity.ok(
                    "Proyecto actualizado correctamente");
        }

        if (resultado == 1) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .badRequest()
                .body(
                        "No se pudo actualizar el proyecto");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String>
    eliminarProyecto(
            @PathVariable Long id) {

        try {

            ExceptionCheker
                    .checkerNegativeNumber(
                            id.intValue());

        } catch (NegativeNumberException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "El ID no puede ser negativo");
        }

        int resultado =
                proyectoService.deleteById(id);

        if (resultado == 0) {

            return ResponseEntity.ok(
                    "Proyecto eliminado correctamente");
        }

        return ResponseEntity
                .notFound()
                .build();
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> descargarPdfProyecto(
            @PathVariable Long id) {

        try {

            ExceptionCheker.checkerNegativeNumber(
                    id.intValue());

        } catch (NegativeNumberException e) {

            return ResponseEntity
                    .badRequest()
                    .build();
        }

        if (!proyectoService.exist(id)) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        byte[] pdf =
                proyectoService.generarPdfProyecto(id);

        if (pdf == null) {

            return ResponseEntity
                    .internalServerError()
                    .build();
        }

        return ResponseEntity
                .ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=proyecto_"
                                + id
                                + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}