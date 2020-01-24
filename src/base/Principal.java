package base;

import java.awt.EventQueue;

/**
 * Principal para el juego.
 * 
 * @author Tomas Macias Castela
 *
 */
public class Principal {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
					ventanaPrincipal.inicializar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
