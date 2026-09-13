package co.edu.unbosque.metodologiaespiral.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.metodologiaespiral.dto.TareaDTO;
import co.edu.unbosque.metodologiaespiral.entity.Estado;
import co.edu.unbosque.metodologiaespiral.entity.Tarea;
import co.edu.unbosque.metodologiaespiral.repository.TareaRepository;

@Service
public class TareaService implements CRUDOperation<TareaDTO> {

    @Autowired
    private TareaRepository tareaRepo;

    public TareaService() {
    }

    @Override
    public int create(TareaDTO data) {

        try {

            if (data.getNombre() == null
                    || data.getNombre().trim().isEmpty()) {

                return 2;
            }

            if (data.getDescripcion() == null
                    || data.getDescripcion().trim().isEmpty()) {

                return 2;
            }

            Tarea tarea = new Tarea();

            tarea.setNombre(data.getNombre());
            tarea.setDescripcion(data.getDescripcion());
            tarea.setFechaInicio(data.getFechaInicio());
            tarea.setFechaEntrega(data.getFechaEntrega());

            /*
             * Si desde el frontend no mandan estado,
             * automáticamente queda PENDIENTE.
             */
            if (data.getEstado() != null) {

                tarea.setEstado(data.getEstado());

            } else {

                tarea.setEstado(Estado.PENDIENTE);
            }

            if (findNombreAlreadyTaken(tarea)) {
                return 1;
            }

            tareaRepo.save(tarea);

            return 0;

        } catch (Exception e) {

            return 2;
        }
    }

    @Override
    public List<TareaDTO> getAll() {

        List<Tarea> entityList = tareaRepo.findAll();

        List<TareaDTO> dtoList = new ArrayList<>();

        for (Tarea entity : entityList) {

            TareaDTO dto = new TareaDTO();

            dto.setId(entity.getId());
            dto.setNombre(entity.getNombre());
            dto.setDescripcion(entity.getDescripcion());
            dto.setFechaInicio(entity.getFechaInicio());
            dto.setFechaEntrega(entity.getFechaEntrega());
            dto.setEstado(entity.getEstado());

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public TareaDTO getById(Long id) {

        Optional<Tarea> optionalTarea =
                tareaRepo.findById(id);

        if (optionalTarea.isEmpty()) {
            return null;
        }

        Tarea entity = optionalTarea.get();

        TareaDTO dto = new TareaDTO();

        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaEntrega(entity.getFechaEntrega());
        dto.setEstado(entity.getEstado());

        return dto;
    }

    @Override
    public int deleteById(Long id) {

        if (!tareaRepo.existsById(id)) {
            return 1;
        }

        tareaRepo.deleteById(id);

        return 0;
    }

    @Override
    public int updateById(Long id, TareaDTO newData) {

        Optional<Tarea> tareaOpt =
                tareaRepo.findById(id);

        if (tareaOpt.isEmpty()) {
            return 1;
        }

        try {

            Tarea tarea = tareaOpt.get();

            if (newData.getNombre() != null
                    && !newData.getNombre().trim().isEmpty()) {

                tarea.setNombre(newData.getNombre());
            }

            if (newData.getDescripcion() != null
                    && !newData.getDescripcion().trim().isEmpty()) {

                tarea.setDescripcion(
                        newData.getDescripcion());
            }

            if (newData.getFechaInicio() != null) {

                tarea.setFechaInicio(
                        newData.getFechaInicio());
            }

            if (newData.getFechaEntrega() != null) {

                tarea.setFechaEntrega(
                        newData.getFechaEntrega());
            }

            if (newData.getEstado() != null) {

                tarea.setEstado(
                        newData.getEstado());
            }

            tareaRepo.save(tarea);

            return 0;

        } catch (Exception e) {

            return 2;
        }
    }

    @Override
    public long count() {

        return tareaRepo.count();
    }

    @Override
    public boolean exist(Long id) {

        return tareaRepo.existsById(id);
    }

    public boolean findNombreAlreadyTaken(
            Tarea nuevaTarea) {

        try {

            Optional<Tarea> found =
                    tareaRepo.findByNombre(
                            nuevaTarea.getNombre());

            return found.isPresent();

        } catch (Exception e) {

            return false;
        }
    }

    public Tarea obtenerTareaPorNombre(
            String nombre) {

        return tareaRepo
                .findByNombre(nombre)
                .orElse(null);
    }
}