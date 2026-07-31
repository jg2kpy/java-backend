package py.una.pol.PoliBank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.una.pol.PoliBank.model.Transferencia;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
}
