package prog2.residenciageriatrica.controllers.pantallas;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import prog2.residenciageriatrica.dto.PersonaDTO;
import prog2.residenciageriatrica.dto.PersonaHabitacionDTO;
import prog2.residenciageriatrica.servicios.ReservaServicio;

/**
 * Pantalla de reservas: qué residente ocupa cada habitación (uno a uno).
 * @author Emanuel
 */
@Controller
@RequestMapping("/reservas")
public class ReservaPantallaController extends RelacionPantallaController {

    private final ReservaServicio servicio;

    public ReservaPantallaController(ReservaServicio servicio) {
        super("/reservas", "Reservas de habitaciones", "Residente");
        this.servicio = servicio;
    }

    @Override
    protected void asignar(int idPersona, String codigo) {
        servicio.reservar(idPersona, codigo);
    }

    @Override
    protected void quitar(String codigo) {
        servicio.liberar(codigo);
    }

    @Override
    protected List<PersonaHabitacionDTO> asignadas() {
        return servicio.listar();
    }

    @Override
    protected List<PersonaDTO> personasParaElegir() {
        return servicio.residentesSinHabitacion();          // solo los que no tienen habitación
    }

    @Override
    protected List<String> habitacionesParaElegir() {
        return servicio.habitacionesLibres();
    }
}
