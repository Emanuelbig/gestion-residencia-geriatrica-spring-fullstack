package prog2.residenciageriatrica.controllers.pantallas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prog2.residenciageriatrica.excepciones.DatosInvalidosException;
import prog2.residenciageriatrica.excepciones.GeriatricoException;
import prog2.residenciageriatrica.servicios.EnfermeroServicio;
import prog2.residenciageriatrica.servicios.ReporteServicio;
import prog2.residenciageriatrica.servicios.ResidenteServicio;

/**
 * Pantalla de consultas: las 3 de la consigna. Solo lee, por eso todo es GET.
 * @author Emanuel
 */
@Controller
@RequestMapping("/reportes")
public class ReportePantallaController {

    private final ReporteServicio reportes;
    private final ResidenteServicio residentes;
    private final EnfermeroServicio enfermeros;

    public ReportePantallaController(ReporteServicio reportes, ResidenteServicio residentes,
                                     EnfermeroServicio enfermeros) {
        this.reportes = reportes;
        this.residentes = residentes;
        this.enfermeros = enfermeros;
    }

    // GET /reportes?consulta=habitacion&id=1 → hace la consulta elegida y muestra el resultado
    @GetMapping
    public String consultar(@RequestParam(required = false) String consulta,
                            @RequestParam(required = false) Integer id,
                            Model model) {
        model.addAttribute("residentes", residentes.listar());
        model.addAttribute("enfermeros", enfermeros.listar());
        if (consulta != null) {
            try {
                if (id == null) {
                    throw new DatosInvalidosException("Elegí a quién consultar.");
                }
                switch (consulta) {
                    case "habitacion":
                        model.addAttribute("habitacionDeResidente", reportes.habitacionDeResidente(id));
                        break;
                    case "residentes":
                        model.addAttribute("enfermero", enfermeros.obtener(id));
                        model.addAttribute("residentesDeEnfermero", reportes.residentesDeEnfermero(id));
                        break;
                    case "enfermero":
                        model.addAttribute("residente", residentes.obtener(id));
                        // null si no tiene: el JSP muestra "no tiene enfermero asignado"
                        model.addAttribute("enfermeroDeResidente", reportes.enfermeroDeResidente(id).orElse(null));
                        break;
                    default:
                        throw new DatosInvalidosException("Consulta desconocida.");
                }
            } catch (GeriatricoException e) {
                model.addAttribute("error", e.getMessage());
            }
        }
        return "reportes";
    }
}
