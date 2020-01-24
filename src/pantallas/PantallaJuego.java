package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import base.Cronometro;
import base.PanelJuego;
import base.Sprite;

/**
 * 
 * @author Tomas Macias Castela
 *
 */
public class PantallaJuego implements Pantalla {

	/** PANEL JUEGO **/
	PanelJuego panelJuego;

	/** DIRECCION DE IMAGEN FONDO, BOLA Y BARRA **/
	// https://www.freepik.es/fotos-vectores-gratis/fondo
	// https://www.pngocean.com/gratis-png-clipart-wnltg
	// https://i.ytimg.com/vi/dddeFS44f-E/maxresdefault.jpg

	/** FUENTE **/
	final Font fuenteLvl = new Font("Chiller", Font.ITALIC, 40);
	final Font fuentePause = new Font("Chiller", Font.ITALIC, 100);
	final Color colorLvl = Color.WHITE;
	final Color colorLad[] = { Color.GRAY, Color.YELLOW, Color.WHITE, Color.RED, Color.PINK };
	final Font fuenteVolver = new Font("", Font.BOLD, 20);

	final static int TOTAL_LADRILLO = 70;
	final static int ANCHO_LADRILLO = 60;
	final static int ALTO_LADRILLO = 25;
	final static int VEL_BOLA = 8;
	final static int VEL_BARRA = 15;

	/** IMAGENES **/
	private BufferedImage fondo;
	private Image fondoEscalado, imagen_lateral, imagen_arriba, imagen_bola, imagen_barra;

	/** SPRITE **/
	Sprite barra_jugador;
	Sprite[] margen = new Sprite[3];
	Sprite bola;
	ArrayList<Sprite> ladrillos = new ArrayList<Sprite>();
	Sprite ladrilloAux;

	/** CONTROLAR MOVIMIENTO Y PRESION DE TECLAS **/
	boolean movimientoTecla;
	boolean jugando = false, pause = false;

	/** OBTENER VELOCIDAD DE LA BOLA **/
	int velX, velY;

	/** VARIABLES PARA TIEMPO **/
	final Font fuenteTiempo = new Font("Chiller", Font.BOLD, 20);
	double tiempoAcumulado;
	double tiempoTotal;
	DecimalFormat df = new DecimalFormat("#.##");
	String tiempo;
	Cronometro c;

	/** VARIABLE PARA SCORE **/
	int score = 0;

	public PantallaJuego(PanelJuego panelJuego) {
		inicializarPantalla(panelJuego);
	}

	@Override
	public void inicializarPantalla(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
		try {
			fondo = ImageIO.read(new File("img/fondoJuego.jpg"));
			imagen_barra = ImageIO.read(new File("img/barra.png"));
			imagen_lateral = ImageIO.read(new File("img/margen_lateral.png"));
			imagen_arriba = ImageIO.read(new File("img/margen_arriba.png"));
			imagen_bola = ImageIO.read(new File("img/bola.png"));
		} catch (Exception e) {
			System.out.println("ERROR AL CARGAR LAS IMAGENES.");
		}
		crearSprite();
		redimensionarPantalla();
	}

	@Override
	public void ejecutarFrame() {

		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			// TODO: handle exception
		}

		moverSprite(movimientoTecla, jugando);

