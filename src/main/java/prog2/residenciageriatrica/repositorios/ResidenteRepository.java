package prog2.residenciageriatrica.repositorios;

import org.springframework.stereotype.Repository;
import prog2.residenciageriatrica.modelos.Residente;

/**
 * @author Emanuel Morano Sole
 */

@Repository
public interface ResidenteRepository extends PersonaRepository<Residente> {
}
