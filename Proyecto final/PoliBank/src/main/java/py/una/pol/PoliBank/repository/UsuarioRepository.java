package py.una.pol.PoliBank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.una.pol.PoliBank.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    // findById(username) ya viene de JpaRepository con PK String
}
