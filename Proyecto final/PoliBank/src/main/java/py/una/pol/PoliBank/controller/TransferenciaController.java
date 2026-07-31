package py.una.pol.PoliBank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import py.una.pol.PoliBank.dto.TransferenciaRequest;
import py.una.pol.PoliBank.dto.TransferenciaResponse;
import py.una.pol.PoliBank.service.TransferenciaService;

import java.security.Principal;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @PostMapping
    public ResponseEntity<TransferenciaResponse> transferir(
            @RequestBody TransferenciaRequest request,
            Principal principal) {
        TransferenciaResponse respuesta = transferenciaService.transferir(principal.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}
