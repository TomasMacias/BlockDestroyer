package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import base.PanelJuego;

/**
 * 
 * @author Tomas Macias Castela
 *
 */
public class PantallaInicial implements Pantalla {

	/** PANEL JUEGO **/
	PanelJuego panelJuego;

	/** COLOR Y FUENTE **/
	final Font fuenteJuego = new Font("TimesRoman", Font.BOLD, 40);
	final Font fuenteInstrucciones = new Font("TimesRoman", Font.BOLD, 30);
	final Font fuenteSalir = new Font("TimesRoman", Font.BOLD, 20);
	Color colorLetraInicio = Color.RED;

	/** DIRECCION DE IMAGEN FONDO **/
	// https://www.freepik.es/fotos-vectores-gratis/fondo

	/** FONDO **/
	private BufferedImage fondo;

	public PantallaInicial(PanelJuego panelJuego) {
		inicializarPantalla(panelJuego);
	}

	@Override
	public void inicializarPantalla(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
		try {
			fondo = ImageIO.read(new File("img/fondo.jpg"));

		} catch (Exception e) {
			System.out.println("PROBLEMAS AL CARGAR LAS IMAGENES FIN DEL PROGRAMA");
		}
	}

	@Override
	public void ejecutarFrame() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		colorLetraInicio = colorLetraInicio == Color.RED ? Color.WHITE : Color.RED;

	}

	@Override
	public void pulsarTecla(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 10:
			panelJuego.setPantalla(new PantallaJuego(panelJuego));
			break;
		case 73:
			panelJuego.setPantalla(new PantallaInstrucciones(panelJuego));
			break;
		case 27:
			System.exit(0);
			break;
		default:
			break;
		}

	}

	@Override
	public void soltarTecla(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void redimensionarPantalla() {
		// TODO Auto-generated method stub
	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), BufferedImage.SCALE_SMOOTH),
				0, 0, null);
		mensajeJugar(g);
		mensajeInstrucciones(g);
		mensajeSalir(g);

	}

	/**
	 * Metodo para pintar en la imagen.
	 * 
	 * @param g
	 */
	public void mensajeJugar(Graphics g) {
		g.setFont(fuenteJuego);
		g.setColor(colorLetraInicio);
		g.drawString("Presione 'Enter' para jugar", panelJuego.getWidth() / 2 - 240, panelJuego.getHeight() / 2 + 100);
	}

	public void mensajeInstrucciones(Graphics g) {
		g.setFont(fuenteInstrucciones);
		g.setColor(Color.GREEN);
		g.drawString("PULSA 'I' INSTRUCCIONES", panelJuego.getWidth() / 2 - 200, panelJuego.getHeight() / 2 + 155);
	}

	public void mensajeSalir(Graphics g) {
		g.setFont(fuenteSalir);
		g.setColor(Color.YELLOW);
		g.drawString("PULSA 'ESC' PARA SALIR", panelJuego.getWidth() / 2 - 120, panelJuego.getHeight() / 2 + 210);
	}

}
