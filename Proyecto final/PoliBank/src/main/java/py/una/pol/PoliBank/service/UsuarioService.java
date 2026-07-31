package py.una.pol.PoliBank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.una.pol.PoliBank.dto.RegistroRequest;
import py.una.pol.PoliBank.dto.RegistroResponse;
import py.una.pol.PoliBank.model.Cuenta;
import py.una.pol.PoliBank.model.Usuario;
import py.una.pol.PoliBank.repository.CuentaRepository;
import py.una.pol.PoliBank.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Registra un nuevo usuario y crea su cuenta bancaria con saldo inicial 0.
     * Ambas operaciones se realizan en una misma transacción.
     */
    @Transactional
    public RegistroResponse registrar(RegistroRequest request) {
        if (usuarioRepository.existsById(request.getUsername())) {
            throw new IllegalArgumentException("El usuario ya existe: " + request.getUsername());
        }

        // Hashear la contraseña antes de persistir — NUNCA guardar en texto plano
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setNombre(request.getNombre());
        usuario.setRol("USER");
        usuarioRepository.save(usuario);

        // Crear la cuenta asociada con saldo inicial 0
        Cuenta cuenta = new Cuenta();
        cuenta.setUsername(request.getUsername());
        cuenta.setSaldo(0L);
        cuentaRepository.save(cuenta);

        return new RegistroResponse(
                usuario.getUsername(),
                usuario.getNombre(),
                "Usuario registrado correctamente"
        );
    }
}
