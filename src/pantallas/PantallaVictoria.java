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
public class PantallaVictoria implements Pantalla {

	/** PANEL JUEGO **/
	PanelJuego panelJuego;

	/** COLOR Y FUENTES. **/
	Color colorLetra = Color.PINK;
	Color colorCuadradoUno = Color.GREEN;
	Color colorCuadradoDos = Color.WHITE;
	final Font fuenteVictoria = new Font("Chiller", Font.BOLD, 20);
	final Font fuentePuntuacion = new Font("Chiller", Font.BOLD, 40);
	final Font fuenteTiempo = new Font("Chiller", Font.BOLD, 30);

	/** RUTA IMAGEN FONDO VICTORIA. **/
	// http://www.theorangeday.com/wp-content/uploads/2014/02/win-screen.png
	// https://media.tenor.com/images/ee9dd9991c6d0a62f3c68bc39c6b9096/tenor.gif

	/** FONDO **/
	private BufferedImage fondo;
	private Image fondoEscalado;

	/** FUENTES **/
	int score;

	public PantallaVictoria(PanelJuego panelJuego, int score) {
		this.score = score;
		inicializarPantalla(panelJuego);
	}

	@Override
	public void inicializarPantalla(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
		try {
			fondo = ImageIO.read(new File("img/win.png"));
		} catch (Exception e) {
			System.out.println("PROBLEMAS AL CARGAR LAS IMAGENENES. FIN DEL PROGRAMA");
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
		colorCuadradoUno = colorCuadradoUno == Color.GREEN ? Color.WHITE : Color.GREEN;
		colorCuadradoDos = colorCuadradoDos == Color.WHITE ? Color.GREEN : Color.WHITE;

	}

	@Override
	public void pulsarTecla(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 10:
			panelJuego.setPantalla(new PantallaJuego(panelJuego, 0,0));
			break;
		case 27:
			panelJuego.setPantalla(new PantallaInicial(panelJuego));
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
		g.setColor(colorCuadradoUno);
		g.setFont(fuentePuntuacion);

		g.drawRect(panelJuego.getWidth() / 2 - 160, panelJuego.getHeight() - 300, 350, 230);
		g.setColor(colorCuadradoDos);
		g.drawRect(panelJuego.getWidth() / 2 - 150, panelJuego.getHeight() - 290, 330, 210);
		g.setColor(colorLetra);
		g.drawString("SCORE: ", panelJuego.getWidth() / 2 - 140, panelJuego.getHeight() - 250);
		g.setFont(fuenteVictoria);
		g.drawString("Pulsa 'ENTER' para volver a jugar.", panelJuego.getWidth() / 2 - 140,
				panelJuego.getHeight() - 150);
		g.drawString("Pulsa 'ESC' para volver al menu.", panelJuego.getWidth() / 2 - 140, panelJuego.getHeight() - 100);
	}

}
