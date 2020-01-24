package base;

import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

import pantallas.Pantalla;
import pantallas.PantallaInicial;

/**
 * 
 * @author Tomas Macias Castela
 *
 */
public class PanelJuego extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1638388215732609321L;

	/** PANTALLAS **/
	private Pantalla pantallaEjecucion;

	// El contructor
	public PanelJuego() {
		// Creamos la pantalla inicial al principio. Le pasamos el panel juego.
		pantallaEjecucion = new PantallaInicial(this);

		// HILO.
		new Thread(this).start();

		// LISTENERS.
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				pantallaEjecucion.redimensionarPantalla();
			}
		});

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				pantallaEjecucion.pulsarTecla(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				pantallaEjecucion.soltarTecla(e);
			}

		});
	}

	// METODO QUE SE LLAMA AUTOMATICAMENTE.
	@Override
	public void paintComponent(Graphics g) {
		pantallaEjecucion.pintarPantalla(g);
	}

	@Override
	public void run() {
		setFocusable(true);
		requestFocusInWindow();
		while (true) {
			repaint();
			pantallaEjecucion.ejecutarFrame();

		}

	}

	public void setPantalla(Pantalla pantalla) {
		this.pantallaEjecucion = pantalla;
	}

}
