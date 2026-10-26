package prog2.residenciageriatrica.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import prog2.residenciageriatrica.dto.PersonaDTO;
import prog2.residenciageriatrica.dto.PersonaHabitacionDTO;
import prog2.residenciageriatrica.excepciones.EntidadNoEncontradaException;
import prog2.residenciageriatrica.excepciones.OperacionNoPermitidaException;
import prog2.residenciageriatrica.modelos.Enfermero;
import prog2.residenciageriatrica.modelos.Habitacion;
import prog2.residenciageriatrica.repositorios.EnfermeroRepository;
import prog2.residenciageriatrica.repositorios.HabitacionRepository;

/**
 * Reglas de las asignaciones: un enfermero puede atender varias habitaciones,
 * pero cada habitación tiene como máximo un enfermero. La asignación se guarda en la habitación (id_enfermero).
 * @author Emanuel
 */
@Service
public class AsignacionServicio {

    private final HabitacionRepository habitacionRepository;
    private final EnfermeroRepository enfermeroRepository;

    public AsignacionServicio(HabitacionRepository habitacionRepository,
                              EnfermeroRepository enfermeroRepository) {
        this.habitacionRepository = habitacionRepository;
        this.enfermeroRepository = enfermeroRepository;
    }

    public void asignar(int idEnfermero, String codigo) {
        String normalizado = Habitacion.normalizarCodigo(codigo);
        Optional<Enfermero> enfermero = enfermeroRepository.findById(idEnfermero);
        if (enfermero.isEmpty()) {
            throw new EntidadNoEncontradaException("No existe el enfermero " + idEnfermero + ".");
        }
        Habitacion habitacion = buscarHabitacion(normalizado);
        if (habitacion.tieneEnfermero()) {
            throw new OperacionNoPermitidaException("La habitación " + normalizado + " ya tiene un enfermero asignado.");
        }
        habitacionRepository.save(habitacion.conEnfermero(enfermero.get()));  // UPDATE de id_enfermero
    }

    public void quitar(String codigo) {
        String normalizado = Habitacion.normalizarCodigo(codigo);
        Habitacion habitacion = buscarHabitacion(normalizado);
        if (!habitacion.tieneEnfermero()) {
            throw new EntidadNoEncontradaException("La habitación " + normalizado + " no tiene enfermero asignado.");
        }
        habitacionRepository.save(habitacion.conEnfermero(null));             // id_enfermero = NULL
    }

    /** Habitaciones que tienen enfermero, cada una con el suyo. */
    public List<PersonaHabitacionDTO> listar() {
        List<PersonaHabitacionDTO> resultado = new ArrayList<>();
        for (Habitacion h : habitacionRepository.findByEnfermeroIsNotNull()) {
            resultado.add(new PersonaHabitacionDTO(PersonaDTO.desde(h.getEnfermero()), h.getCodigo()));
        }
        return resultado;
    }

    /** Códigos de las habitaciones que todavía no tienen enfermero. */
    public List<String> habitacionesSinEnfermero() {
        List<String> resultado = new ArrayList<>();
        for (Habitacion h : habitacionRepository.findByEnfermeroIsNull()) {
            resultado.add(h.getCodigo());
        }
        return resultado;
    }

    private Habitacion buscarHabitacion(String codigo) {
        Optional<Habitacion> habitacion = habitacionRepository.findById(codigo);
        if (habitacion.isEmpty()) {
            throw new EntidadNoEncontradaException("No existe la habitación " + codigo + ".");
        }
        return habitacion.get();
    }
}
