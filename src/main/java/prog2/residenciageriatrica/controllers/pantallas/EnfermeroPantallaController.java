package prog2.residenciageriatrica.controllers.pantallas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import prog2.residenciageriatrica.servicios.EnfermeroServicio;

/**
 * Pantalla de enfermeros.
 * @author Emanuel
 */
@Controller
@RequestMapping("/enfermeros")
public class EnfermeroPantallaController extends PersonaPantallaController {

    public EnfermeroPantallaController(EnfermeroServicio servicio) {
        super(servicio, "/enfermeros", "Enfermeros");
    }
}
