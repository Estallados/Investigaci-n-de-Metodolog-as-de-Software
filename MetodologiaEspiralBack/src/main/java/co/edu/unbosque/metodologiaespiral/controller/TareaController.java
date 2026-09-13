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

import co.edu.unbosque.metodologiaespiral.dto.TareaDTO;
import co.edu.unbosque.metodologiaespiral.entity.Tarea;
import co.edu.unbosque.metodologiaespiral.exception.ExceptionCheker;
import co.edu.unbosque.metodologiaespiral.exception.NegativeNumberException;
import co.edu.unbosque.metodologiaespiral.service.TareaService;

@RestController
@RequestMapping("/tareas")
@CrossOrigin(origins = "http://localhost:4200")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    public TareaController() {

    }

    @GetMapping("/ListarTareas")
    public ResponseEntity<List<TareaDTO>> listarTodas() {

        List<TareaDTO> lista =
                tareaService.getAll();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<TareaDTO> obtenerTareaPorId(
            @PathVariable Long id) {

        try {

            ExceptionCheker.checkerNegativeNumber(
                    id.intValue());

        } catch (NegativeNumberException e) {

            return ResponseEntity
                    .badRequest()
                    .build();
        }

        TareaDTO tarea =
                tareaService.getById(id);

        if (tarea == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(tarea);
    }

    @GetMapping("/obtenernombre")
    public ResponseEntity<Tarea> obtenerTareaPorNombre(
            @RequestParam String nombre) {

        Tarea tarea =
                tareaService.obtenerTareaPorNombre(
                        nombre);

        if (tarea == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(tarea);
    }

    @PostMapping("/crear")
    public ResponseEntity<String> crearTarea(
            @RequestBody TareaDTO tarea) {

        int resultado =
                tareaService.create(tarea);

        if (resultado == 0) {

            return ResponseEntity.ok(
                    "Tarea creada correctamente");
        }

        if (resultado == 1) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "Ya existe una tarea con ese nombre");
        }

        return ResponseEntity
                .badRequest()
                .body(
                        "No se pudo crear la tarea");
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarTarea(
            @PathVariable Long id,
            @RequestBody TareaDTO tarea) {

        try {

            ExceptionCheker.checkerNegativeNumber(
                    id.intValue());

        } catch (NegativeNumberException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "El ID no puede ser negativo");
        }

        int resultado =
                tareaService.updateById(
                        id,
                        tarea);

        if (resultado == 0) {

            return ResponseEntity.ok(
                    "Tarea actualizada correctamente");
        }

        if (resultado == 1) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .badRequest()
                .body(
                        "No se pudo actualizar la tarea");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTarea(
            @PathVariable Long id) {

        try {

            ExceptionCheker.checkerNegativeNumber(
                    id.intValue());

        } catch (NegativeNumberException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "El ID no puede ser negativo");
        }

        int resultado =
                tareaService.deleteById(id);

        if (resultado == 0) {

            return ResponseEntity.ok(
                    "Tarea eliminada correctamente");
        }

        return ResponseEntity
                .notFound()
                .build();
    }
}