package co.edu.unbosque.metodologiaespiral.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.metodologiaespiral.dto.ProyectoDTO;
import co.edu.unbosque.metodologiaespiral.dto.TareaDTO;
import co.edu.unbosque.metodologiaespiral.dto.UsuarioDTO;
import co.edu.unbosque.metodologiaespiral.entity.Proyecto;
import co.edu.unbosque.metodologiaespiral.entity.Tarea;
import co.edu.unbosque.metodologiaespiral.entity.Usuario;
import co.edu.unbosque.metodologiaespiral.repository.ProyectoRepository;
import co.edu.unbosque.metodologiaespiral.repository.TareaRepository;
import co.edu.unbosque.metodologiaespiral.repository.UsuarioRepository;
import java.io.IOException;
import org.springframework.transaction.annotation.Transactional;
import co.edu.unbosque.metodologiaespiral.util.PDFUtil;

@Service
public class ProyectoService implements CRUDOperation<ProyectoDTO> {

    @Autowired
    private PDFUtil pdfUtil;

    @Autowired
    private ProyectoRepository proyectoRepo;

    @Autowired
    private TareaRepository tareaRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    public ProyectoService() {
    }

    @Override
    public int create(ProyectoDTO data) {

        try {

            if (data.getNombre() == null
                    || data.getNombre().trim().isEmpty()) {

                return 2;
            }

            if (data.getDescripcion() == null
                    || data.getDescripcion().trim().isEmpty()) {

                return 2;
            }

            Optional<Proyecto> proyectoExistente =
                    proyectoRepo.findByNombre(data.getNombre());

            if (proyectoExistente.isPresent()) {
                return 1;
            }

            Proyecto proyecto = new Proyecto();

            proyecto.setNombre(data.getNombre());
            proyecto.setDescripcion(data.getDescripcion());

            /*
             * Agregar tareas existentes
             */
            List<Tarea> tareas = new ArrayList<>();

            if (data.getTareas() != null) {

                for (TareaDTO tareaDTO : data.getTareas()) {

                    if (tareaDTO.getId() != null) {

                        Optional<Tarea> tarea =
                                tareaRepo.findById(tareaDTO.getId());

                        tarea.ifPresent(tareas::add);
                    }
                }
            }

            proyecto.setTareas(tareas);

            /*
             * Agregar usuarios existentes
             */
            List<Usuario> usuarios = new ArrayList<>();

            if (data.getUsuarios() != null) {

                for (UsuarioDTO usuarioDTO : data.getUsuarios()) {

                    if (usuarioDTO.getId() != null) {

                        Optional<Usuario> usuario =
                                usuarioRepo.findById(usuarioDTO.getId());

                        usuario.ifPresent(usuarios::add);
                    }
                }
            }

            proyecto.setUsuarios(usuarios);

            proyectoRepo.save(proyecto);

            return 0;

        } catch (Exception e) {

            e.printStackTrace();
            return 2;
        }
    }

    @Override
    public List<ProyectoDTO> getAll() {

        List<Proyecto> entityList =
                proyectoRepo.findAll();

        List<ProyectoDTO> dtoList =
                new ArrayList<>();

        for (Proyecto proyecto : entityList) {

            dtoList.add(convertirADTO(proyecto));
        }

        return dtoList;
    }

    @Override
    public ProyectoDTO getById(Long id) {

        Optional<Proyecto> proyectoOpt =
                proyectoRepo.findById(id);

        if (proyectoOpt.isEmpty()) {
            return null;
        }

        return convertirADTO(proyectoOpt.get());
    }

    @Override
    public int deleteById(Long id) {

        if (!proyectoRepo.existsById(id)) {
            return 1;
        }

        proyectoRepo.deleteById(id);

        return 0;
    }

