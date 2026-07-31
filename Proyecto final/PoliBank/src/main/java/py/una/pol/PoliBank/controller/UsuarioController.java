package py.una.pol.PoliBank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import py.una.pol.PoliBank.dto.RegistroRequest;
import py.una.pol.PoliBank.dto.RegistroResponse;
import py.una.pol.PoliBank.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint público: el usuario todavía no tiene credenciales para autenticarse
    @PostMapping
    public ResponseEntity<RegistroResponse> registrar(@RequestBody RegistroRequest request) {
        RegistroResponse respuesta = usuarioService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}
