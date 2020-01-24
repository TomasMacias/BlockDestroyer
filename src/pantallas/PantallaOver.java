package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
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
public class PantallaOver implements Pantalla {

	/** COLOR Y FUENTES. **/
	Color colorLetra = Color.PINK;
	Color colorCuadrado = Color.GREEN;
	final Font fuenteFinal = new Font("", Font.BOLD, 20);
	final Font fuentePuntuacion = new Font("Chiller", Font.BOLD, 40);
	final Font fuenteTiempo = new Font("Chiller", Font.BOLD, 30);

	/** PANEL JUEGO **/
	PanelJuego panelJuego;
	String tiempo;

	/** FONDO **/
	private BufferedImage fondo;
	private Image fondoEscalado;

	/** IMAGEN DE FONDO **/
	// https://www.freepik.es/fotos-vectores-gratis/fondo

	public PantallaOver(PanelJuego panelJuego, String tiempo) {
		inicializarPantalla(panelJuego);
		this.tiempo = tiempo;
	}

	@Override
	public void inicializarPantalla(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
		
		try {
			fondo = ImageIO.read(new File("img/game_over.jpg"));
		} catch (Exception e) {
			System.out.println("PROBLEMAS AL CARGAR LAS IMAGENES. FIN DEL PROGRAMA");
		}
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
				BufferedImage.SCALE_SMOOTH);
	}

	@Override
	public void ejecutarFrame() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		colorCuadrado = colorCuadrado == Color.GREEN ? Color.WHITE : Color.GREEN;

	}

	@Override
	public void pulsarTecla(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			panelJuego.setPantalla(new PantallaJuego(panelJuego));
			break;
		case KeyEvent.VK_ESCAPE:
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
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
				BufferedImage.SCALE_SMOOTH);

	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
		pintarInformacion(g);
	}

	public void pintarInformacion(Graphics g) {
		g.setColor(colorCuadrado);
		g.setFont(fuentePuntuacion);
		g.drawRect(panelJuego.getWidth() / 2 - 160, panelJuego.getHeight() - 250, 350, 230);
		g.setColor(colorLetra);
		g.drawString("SCORE: ", panelJuego.getWidth() / 2 - 150, panelJuego.getHeight() - 200);
		g.setFont(fuenteTiempo);
		g.drawString("TIME: "+tiempo, panelJuego.getWidth() / 2 - 150, panelJuego.getHeight() - 150);
		g.setFont(fuenteFinal);
		g.drawString("Pulsa 'ENTER' para volver a jugar.", panelJuego.getWidth() / 2 - 150, panelJuego.getHeight() - 100);
		g.drawString("Pulsa 'ESC' para salir.", panelJuego.getWidth() / 2 - 150, panelJuego.getHeight() - 50);

	}

}
