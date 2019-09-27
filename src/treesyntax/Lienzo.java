package treesyntax;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Lienzo extends Canvas {

    private Arbol objArbol;
    public static final int DIAMETRO = 30;
    public static final int RADIO = DIAMETRO / 2;
    public static final int ANCHO = 50;

    public void setObjArbol(Arbol objArbol) {
        this.objArbol = objArbol;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        Interfaz faz = new Interfaz();
       
        pintar(g, faz.getWidth()/2, 20, objArbol.getRaiz());
    }

    private void pintar(Graphics g, int x, int y, Nodo n) {
        if (n == null) {
        } else {
            int EXTRA = n.NodosCompletos(n) * (ANCHO / 2);
            g.setColor(Color.black);
            g.drawOval(x, y, DIAMETRO, DIAMETRO);
            g.fillOval(x, y, DIAMETRO, DIAMETRO);
            g.setColor(Color.WHITE);
            g.drawString(n.valor + "", x + 12, y + 18);
            g.setColor(Color.BLACK);
            if (n.getHijo_izq() != null) {
                g.drawLine(x + RADIO, y + RADIO, x - ANCHO - EXTRA + RADIO, y + ANCHO + RADIO);
            }
            if (n.getHijo_der() != null) {
                g.drawLine(x + RADIO, y + RADIO, x + ANCHO + EXTRA + RADIO, y + ANCHO + RADIO);
            }
            pintar(g, x - ANCHO - EXTRA, y + ANCHO, n.getHijo_izq());
            pintar(g, x + ANCHO + EXTRA, y + ANCHO, n.getHijo_der());
        }
    }

}
