package main;

import energy.Consumo;
import energy.RedEnergetica;

public class TramitarConsumo implements Runnable {
	private final RedEnergetica red;
	private final Consumo consumo;
	
	public TramitarConsumo(RedEnergetica red, Consumo consumo) {
		this.red = red;
		this.consumo = consumo;
	}
	
	@Override
	public void run() {
		String resultado = red.getZona(consumo.getZona()).tramitarConsumo(consumo);
        red.getZona(consumo.getZona()).getVentana().traza (consumo.getIdConsumo()+ " - Tramitado: "+resultado);
	}
	
}
