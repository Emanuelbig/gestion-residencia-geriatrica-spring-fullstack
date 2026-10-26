package prog2.residenciageriatrica.controllers.pantallas;

import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prog2.residenciageriatrica.dto.PersonaDTO;
import prog2.residenciageriatrica.dto.PersonaHabitacionDTO;
import prog2.residenciageriatrica.excepciones.DatosInvalidosException;
import prog2.residenciageriatrica.excepciones.GeriatricoException;

/**
 * Pantalla común de reservas y asignaciones: ver, asignar y quitar una persona de una habitación.
 * El recorrido es siempre el mismo; cada hija dice con qué servicio se hace cada paso (método plantilla).
 * Las reglas (uno a uno, uno a muchos) las controlan los servicios.
 * @author Emanuel
 */
public abstract class RelacionPantallaController {

    private final String ruta;          // "/reservas" o "/asignaciones"
    private final String titulo;
    private final String tipoPersona;   // "Residente" o "Enfermero"

    protected RelacionPantallaController(String ruta, String titulo, String tipoPersona) {
        this.ruta = ruta;
        this.titulo = titulo;
        this.tipoPersona = tipoPersona;
    }

    protected abstract void asignar(int idPersona, String codigo);

    protected abstract void quitar(String codigo);

    /** Las habitaciones que ya tienen a alguien, cada una con su persona. */
    protected abstract List<PersonaHabitacionDTO> asignadas();

    /** Las personas que se pueden elegir en el formulario. */
    protected abstract List<PersonaDTO> personasParaElegir();

    /** Las habitaciones que se pueden elegir en el formulario. */
    protected abstract List<String> habitacionesParaElegir();

    @GetMapping
    public String mostrar(Model model) {
        return vista(model);
    }

    @PostMapping
    public String guardar(@RequestParam String accion,
                          @RequestParam(required = false) Integer idPersona,
                          @RequestParam(required = false) String codigo,
                          Model model, RedirectAttributes avisos) {
        try {
            switch (accion) {
                case "asignar":
                    if (idPersona == null) {
                        throw new DatosInvalidosException("Elegí a quién asignar.");
                    }
                    asignar(idPersona, codigo);
                    avisos.addFlashAttribute("mensaje", "Se asignó la habitación " + codigo + ".");
                    break;
                case "quitar":
                    quitar(codigo);
                    avisos.addFlashAttribute("mensaje", "Se liberó la habitación " + codigo + ".");
                    break;
                default:
                    throw new DatosInvalidosException("Acción desconocida.");
            }
            return "redirect:" + ruta;
        } catch (GeriatricoException e) {
            model.addAttribute("error", e.getMessage());
            return vista(model);
        }
    }

    private String vista(Model model) {
        model.addAttribute("titulo", titulo);
        model.addAttribute("tipoPersona", tipoPersona);
        model.addAttribute("ruta", ruta);
        model.addAttribute("relaciones", asignadas());
        model.addAttribute("personas", personasParaElegir());
        model.addAttribute("habitaciones", habitacionesParaElegir());
        return "relaciones";
    }
}
