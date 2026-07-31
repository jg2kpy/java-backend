package py.una.pol.PoliBank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransferenciaResponse {

    private Long id;
    private Long monto;
    private String fecha;
    private Long cuentaOrigenId;
    private Long cuentaDestinoId;
}
