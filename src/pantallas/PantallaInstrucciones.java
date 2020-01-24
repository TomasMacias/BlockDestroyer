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

	/** RUTA IMAGEN GIF **/
	// https://www.programoergosum.com/images/cursos/111-juego-de-arkanoid-programado-con-scratch/proyecto-pablorubma.gif

	public PantallaInstrucciones(PanelJuego panel) {
		inicializarPantalla(panel);
	}

	@Override
	public void inicializarPantalla(PanelJuego panel) {
		this.panelJuego = panel;
		try {
			fondo = ImageIO.read(new File("img/fondo.jpg"));
			gifImage = new ImageIcon("img/imagenJuego.gif").getImage();
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
		g.drawString("A | ←: → Movimiento para la izquierda.", 30, 70);
		g.drawString("D | →: → Movimiento para la derecha.", 30, 110);
		g.drawString("|________| → Iniciar juego.", 30, 150);
		g.drawString("P → Pausar/Reanudar juego", 30, 190);
		g.drawImage(gifImage, 60, 250, null);

	}

}
