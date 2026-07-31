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
@Table(name = "transferencia")
@Getter
@Setter
@NoArgsConstructor
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cuenta_origen_id")
    private Long cuentaOrigenId;

    @Column(name = "cuenta_destino_id")
    private Long cuentaDestinoId;

    // Monto en guaraníes: siempre Long
    private Long monto;

    // La BD maneja el DEFAULT datetime('now')
    @Column(insertable = false, updatable = false)
    private String fecha;
}
