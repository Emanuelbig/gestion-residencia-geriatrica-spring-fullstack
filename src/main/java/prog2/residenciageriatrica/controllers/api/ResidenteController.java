package prog2.residenciageriatrica.controllers.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prog2.residenciageriatrica.servicios.ResidenteServicio;

/**
 * Endpoints de residentes: todos heredados de PersonaController.
 * @author Emanuel
 */
@RestController
@RequestMapping("/api/residentes")
public class ResidenteController extends PersonaController {

    public ResidenteController(ResidenteServicio servicio) {
        super(servicio);
    }
}
