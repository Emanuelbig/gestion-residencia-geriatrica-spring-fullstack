package prog2.residenciageriatrica.controllers.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prog2.residenciageriatrica.servicios.EnfermeroServicio;

/**
 * Endpoints de enfermeros: todos heredados de PersonaController.
 * @author Emanuel
 */
@RestController
@RequestMapping("/api/enfermeros")
public class EnfermeroController extends PersonaController {

    public EnfermeroController(EnfermeroServicio servicio) {
        super(servicio);
    }
}
