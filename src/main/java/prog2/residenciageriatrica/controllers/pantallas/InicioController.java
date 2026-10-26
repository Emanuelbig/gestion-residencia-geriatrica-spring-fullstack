package prog2.residenciageriatrica.controllers.pantallas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Menú principal de la aplicación web.
 * @author Emanuel
 */
@Controller
public class InicioController {

    @GetMapping("/")
    public String inicio() {
        return "inicio";                            // → /WEB-INF/jsp/inicio.jsp
    }
}
