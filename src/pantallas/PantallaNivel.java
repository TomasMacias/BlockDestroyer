package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import base.PanelJuego;

public class PantallaNivel implements Pantalla {
	PanelJuego panelJuego;
	int lvl;
	int score;

	public PantallaNivel(PanelJuego panelJuego, int lvl, int score) {
		this.lvl = lvl;
		this.score = score;
		inicializarPantalla(panelJuego);
	}

	@Override
	public void inicializarPantalla(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;

	}

	@Override
	public void ejecutarFrame() {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		panelJuego.setPantalla(new PantallaJuego(panelJuego, lvl + 1, score));

	}

	@Override
	public void redimensionarPantalla() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulsarTecla(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void soltarTecla(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, panelJuego.getWidth(), panelJuego.getHeight());
		g.setFont(new Font("Chiller", Font.ITALIC, 80));
		g.setColor(Color.WHITE);
		g.drawString("Pasando al siguiente nivel ", panelJuego.getWidth() / 2 - 300, panelJuego.getHeight() / 2 - 10);

	}

}
