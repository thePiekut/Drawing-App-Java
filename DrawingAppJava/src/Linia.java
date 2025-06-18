import java.awt.*;
import java.awt.geom.Path2D;

public class Linia extends Ksztalt {
    /*private  Path2D path;*/

    Linia(int x, int y, Color color) {
        super(x, y, color);

    }

    @Override
    void rysuj(Graphics g) {
        g.setColor(color);
        g.drawLine(x,y,x,y); //rysujemy linie punktowo


    }

    @Override
    boolean zawiera(int px, int py) {
        return false; //false bo nie robimy usuwania lini

    }

    @Override
    String zRysunkuNaPlik() { //funckja pomocnicza do zapisu do plikiu
        return "PEN " + x + " " + y + " " + color.getRed() + "," + color.getGreen() + "," + color.getBlue();
    }
}
