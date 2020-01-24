package pantallas;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import base.PanelJuego;



/**
 * 
 * @author Tomas Macias Castela
 *
 */
public interface Pantalla {

	public void inicializarPantalla(PanelJuego panelJuego);

	// Hilo.	
	public void ejecutarFrame();

	// Listeners.
	public void redimensionarPantalla();
	
	public void pulsarTecla(KeyEvent e);
	
	public void soltarTecla(KeyEvent e);

	// Pintar la pantalla.
	public void pintarPantalla(Graphics g);

}
