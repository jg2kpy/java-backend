package py.una.pol.PoliBank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.una.pol.PoliBank.dto.CuentaResponse;
import py.una.pol.PoliBank.dto.MovimientoResponse;
import py.una.pol.PoliBank.model.Cuenta;
import py.una.pol.PoliBank.model.Movimiento;
import py.una.pol.PoliBank.repository.CuentaRepository;
import py.una.pol.PoliBank.repository.MovimientoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    public CuentaResponse obtenerCuenta(String username) {
        Cuenta cuenta = cuentaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada para el usuario: " + username));
        return CuentaResponse.fromEntity(cuenta);
    }

    public List<MovimientoResponse> obtenerMovimientos(String username) {
        Cuenta cuenta = cuentaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada para el usuario: " + username));

        List<Movimiento> movimientos = movimientoRepository.findByCuentaIdOrderByFechaDesc(cuenta.getId());

        List<MovimientoResponse> respuestas = new ArrayList<>();
        for (Movimiento movimiento : movimientos) {
            respuestas.add(MovimientoResponse.fromEntity(movimiento));
        }
        return respuestas;
    }
}
