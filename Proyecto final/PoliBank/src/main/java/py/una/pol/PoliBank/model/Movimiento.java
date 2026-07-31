package py.una.pol.PoliBank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movimiento")
@Getter
@Setter
@NoArgsConstructor
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cuenta_id")
    private Long cuentaId;

    @Column(name = "transferencia_id")
    private Long transferenciaId;

    // Valores válidos: 'DEBITO' o 'CREDITO' (restricción en la BD)
    private String tipo;

    // Monto en guaraníes: siempre Long
    private Long monto;

    @Column(name = "saldo_posterior")
    private Long saldoPosterior;

    // La BD maneja el DEFAULT datetime('now')
    @Column(insertable = false, updatable = false)
    private String fecha;
}
