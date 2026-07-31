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
@Table(name = "cuenta")
@Getter
@Setter
@NoArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    // Monto en guaraníes: siempre Long, nunca double/BigDecimal
    private Long saldo;

    // La BD maneja el DEFAULT datetime('now')
    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private String fechaCreacion;
}
