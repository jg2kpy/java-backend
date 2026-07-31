package py.una.pol.PoliBank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import py.una.pol.PoliBank.model.Cuenta;

@Getter
@AllArgsConstructor
public class CuentaResponse {

    private Long id;
    private String username;
    private Long saldo;
    private String fechaCreacion;

    public static CuentaResponse fromEntity(Cuenta cuenta) {
        return new CuentaResponse(
                cuenta.getId(),
                cuenta.getUsername(),
                cuenta.getSaldo(),
                cuenta.getFechaCreacion()
        );
    }
}
