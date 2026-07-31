package py.una.pol.PoliBank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.una.pol.PoliBank.model.Cuenta;

import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByUsername(String username);
}
