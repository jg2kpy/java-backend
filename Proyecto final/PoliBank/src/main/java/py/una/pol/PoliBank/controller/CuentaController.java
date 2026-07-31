package py.una.pol.PoliBank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import py.una.pol.PoliBank.dto.CuentaResponse;
import py.una.pol.PoliBank.dto.MovimientoResponse;
import py.una.pol.PoliBank.service.CuentaService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    /**
     * Devuelve el saldo del usuario autenticado.
     * La cuenta se resuelve a partir del Principal (Spring Security),
     * nunca de un parámetro de URL.
     */
    @GetMapping("/me")
    public ResponseEntity<CuentaResponse> obtenerSaldo(Principal principal) {
        CuentaResponse respuesta = cuentaService.obtenerCuenta(principal.getName());
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Devuelve el historial de movimientos del usuario autenticado.
     */
    @GetMapping("/me/movimientos")
    public ResponseEntity<List<MovimientoResponse>> obtenerMovimientos(Principal principal) {
        List<MovimientoResponse> movimientos = cuentaService.obtenerMovimientos(principal.getName());
        return ResponseEntity.ok(movimientos);
    }
}
