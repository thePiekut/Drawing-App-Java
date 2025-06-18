import java.awt.*;

public class Kolo extends Ksztalt{

    private static final int prom=25;  //ustalona stała wartość promoienia
    Kolo(int x, int y, Color color) {
        super(x, y, color);

    }

    @Override
    void rysuj(Graphics g) {
        g.setColor(color);
        g.fillOval(x-prom,y-prom,2*prom,2*prom); //rysujemy żeby środek był w kwadracie
    }

    @Override
    boolean zawiera(int px, int py) {
        return (x-px)*(x-px)+(y-py)*(y-py)<=prom*prom; //sprawdzamy czy punkt jest w kole(zamiast pierwiastkować podnosimy do kwadratu peomień

    }

    @Override
    String zRysunkuNaPlik() { //funckja pomocnicza do zapsiu do pliku
        return "CIRCLE " + x + " " + y + " " + color.getRed() + "," + color.getGreen() + "," + color.getBlue();

    }
}
