import java.awt.*;

public class Kwadrat extends Ksztalt{
    private final int bok=25; //bok kwadratu ustalony jako stała
    Kwadrat(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    void rysuj(Graphics g) {
        g.setColor(color);
        g.fillRect(x-bok/2,y-bok/2,bok,bok); //rysujemy tak żeby środek był tam gdzie myszka

    }

    @Override
    boolean zawiera(int px, int py) {
        return px>=x-bok/2 &&px<=x+bok/2&&py>=y-bok/2 &&py<=y+bok/2; //sprawdzamy czy klikamy w obrembie kwadratu

    }

    @Override
    String zRysunkuNaPlik() { //funkcja pomocnicza do zapisywania danych do pliku
         return "SQUARE " + x + " " + y + " " + color.getRed() + "," + color.getGreen() + "," + color.getBlue();

    }
}
