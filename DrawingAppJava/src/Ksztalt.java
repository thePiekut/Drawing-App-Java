import java.awt.*;

public abstract class Ksztalt {
    public int x;
    public int y;
    Color color;

    Ksztalt(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    abstract void rysuj(Graphics g);

    abstract boolean zawiera(int px, int py);

    abstract String zRysunkuNaPlik();

    public static Color wczytajColor(String colorString) {
        String[] rgb = colorString.split(","); // funkcja do wydzielania koloru R,G,B
        return new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
    }

    public static Ksztalt zPlikuNaRysunek(String string) {
        String[] dane = string.split(" "); //tworzymy tablice ze stringa oddzielamy spacja
        String nazwa = dane[0];  //wydzielamy dane z tablicy do zmiennych
        int x = Integer.parseInt(dane[1]);
        int y = Integer.parseInt(dane[2]);
        Color color = wczytajColor(dane[3]);
        //tworzymy rysunki z danych
        if(nazwa.equals("CIRCLE")) {
            return new Kolo(x,y,color);
        }

        else if (nazwa.equals("SQUARE")) {
            return new Kwadrat(x,y,color);

        } else if (nazwa.equals("PEN")) {
            return new Linia(x,y,color);
        }


        return null;
    }


}
