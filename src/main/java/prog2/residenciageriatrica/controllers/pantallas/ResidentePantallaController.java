package prog2.residenciageriatrica.controllers.pantallas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import prog2.residenciageriatrica.servicios.ResidenteServicio;

/**
 * Pantalla de residentes.
 * @author Emanuel
 */
@Controller
@RequestMapping("/residentes")
public class ResidentePantallaController extends PersonaPantallaController {

    public ResidentePantallaController(ResidenteServicio servicio) {
        super(servicio, "/residentes", "Residentes");
    }
}
