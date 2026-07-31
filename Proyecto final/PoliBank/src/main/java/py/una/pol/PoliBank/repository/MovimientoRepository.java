package py.una.pol.PoliBank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.una.pol.PoliBank.model.Movimiento;

import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByCuentaIdOrderByFechaDesc(Long cuentaId);
}
