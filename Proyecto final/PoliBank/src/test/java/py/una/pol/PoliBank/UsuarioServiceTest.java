package py.una.pol.PoliBank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import py.una.pol.PoliBank.dto.RegistroRequest;
import py.una.pol.PoliBank.model.Cuenta;
import py.una.pol.PoliBank.model.Usuario;
import py.una.pol.PoliBank.repository.CuentaRepository;
import py.una.pol.PoliBank.repository.UsuarioRepository;
import py.una.pol.PoliBank.service.UsuarioService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void registro_la_password_se_persiste_hasheada_nunca_en_texto_plano() {
        // Dado
        RegistroRequest request = new RegistroRequest();
        request.setUsername("nuevo");
        request.setPassword("mi-clave-secreta");
        request.setNombre("Nuevo Usuario");

        String hashBcrypt = "$2a$10$abcdefghijklmnopqrstuvwHashSimuladoParaElTest";

        when(usuarioRepository.existsById("nuevo")).thenReturn(false);
        when(passwordEncoder.encode("mi-clave-secreta")).thenReturn(hashBcrypt);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocacion -> invocacion.getArgument(0));
        when(cuentaRepository.save(any(Cuenta.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

        // Cuando
        usuarioService.registrar(request);

        // Entonces: capturar el usuario que se guardó y verificar que la password está hasheada
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());

        Usuario usuarioGuardado = captor.getValue();
        assertNotEquals("mi-clave-secreta", usuarioGuardado.getPassword(),
                "La contraseña no debe quedar en texto plano");
        assertEquals(hashBcrypt, usuarioGuardado.getPassword(),
                "La contraseña debe estar hasheada con BCrypt");
    }
}
