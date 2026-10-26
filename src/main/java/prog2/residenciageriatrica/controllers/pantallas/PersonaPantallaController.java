package prog2.residenciageriatrica.controllers.pantallas;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prog2.residenciageriatrica.dto.PersonaDTO;
import prog2.residenciageriatrica.excepciones.DatosInvalidosException;
import prog2.residenciageriatrica.excepciones.GeriatricoException;
import prog2.residenciageriatrica.servicios.PersonaServicio;

/**
 * Pantalla común de residentes y enfermeros: listar, alta, modificación y baja.
 * Cada hija dice su URL y su título. Llama al servicio directo: no pasa por la API.
 * @author Emanuel
 */
public abstract class PersonaPantallaController {

    private final PersonaServicio<?> servicio;
    private final String ruta;      // "/residentes" o "/enfermeros"
    private final String titulo;    // "Residentes" o "Enfermeros"

    protected PersonaPantallaController(PersonaServicio<?> servicio, String ruta, String titulo) {
        this.servicio = servicio;
        this.ruta = ruta;
        this.titulo = titulo;
    }

    // GET /residentes → la lista; con ?editar=3 además carga a la persona 3 en el formulario
    @GetMapping
    public String mostrar(@RequestParam(required = false) Integer editar, Model model) {
        if (editar != null) {
            try {
                model.addAttribute("editando", servicio.obtener(editar));
            } catch (GeriatricoException e) {
                model.addAttribute("error", e.getMessage());
            }
        }
        return vista(model);
    }

    // POST: los formularios HTML solo mandan GET o POST, así que "accion" dice qué hacer
    @PostMapping
    public String guardar(@RequestParam String accion,
                          @RequestParam(required = false) Integer id,
                          @RequestParam(required = false) String dni,
                          @RequestParam(required = false) String nombre,
                          @RequestParam(required = false) String apellido,
                          Model model, RedirectAttributes avisos) {
        PersonaDTO datos = new PersonaDTO(dni, nombre, apellido, id);
        try {
            switch (accion) {
                case "alta":
                    servicio.crear(datos);
                    avisos.addFlashAttribute("mensaje", "Se dio de alta correctamente.");
                    break;
                case "modificar":
                    servicio.modificar(exigirId(id), datos);
                    avisos.addFlashAttribute("mensaje", "Se modificó correctamente.");
                    break;
                case "baja":
                    servicio.eliminar(exigirId(id));
                    avisos.addFlashAttribute("mensaje", "Se dio de baja correctamente.");
                    break;
                default:
                    throw new DatosInvalidosException("Acción desconocida.");
            }
            return "redirect:" + ruta;              // Post/Redirect/Get: F5 no repite el alta
        } catch (GeriatricoException e) {
            model.addAttribute("error", e.getMessage());
            if (!accion.equals("baja")) {
                model.addAttribute("editando", datos);   // vuelve a mostrar lo que escribió el usuario
            }
            return vista(model);
        }
    }

    // modificar y baja necesitan saber a quién: el id viaja en un campo oculto del formulario
    private int exigirId(Integer id) {
        if (id == null) {
            throw new DatosInvalidosException("Falta indicar a quién.");
        }
        return id;
    }

    private String vista(Model model) {
        model.addAttribute("titulo", titulo);
        model.addAttribute("ruta", ruta);
        model.addAttribute("personas", servicio.listar());
        return "personas";                          // → /WEB-INF/jsp/personas.jsp
    }
}