    @Override
    public int updateById(Long id, ProyectoDTO newData) {

        Optional<Proyecto> proyectoOpt =
                proyectoRepo.findById(id);

        if (proyectoOpt.isEmpty()) {
            return 1;
        }

        try {

            Proyecto proyecto =
                    proyectoOpt.get();

            if (newData.getNombre() != null
                    && !newData.getNombre().trim().isEmpty()) {

                proyecto.setNombre(
                        newData.getNombre());
            }

            if (newData.getDescripcion() != null
                    && !newData.getDescripcion().trim().isEmpty()) {

                proyecto.setDescripcion(
                        newData.getDescripcion());
            }

            /*
             * Actualizar tareas
             */
            if (newData.getTareas() != null) {

                List<Tarea> tareas =
                        new ArrayList<>();

                for (TareaDTO tareaDTO :
                        newData.getTareas()) {

                    if (tareaDTO.getId() != null) {

                        Optional<Tarea> tarea =
                                tareaRepo.findById(
                                        tareaDTO.getId());

                        tarea.ifPresent(tareas::add);
                    }
                }

                proyecto.setTareas(tareas);
            }

            /*
             * Actualizar usuarios
             */
            if (newData.getUsuarios() != null) {

                List<Usuario> usuarios =
                        new ArrayList<>();

                for (UsuarioDTO usuarioDTO :
                        newData.getUsuarios()) {

                    if (usuarioDTO.getId() != null) {

                        Optional<Usuario> usuario =
                                usuarioRepo.findById(
                                        usuarioDTO.getId());

                        usuario.ifPresent(usuarios::add);
                    }
                }

                proyecto.setUsuarios(usuarios);
            }

            proyectoRepo.save(proyecto);

            return 0;

        } catch (Exception e) {

            e.printStackTrace();
            return 2;
        }
    }

    @Override
    public long count() {

        return proyectoRepo.count();
    }

    @Override
    public boolean exist(Long id) {

        return proyectoRepo.existsById(id);
    }

    public ProyectoDTO obtenerProyectoPorNombre(
            String nombre) {

        Optional<Proyecto> proyecto =
                proyectoRepo.findByNombre(nombre);

        if (proyecto.isEmpty()) {
            return null;
        }

        return convertirADTO(proyecto.get());
    }

    /*
     * Convierte Proyecto Entity
     * a ProyectoDTO
     */
    private ProyectoDTO convertirADTO(
            Proyecto proyecto) {

        ProyectoDTO dto =
                new ProyectoDTO();

        dto.setId(proyecto.getId());
        dto.setNombre(proyecto.getNombre());
        dto.setDescripcion(
                proyecto.getDescripcion());

        ArrayList<TareaDTO> tareasDTO =
                new ArrayList<>();

        for (Tarea tarea :
                proyecto.getTareas()) {

            TareaDTO tareaDTO =
                    new TareaDTO();

            tareaDTO.setId(tarea.getId());
            tareaDTO.setNombre(
                    tarea.getNombre());
            tareaDTO.setDescripcion(
                    tarea.getDescripcion());
            tareaDTO.setFechaInicio(
                    tarea.getFechaInicio());
            tareaDTO.setFechaEntrega(
                    tarea.getFechaEntrega());
            tareaDTO.setEstado(
                    tarea.getEstado());

            tareasDTO.add(tareaDTO);
        }

        dto.setTareas(tareasDTO);

        ArrayList<UsuarioDTO> usuariosDTO =
                new ArrayList<>();

        for (Usuario usuario :
                proyecto.getUsuarios()) {

            UsuarioDTO usuarioDTO =
                    new UsuarioDTO();

            usuarioDTO.setId(
                    usuario.getId());
            usuarioDTO.setNombre(
                    usuario.getNombre());
            usuarioDTO.setCorreo(
                    usuario.getCorreo());
            usuarioDTO.setRole(
                    usuario.getRole());

            usuariosDTO.add(usuarioDTO);
        }

        dto.setUsuarios(usuariosDTO);

        return dto;
    }

    @Transactional(readOnly = true)
    public byte[] generarPdfProyecto(Long id) {

        try {

            Optional<Proyecto> proyectoOpt =
                    proyectoRepo.findById(id);

            if (proyectoOpt.isEmpty()) {
                return null;
            }

            Proyecto proyecto =
                    proyectoOpt.get();

            return pdfUtil
                    .generarPdf(proyecto);

        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }
}
