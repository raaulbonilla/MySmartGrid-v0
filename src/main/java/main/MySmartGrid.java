package main;

import energy.Consumo;
import energy.RedEnergetica;
import java.util.List;
import java.util.ArrayList;

public class MySmartGrid {
	
    public static void main(String[] args) {
        RedEnergetica red = new RedEnergetica(
                Config.NUM_ZONAS,
                Config.CAPACIDAD_BATERIA,
                Config.NIVEL_INICIAL_BATERIA
        );
        
        List<Consumo> consumos = Consumo.consumosDesdeFichero(Config.FICHERO_CONSUMOS);
        System.out.println("Leidos " + consumos.size() + " consumos desde " + Config.FICHERO_CONSUMOS);
      
        List<Thread> threads = new ArrayList<>();

        for (Consumo c : consumos) {
            Thread t = new Thread(new TramitarConsumo(red, c));
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("No se han procesado todos los hilos al completo");
                break;
            }
        red.imprimeAuditoria();
        }
    }
}
