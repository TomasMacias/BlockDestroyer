package base;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;


/**
 * VentanaPrincipal sirve como contenedor para el panel de juego.
 * @author Tomas Macias Castela
 *
 */
public class VentanaPrincipal {

	//Sigo teniendo la ventana
	JFrame ventana;
	PanelJuego panelJuego;
	BufferedImage imgCursor;
	Cursor cursorTransparente;
	public VentanaPrincipal() {
		ventana = new JFrame("Tomas Macias");
		
		ventana.setSize(1200,950);
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Método que inicializa todos los componentes de la ventana
	 */
	public void inicializarComponentes(){	
		//Definimos el layout:
		ventana.setLayout(new GridLayout(1,1));
		
		//PANEL JUEGO
		panelJuego = new PanelJuego();
		imgCursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		cursorTransparente = Toolkit.getDefaultToolkit().createCustomCursor(imgCursor, new Point(0, 0), "transparente");
		ventana.getContentPane().setCursor(cursorTransparente);
		ventana.add(panelJuego);
	}
		
	/**
	 * Método que realiza todas las llamadas necesarias para inicializar la ventana correctamente.
	 */
	public void inicializar(){
		ventana.setVisible(true);
		ventana.setResizable(false);
		inicializarComponentes();	
	}
}