		if (jugando) {
			if (!pause) { // Mientras el juego no estÃ© pausado. Seguimos sumando el tiempo.
				tiempoTotal = tiempoAcumulado + c.getTiempoTranscurrido();
				comprobarVictoria();
				comprobarColision();
			}
		}

	}

	@Override
	public void redimensionarPantalla() {
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
				BufferedImage.SCALE_SMOOTH);
	}

	@Override
	public void pulsarTecla(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			movIzquierda();
			break;
		case KeyEvent.VK_LEFT:
			movIzquierda();
			break;
		case KeyEvent.VK_D:
			movDerecha();
			break;
		case KeyEvent.VK_RIGHT:
			movDerecha();
			break;
		case KeyEvent.VK_SPACE:
			iniciarJuego();
			break;
		case KeyEvent.VK_P:
			if (jugando) {
				pauseGame();
			}
			break;
		case KeyEvent.VK_ESCAPE:
			if (pause) {
				panelJuego.setPantalla(new PantallaInicial(panelJuego));
			}
			break;

		}
	}

	@Override
	public void soltarTecla(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			movimientoTecla = false;
			break;
		case KeyEvent.VK_D:
			movimientoTecla = false;
			break;
		case KeyEvent.VK_LEFT:
			movimientoTecla = false;
			break;
		case KeyEvent.VK_RIGHT:
			movimientoTecla = false;
			break;
		default:
			break;
		}
	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
		barra_jugador.pintarEnMundo(g);
		pintarEnMundo(g);
		pintarInformacion(g);
	}

	/**
	 * Metodo para inicialar el juego.
	 * 
	 * Se ejecuta cuando pulsamos 'espacio'
	 * 
	 * Asignamos velocidad (x,y) negativa a la bola para que salga hacia arriba.
	 * 
	 * Iniciamos el hilo del cronometro.
	 */
	private void iniciarJuego() {
		if (!jugando) {

			if (new Random().nextInt(2) == 0) {
				bola.setVelX(new Random().nextInt(9));
			} else {
				bola.setVelX(new Random().nextInt(9) - 8);
			}

			bola.setVelY(-VEL_BOLA);
			jugando = true;
			c = new Cronometro();
			c.comenzar(0);
		}
	}

	/**
	 * Metodo para pausar el juego si pulsamos la tecla P.
	 * 
	 * Paramos la bola y la barra. Si volvemos a pulsar la P le damos velocidad a la
	 * bola y a la barra.
	 * 
	 * Cuando pausamos. Sumamos a la variable de tiempoAcumulado el tiempo que ha
	 * transcurrido el hilo. Le pasamos por parametro el tiempoAcumulado.
	 * 
	 * Cuando iniciamos de nuevo el juego, le pasamos por parametro el
	 * tiempoAcumulado.
	 * 
	 */
	private void pauseGame() {
		if (!pause) {
			velX = bola.getVelX();
			velY = bola.getVelY();
			barra_jugador.setVelX(0);
			bola.setVelX(0);
			bola.setVelY(0);
			tiempoAcumulado += c.getTiempoTranscurrido();
			c.parar(tiempoAcumulado);
			pause = true;
		} else {
			barra_jugador.setVelX(-VEL_BARRA);
			bola.setVelX(velX);
			bola.setVelY(velY);
			c.parar(tiempoAcumulado);
			pause = false;
		}
	}

	/**
	 * Metodo que realizamos si pulsamos la 'A' o 'â†�' -> mover la barra a la
	 * izquierda.
	 * 
	 * Si el juego no estÃ¡ en pausa, movemos la barra.
	 * 
	 * Si no hemos iniciado el juego, la pelota se mueve con la barra.
	 */
	private void movIzquierda() {
		if (!pause) {
			barra_jugador.setVelX(-VEL_BARRA);
			if (!jugando) {
				bola.setVelX(-VEL_BARRA);
			}
		}
		movimientoTecla = true;
	}

	/**
	 * Metodo que realizamos si pulsamos la 'D' o 'â†’' -> mover la barra a la
	 * derecha.
	 * 
	 * Si el juego no estÃ¡ en pausa, movemos la barra.
	 * 
	 * Si no hemos iniciado el juego, la pelota se mueve con la barra.
	 */
	private void movDerecha() {
		if (!pause) {
			barra_jugador.setVelX(VEL_BARRA);
			if (!jugando) {
				bola.setVelX(VEL_BARRA);
			}
		}
		movimientoTecla = true;
	}

	/**
	 * Metodo para controlar si colisiona la bola con la barra del jugador.
	 */

	public void comprobarColision() {
		// Comprobacion de colision bola-ladrillo.
		for (int i = 0; i < ladrillos.size(); i++) {
			if (bola.colisionBarraLadrillo(ladrillos.get(i))) {
				ladrillos.remove(i);
				score += 10;
			}
		}

		// Comprobacion de colision bola-barra.
		if (!(bola.getPosY() > barra_jugador.getPosY()) && bola.colisiona(barra_jugador)) {
			bola.setVelX(new Random().nextInt(17) - 8);
			bola.setVelY(-bola.getVelY());
		}

		// Comprobacion si no damos a la bola.
		if (bola.getPosY() > panelJuego.getHeight()) {
			tiempo = df.format(tiempoTotal);
			panelJuego.setPantalla(new PantallaOver(panelJuego, tiempo));
		}
	}

	/**
	 * Metodo para comprobar si destruimos todos los ladrillos entonces ganamos la
	 * partida.
	 */
	public void comprobarVictoria() {
		if (ladrillos.size() == 0) {
			panelJuego.setPantalla(new PantallaVictoria(panelJuego, score, tiempo));
		}
	}

	/**
	 * Metodo para pintar el lvl por pantalla.
	 * 
	 * @param g
	 */

	public void pintarInformacion(Graphics g) {
		// LVL - Score.
		g.setColor(colorLvl);
		g.setFont(fuenteLvl);
		g.drawString("SCORE: " + score, 25, 50);
		g.drawString("LVL: ", panelJuego.getWidth() - 110, 50);

		// Tiempo.
		g.setFont(fuenteTiempo);
		if (!jugando) {
			g.drawString("TIME: ", 25, 80);
		} else {
			g.drawString("TIME: " + df.format(tiempoTotal), 25, 80);
		}

		// Pause - Menu.
		g.setFont(fuentePause);
		if (pause) {
			g.drawString("PAUSE!", panelJuego.getWidth() / 2 - 140, panelJuego.getHeight() / 2);
			g.setFont(fuenteLvl);
			g.setColor(Color.YELLOW);
			g.drawString("ESC VOLVER AL MENU", panelJuego.getWidth() / 2 - 160, panelJuego.getHeight() / 2 + 60);
		} else {
			g.drawString("", 0, 0);
		}
	}

	/**
	 * Metodo para pintar los margenes y la bola por pantalla.
	 * 
	 * @param g
	 */
	public void pintarEnMundo(Graphics g) {
		for (int i = 0; i < margen.length; i++) {
			margen[i].pintarEnMundo(g);
		}
		for (int i = 0; i < ladrillos.size(); i++) {
			ladrillos.get(i).pintarEnMundo(g);
		}
		bola.pintarEnMundo(g);
	}

	/**
	 * Metodo para realizar el movimiento de la barra.
	 * 
	 * @param movimiento True realiza el movimiento.
	 */
	public void moverSprite(boolean movimiento, boolean pressEspacio) {
		if (movimiento && !pressEspacio) {
			barra_jugador.actualizarPosicion(panelJuego);
			bola.actualizarPosicion(panelJuego);
		}
		if (movimiento && pressEspacio) {
			barra_jugador.actualizarPosicion(panelJuego);
		}
		if (pressEspacio) {
			bola.actualizarPosicion(panelJuego);
		}
	}

	/**
	 * Metodo para crear los sprites.
	 * 
	 * Barra del jugador, margen lateral, margen arriba.
	 */
	public void crearSprite() {
		// Barra.
		barra_jugador = new Sprite(panelJuego.getWidth() / 2 - 35, panelJuego.getHeight() - 90, 100, 20, 0, 0,
				imagen_barra, true);

		// Reescalado de imagen.
		imagen_arriba = imagen_arriba.getScaledInstance(panelJuego.getWidth(), 16, Image.SCALE_SMOOTH);
		imagen_lateral = imagen_lateral.getScaledInstance(76, panelJuego.getHeight() - 85, Image.SCALE_SMOOTH);

		// Sprite margen lateral/arriba.
		margen[0] = new Sprite(2, 90, 15, panelJuego.getHeight(), 0, 0, imagen_lateral, false);
		margen[1] = new Sprite(panelJuego.getWidth() - 14, 90, 15, panelJuego.getHeight(), 0, 0, imagen_lateral, false);
		margen[2] = new Sprite(10, 90, panelJuego.getWidth(), 15, 0, 0, imagen_arriba, false);

		// Sprite bola.
		bola = new Sprite(panelJuego.getWidth() / 2 + 10, panelJuego.getHeight() - 105, 15, 15, 0, 0, imagen_bola,
				true);

		// Sprite cuadrado.
		int iniX = 150, iniY = 150; // Posicion de inicio x e y.
		int cColor = 0; // Indicador del color.
		for (int i = 1; i < TOTAL_LADRILLO + 1; i++) {
			ladrilloAux = new Sprite(iniX, iniY, ANCHO_LADRILLO, ALTO_LADRILLO, 0, 0, colorLad[cColor]);
			ladrillos.add(ladrilloAux);
			iniX += ANCHO_LADRILLO + 5;

			if (i % 14 == 0) {
				iniY += 50;
				iniX = 150;
				cColor++;
			}

		}
	}

}
