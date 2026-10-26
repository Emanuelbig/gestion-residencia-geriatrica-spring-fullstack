package prog2.residenciageriatrica.controllers.pantallas;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import prog2.residenciageriatrica.dto.PersonaDTO;
import prog2.residenciageriatrica.dto.PersonaHabitacionDTO;
import prog2.residenciageriatrica.servicios.AsignacionServicio;
import prog2.residenciageriatrica.servicios.EnfermeroServicio;

/**
 * Pantalla de asignaciones: qué enfermero atiende cada habitación (uno a muchos).
 * @author Emanuel
 */
@Controller
@RequestMapping("/asignaciones")
public class AsignacionPantallaController extends RelacionPantallaController {

    private final AsignacionServicio servicio;
    private final EnfermeroServicio enfermeros;     // para elegir: todos, porque atienden varias habitaciones

    public AsignacionPantallaController(AsignacionServicio servicio, EnfermeroServicio enfermeros) {
        super("/asignaciones", "Enfermeros por habitación", "Enfermero");
        this.servicio = servicio;
        this.enfermeros = enfermeros;
    }

    @Override
    protected void asignar(int idPersona, String codigo) {
        servicio.asignar(idPersona, codigo);
    }

    @Override
    protected void quitar(String codigo) {
        servicio.quitar(codigo);
    }

    @Override
    protected List<PersonaHabitacionDTO> asignadas() {
        return servicio.listar();
    }

    @Override
    protected List<PersonaDTO> personasParaElegir() {
        return enfermeros.listar();
    }

    @Override
    protected List<String> habitacionesParaElegir() {
        return servicio.habitacionesSinEnfermero();
    }
}
