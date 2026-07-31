package py.una.pol.PoliBank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    private String username;

    private String password;

    private String nombre;

    private String rol;

    // La BD maneja el DEFAULT datetime('now'), no lo seteamos desde Java
    @Column(name = "fecha_alta", insertable = false, updatable = false)
    private String fechaAlta;
}
