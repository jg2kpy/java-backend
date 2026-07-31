package py.una.pol.PoliBank.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OfertaPrestamoJob {

    // Se ejecuta cada 10 minutos (600.000 ms). Solo imprime en consola, sin persistencia.
    @Scheduled(fixedRate = 600000)
    public void emitirOferta() {
        System.out.println("Tienes una oferta de préstamo disponible de 1.000.000 Gs");
    }
}
