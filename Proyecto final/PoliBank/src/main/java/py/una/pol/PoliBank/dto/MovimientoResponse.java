package py.una.pol.PoliBank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import py.una.pol.PoliBank.model.Movimiento;

@Getter
@AllArgsConstructor
public class MovimientoResponse {

    private Long id;
    private String tipo;
    private Long monto;
    private Long saldoPosterior;
    private String fecha;
    private Long transferenciaId;

    public static MovimientoResponse fromEntity(Movimiento movimiento) {
        return new MovimientoResponse(
                movimiento.getId(),
                movimiento.getTipo(),
                movimiento.getMonto(),
                movimiento.getSaldoPosterior(),
                movimiento.getFecha(),
                movimiento.getTransferenciaId()
        );
    }
}
