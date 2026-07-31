package py.una.pol.PoliBank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.una.pol.PoliBank.dto.TransferenciaRequest;
import py.una.pol.PoliBank.dto.TransferenciaResponse;
import py.una.pol.PoliBank.model.Cuenta;
import py.una.pol.PoliBank.model.Movimiento;
import py.una.pol.PoliBank.model.Transferencia;
import py.una.pol.PoliBank.repository.CuentaRepository;
import py.una.pol.PoliBank.repository.MovimientoRepository;
import py.una.pol.PoliBank.repository.TransferenciaRepository;

@Service
public class TransferenciaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    /**
     * Realiza una transferencia entre dos cuentas en una única transacción.
     * Si cualquier paso falla, Spring revierte todo automáticamente.
     */
    @Transactional
    public TransferenciaResponse transferir(String usernameOrigen, TransferenciaRequest request) {
        Cuenta origen = cuentaRepository.findByUsername(usernameOrigen)
                .orElseThrow(() -> new RuntimeException("Cuenta de origen no encontrada"));

        Cuenta destino = cuentaRepository.findByUsername(request.getDestinoUsername())
                .orElseThrow(() -> new RuntimeException("Cuenta de destino no encontrada: " + request.getDestinoUsername()));

        // Validar saldo suficiente antes de cualquier modificación
        if (origen.getSaldo() < request.getMonto()) {
            throw new SaldoInsuficienteException(
                    "Saldo insuficiente. Saldo actual: " + origen.getSaldo() +
                    " Gs. Monto solicitado: " + request.getMonto() + " Gs."
            );
        }

        // Debitar cuenta origen y acreditar cuenta destino
        origen.setSaldo(origen.getSaldo() - request.getMonto());
        destino.setSaldo(destino.getSaldo() + request.getMonto());
        cuentaRepository.save(origen);
        cuentaRepository.save(destino);

        // Registrar la transferencia
        Transferencia transferencia = new Transferencia();
        transferencia.setCuentaOrigenId(origen.getId());
        transferencia.setCuentaDestinoId(destino.getId());
        transferencia.setMonto(request.getMonto());
        transferencia = transferenciaRepository.save(transferencia);

        // Registrar movimiento DEBITO en cuenta origen
        Movimiento debito = new Movimiento();
        debito.setCuentaId(origen.getId());
        debito.setTransferenciaId(transferencia.getId());
        debito.setTipo("DEBITO");
        debito.setMonto(request.getMonto());
        debito.setSaldoPosterior(origen.getSaldo());
        movimientoRepository.save(debito);

        // Registrar movimiento CREDITO en cuenta destino
        Movimiento credito = new Movimiento();
        credito.setCuentaId(destino.getId());
        credito.setTransferenciaId(transferencia.getId());
        credito.setTipo("CREDITO");
        credito.setMonto(request.getMonto());
        credito.setSaldoPosterior(destino.getSaldo());
        movimientoRepository.save(credito);

        return new TransferenciaResponse(
                transferencia.getId(),
                transferencia.getMonto(),
                transferencia.getFecha(),
                origen.getId(),
                destino.getId()
        );
    }
}
