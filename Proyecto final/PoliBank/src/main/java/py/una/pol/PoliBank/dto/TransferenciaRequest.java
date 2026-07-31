package py.una.pol.PoliBank.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransferenciaRequest {

    // Username del usuario destino
    private String destinoUsername;

    // Monto en guaraníes (entero, sin decimales)
    private Long monto;
}
