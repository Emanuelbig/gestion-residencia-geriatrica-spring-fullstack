package prog2.residenciageriatrica.controllers.pantallas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prog2.residenciageriatrica.dto.HabitacionDTO;
import prog2.residenciageriatrica.excepciones.DatosInvalidosException;
import prog2.residenciageriatrica.excepciones.GeriatricoException;
import prog2.residenciageriatrica.servicios.HabitacionServicio;

/**
 * Pantalla de habitaciones: listar, ver el detalle, alta (el código se genera solo) y baja.
 * @author Emanuel
 */
@Controller
@RequestMapping("/habitaciones")
public class HabitacionPantallaController {

    private static final String RUTA = "/habitaciones";

    private final HabitacionServicio servicio;

    public HabitacionPantallaController(HabitacionServicio servicio) {
        this.servicio = servicio;
    }

    // GET /habitaciones → la lista; con ?ver=HAB-101 además muestra el detalle de esa habitación
    @GetMapping
    public String mostrar(@RequestParam(required = false) String ver, Model model) {
        if (ver != null) {
            try {
                model.addAttribute("detalle", servicio.obtener(ver));
            } catch (GeriatricoException e) {
                model.addAttribute("error", e.getMessage());
            }
        }
        return vista(model);
    }

    @PostMapping
    public String guardar(@RequestParam String accion,
                          @RequestParam(required = false) String codigo,
                          Model model, RedirectAttributes avisos) {
        try {
            switch (accion) {
                case "alta":
                    HabitacionDTO nueva = servicio.crear();
                    avisos.addFlashAttribute("mensaje", "Se creó la habitación " + nueva.getCodigo() + ".");
                    break;
                case "baja":
                    servicio.eliminar(codigo);
                    avisos.addFlashAttribute("mensaje", "Se eliminó la habitación " + codigo + ".");
                    break;
                default:
                    throw new DatosInvalidosException("Acción desconocida.");
            }
            return "redirect:" + RUTA;
        } catch (GeriatricoException e) {
            model.addAttribute("error", e.getMessage());
            return vista(model);
        }
    }

    private String vista(Model model) {
        model.addAttribute("ruta", RUTA);
        model.addAttribute("habitaciones", servicio.listar());
        return "habitaciones";
    }
}
