package py.una.pol.PoliBank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import py.una.pol.PoliBank.dto.TransferenciaRequest;
import py.una.pol.PoliBank.model.Cuenta;
import py.una.pol.PoliBank.model.Transferencia;
import py.una.pol.PoliBank.repository.CuentaRepository;
import py.una.pol.PoliBank.repository.MovimientoRepository;
import py.una.pol.PoliBank.repository.TransferenciaRepository;
import py.una.pol.PoliBank.service.SaldoInsuficienteException;
import py.una.pol.PoliBank.service.TransferenciaService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferenciaServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private TransferenciaRepository transferenciaRepository;

    @Mock
    private MovimientoRepository movimientoRepository;

    @InjectMocks
    private TransferenciaService transferenciaService;

    @Test
    void transferencia_exitosa_actualiza_saldos_en_ambas_cuentas() {
        // Dado
        Cuenta origen = new Cuenta();
        origen.setId(1L);
        origen.setUsername("juan");
        origen.setSaldo(10_000L);

        Cuenta destino = new Cuenta();
        destino.setId(2L);
        destino.setUsername("maria");
        destino.setSaldo(5_000L);

        TransferenciaRequest request = new TransferenciaRequest();
        request.setDestinoUsername("maria");
        request.setMonto(3_000L);

        when(cuentaRepository.findByUsername("juan")).thenReturn(Optional.of(origen));
        when(cuentaRepository.findByUsername("maria")).thenReturn(Optional.of(destino));

        Transferencia transferenciaGuardada = new Transferencia();
        transferenciaGuardada.setId(1L);
        transferenciaGuardada.setMonto(3_000L);
        when(transferenciaRepository.save(any(Transferencia.class))).thenReturn(transferenciaGuardada);

        // Cuando
        transferenciaService.transferir("juan", request);

        // Entonces: saldo origen disminuye, saldo destino aumenta
        assertEquals(7_000L, origen.getSaldo(), "El saldo origen debe haber disminuido en el monto transferido");
        assertEquals(8_000L, destino.getSaldo(), "El saldo destino debe haber aumentado en el monto transferido");
    }

    @Test
    void transferencia_con_saldo_insuficiente_lanza_excepcion_y_no_modifica_saldos() {
        // Dado
        Cuenta origen = new Cuenta();
        origen.setId(1L);
        origen.setUsername("juan");
        origen.setSaldo(1_000L);

        Cuenta destino = new Cuenta();
        destino.setId(2L);
        destino.setUsername("maria");
        destino.setSaldo(5_000L);

        TransferenciaRequest request = new TransferenciaRequest();
        request.setDestinoUsername("maria");
        request.setMonto(5_000L); // mayor al saldo disponible

        when(cuentaRepository.findByUsername("juan")).thenReturn(Optional.of(origen));
        when(cuentaRepository.findByUsername("maria")).thenReturn(Optional.of(destino));

        // Cuando / Entonces
        assertThrows(SaldoInsuficienteException.class, () ->
                transferenciaService.transferir("juan", request),
                "Debe lanzar SaldoInsuficienteException cuando el monto supera el saldo"
        );

        // Los saldos no deben haberse modificado
        assertEquals(1_000L, origen.getSaldo(), "El saldo origen no debe cambiar ante saldo insuficiente");
        assertEquals(5_000L, destino.getSaldo(), "El saldo destino no debe cambiar ante saldo insuficiente");

        // No debe haberse guardado ninguna transferencia ni movimiento
        verify(transferenciaRepository, never()).save(any());
        verify(movimientoRepository, never()).save(any());
    }
}
