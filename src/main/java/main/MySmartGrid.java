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

        try {
            for (int i = 0; i < threads.size(); i++) {
                threads.get(i).join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        red.imprimeAuditoria();
    }
}
