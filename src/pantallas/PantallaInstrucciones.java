package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import base.PanelJuego;

/**
 * 
 * @author Tomas Macias Castela
 *
 */
public class PantallaInstrucciones implements Pantalla {

	/** PANEL JUEGO **/
	PanelJuego panelJuego;

	/** COLOR Y FUENTE **/
	final Font fuenteLetra = new Font("", Font.BOLD, 15);
	final Font fuenteVolver = new Font("", Font.BOLD, 20);
	Color colorLetra = Color.RED;

	/** IMAGENES **/
	private BufferedImage fondo;
	private Image fondoEscalado, gifImage;

	public PantallaInstrucciones(PanelJuego panel) {
		inicializarPantalla(panel);
	}

	@Override
	public void inicializarPantalla(PanelJuego panel) {
		this.panelJuego = panel;
		try {
			fondo = ImageIO.read(new File(".//img//fondo.jpg"));
			gifImage = new ImageIcon(".//img//gif.gif").getImage();
		} catch (Exception e) {
			System.out.println("PROBLEMAS AL CARGAR LAS IMAGENES FIN DEL PROGRAMA");
		}
		redimensionarPantalla();

	}

	@Override
	public void ejecutarFrame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void redimensionarPantalla() {
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
				BufferedImage.SCALE_SMOOTH);

	}

	@Override
	public void pulsarTecla(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			panelJuego.setPantalla(new PantallaInicial(panelJuego));
		}

	}

	@Override
	public void soltarTecla(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
		mensajeInstruccion(g);
	}

	/**
	 * Metodo para pintar en la imagen.
	 * 
	 * @param g
	 */
	public void mensajeInstruccion(Graphics g) {
		g.setFont(fuenteVolver);
		g.setColor(colorLetra);
		g.drawString("Pulse 'ESC' para volver al menu.", 30, 30);
		g.setFont(fuenteLetra);
		g.setColor(Color.GREEN);
		g.drawString("A | <-: Movimiento para la izquierda.", 30, 70);
		g.drawString("D | ->: Movimiento para la derecha.", 30, 110);
		g.drawString("|________|: Iniciar juego.", 30, 150);
		g.drawString("P: Pausar/Reanudar juego", 30, 190);

		mensajePowerUp(g);
		g.drawImage(gifImage, 325, 480, null);

	}

	private void mensajePowerUp(Graphics g) {
		g.drawString("Power UP:", 30, 230);
		g.setColor(Color.GRAY);
		g.fillRect(50, 260, 30, 10);
		g.drawString("Aumento de tamaño la barra.", 90, 270);
		g.setColor(Color.YELLOW);
		g.fillRect(50, 300, 30, 10);
		g.drawString("Aumento de velocidad de la bola.", 90, 310);
		g.setColor(Color.WHITE);
		g.fillRect(50, 340, 30, 10);
		g.drawString("Disminucion de tamaño la barra.", 90, 350);
		g.setColor(Color.RED);
		g.fillRect(50, 380, 30, 10);
		g.drawString("Rojo: Lanzamiento de una bola extra.", 90, 390);
		g.setColor(Color.PINK);
		g.fillRect(50, 420, 30, 10);
		g.drawString("Aumento de velocidad a la barra.", 90, 430);
	}

}
