package base;

import javax.swing.JPanel;

public class Cronometro extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2040147249994050872L;
	Thread hilo = null;

	double tiempoTranscurrido;
	double tiempoAcumulado;
	double tiempoInicio;
	String tiempo;
	boolean cronoActivo;

	@Override
	public void run() {
		tiempoInicio = System.nanoTime();

		while (cronoActivo) {
			calcularTiempoTranscurrido();
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void comenzar(double tiempoAcumulado) {
		this.tiempoAcumulado = tiempoAcumulado;
		if (hilo == null) {
			cronoActivo = true;
			hilo = new Thread(this);
			hilo.start();
		}

	}

	public void parar(double tiempoAcumulado) {
		if (hilo != null) {
			cronoActivo = false;
			hilo = null;
		} else {
			comenzar(tiempoAcumulado);
		}

	}

	/**
	 * Metodo para calcular el tiempo que ha transcurrido. Le sumamos el
	 * tiempoAcumulado por si realizamos una pausa.
	 */
	private void calcularTiempoTranscurrido() {
		tiempoTranscurrido = ((System.nanoTime() - tiempoInicio + tiempoAcumulado) / 1e9);
	}

	public void setTiempoTranscurrido(double tiempoTranscurrido) {
		this.tiempoTranscurrido = tiempoTranscurrido;
	}

	public double getTiempoTranscurrido() {
		return tiempoTranscurrido;
	}

}
