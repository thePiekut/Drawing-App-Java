import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Obszar extends JPanel {
    Random random = new Random();
    private ArrayList<Ksztalt> rysunek = new ArrayList<>();
    private Tryb tryb = Tryb.PEN;
    private Color colorPen = Color.BLACK;
    boolean delete = false;
    private Point mousePosition;


    public Obszar() {
        setBackground(Color.WHITE);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) { //usuwamy figure gdy naciskamy D
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    delete = true;
                } else if (e.getKeyCode() == KeyEvent.VK_F1) { //rysujemy figury naciskając f1

                        //sprawdamy jaki to tryb,tworzymy figure z pozycją myszki
                    if (tryb == Tryb.CIRCLE) {
                        Ksztalt ksztalt = new Kolo((int) mousePosition.getX(), (int) mousePosition.getY(), randColor());
                        rysunek.add(ksztalt); //dodajmy do listy
                        repaint(); //wywołanie paint component
                        Main.stan.setText("Modified"); //zmieniamy stn w JToolBar

                    } else if (tryb == Tryb.SQUARE) {
                        Ksztalt ksztalt = new Kwadrat((int) mousePosition.getX(), (int) mousePosition.getY(), randColor());
                        rysunek.add(ksztalt);
                        repaint();
                        Main.stan.setText("Modified");
                    }


                }
            }

            @Override
            public void keyReleased(KeyEvent e) { //gdy nie naciskamy D nie usuwa
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    delete = false;

                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (delete) { //jeśli tryb usuwania usuwamy kształt z listy
                    Iterator<Ksztalt> iterator = rysunek.iterator();
                    while (iterator.hasNext()) {
                        Ksztalt ksztalt = iterator.next();
                        if (ksztalt.zawiera(e.getX(), e.getY())) { //jeśli myszka w na figurze
                            //panel potwierdający wybór
                            int wybor = JOptionPane.showConfirmDialog(null, "Czy usunąc zaznaczony obiekt?", "Usuniencie", JOptionPane.YES_NO_OPTION);
                            if (wybor == JOptionPane.YES_OPTION) {
                                iterator.remove();
                                repaint();
                                Main.stan.setText("Modyfied");
                                break;
                            }
                        }
                    }

                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) { //rysowanie lini przejeżdżając myszka
                if (tryb == Tryb.PEN) {
                    Ksztalt ksztalt = new Linia(e.getX(), e.getY(), colorPen);
                    rysunek.add(ksztalt);
                    repaint();
                    Main.stan.setText("Modified");

                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mousePosition = e.getPoint(); //pobieranie pozycji myszki do rysowania figur
            }


        });
        setFocusable(true); //ustawianie aby aplikacja wyłapywała przyciski
        requestFocusInWindow();
    }

    public Color randColor() { //funkcja losowych kolorów
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public void setTryb(Tryb tryb) {
        this.tryb = tryb;
    }

    @Override
    public void paintComponent(Graphics g) { //funckja do rysowania naszych figur
        super.paintComponents(g);
        for (Ksztalt ksztalt : rysunek) {
            ksztalt.rysuj(g);
        }
    }

    public void wyczysc() { //czyszczenie rysunku
        rysunek.clear();
        repaint();
    }

    public Color getColorPen() {
        return colorPen;
    }

    public void setColorPen(Color colorPen) {
        this.colorPen = colorPen;
    }

    public void zapiszRysunek(File file) throws IOException { //zapis do pliku
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Ksztalt ksztalt : rysunek) {
                writer.write(ksztalt.zRysunkuNaPlik()); //zapisujemy do pliku przy użyciu funkcji
                writer.newLine();// żeby zapisało sie w nowej lini
            }
        }
    }
    public void wczytajRysunek(File file) throws IOException { //odczyt z pliku
        rysunek.clear(); //czyścimy rysunek
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line; //każda linia jako string
            while ((line = reader.readLine()) != null) { //wczytuje aż nie bedzie co wczytwywać
                rysunek.add(Ksztalt.zPlikuNaRysunek(line)); //dodajemy do rysunku kształty tworzac je z pliku
            }
            repaint();

        }
    }


}
