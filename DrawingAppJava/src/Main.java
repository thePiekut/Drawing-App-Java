import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main implements ActionListener {
    JFrame jf;
    JToolBar jtb;
    JMenuItem open, save, saveAs, quit, kolor, clear;
    static Label stan;
        Label tryb;
    JRadioButtonMenuItem circle, square, pen;
    File usingFile;
    Obszar obszar;

    public static void main(String[] args) {


        new Main();

    }

    public Main() {
        SwingUtilities.invokeLater(this::createGUI);
    }

    protected void createGUI() {
         jf = new JFrame();
        jf.setTitle("Simple Draw");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(600, 600);
        jf.setLocationRelativeTo(null); //wyśrdokowanie okna


        JMenuBar menuBar = new JMenuBar(); //tworzymy menu dodajemy Itemy przypisujemy memoniki i akceleratory
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        JMenu draw = new JMenu("Draw");
        draw.setMnemonic(KeyEvent.VK_R);
        open = new JMenuItem("Open");
        open.setMnemonic(KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        save = new JMenuItem("Save");
        save.setMnemonic(KeyEvent.VK_S);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        saveAs = new JMenuItem("Save as");
        saveAs.setMnemonic(KeyEvent.VK_A);
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        quit = new JMenuItem("Quit");
        quit.setMnemonic(KeyEvent.VK_Q);
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        JMenu figure = new JMenu("Figure");
        figure.setMnemonic(KeyEvent.VK_I);
        circle = new JRadioButtonMenuItem("Circle");
        circle.setMnemonic(KeyEvent.VK_K);
        circle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));
        square = new JRadioButtonMenuItem("Square");
        square.setMnemonic(KeyEvent.VK_V);
        square.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        pen = new JRadioButtonMenuItem("Pen");
        pen.setMnemonic(KeyEvent.VK_P);
        pen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        kolor = new JMenuItem("Color");
        kolor.setMnemonic(KeyEvent.VK_C);
        kolor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        clear = new JMenuItem("Clear");
        clear.setMnemonic(KeyEvent.VK_W);
        clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));

        //dodajemy itemy do meny file
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.addSeparator();
        file.add(quit);
        menuBar.add(file);//dodajemy file do menubara

        ButtonGroup ksztalt = new ButtonGroup(); //tworzymy grupe radiobutonów
        ksztalt.add(circle);
        ksztalt.add(square);
        ksztalt.add(pen);

        figure.add(circle); //dodajemy je do podmenu figure
        figure.add(square);
        figure.add(pen);
        //dodajemy pozostałe itemy do podmenu
        draw.add(figure);
        draw.add(kolor);
        draw.addSeparator();
        draw.add(clear);
        menuBar.add(draw);

        jf.setJMenuBar(menuBar); //ustawiamy nasz MenuBar jako menu bar tego JFrame

        obszar = new Obszar(); //tworzymy obszar do ryswoania
        jf.add(obszar, BorderLayout.CENTER); //ustawiamy go na środek
        jtb =new JToolBar(); //tworzymy toolbar
        JPanel dol=new JPanel(new BorderLayout()); //tworzymy JPanel dla naszego toolbara
        stan=new Label("New");
        tryb=new Label("Pen");
        dol.add(tryb,BorderLayout.WEST);
        dol.add(stan,BorderLayout.EAST);
        jtb.add(dol); //dodajemy JPanel do ToolBara
        jf.add(jtb,BorderLayout.SOUTH); //dodajemy Toolbar do JFrame




        jf.setVisible(true); // ustawiamy żeby było widac nasze okienko

        //dodajemy acionlisinerów
        open.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);
        quit.addActionListener(this);
        circle.addActionListener(this);
        square.addActionListener(this);
        pen.addActionListener(this);
        kolor.addActionListener(this);
        clear.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == open) { //opcja open otwiera plik
            JFileChooser jfile = new JFileChooser();
            int wybor = jfile.showOpenDialog(null);
            if (wybor == JFileChooser.APPROVE_OPTION) {
                File file = jfile.getSelectedFile(); //plik wybrany zapisujemy do zmiennej
                try {
                    obszar.wczytajRysunek(file); //wczytujemy rysunek
                    stan.setText("Saved");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                usingFile = file; //plik który otwieramy ustalamy jako obecny
                jf.setTitle("Simple Draw: "+usingFile.getName());
            } }
        else if (source == save) { //zapisywanie pliku
                if (usingFile != null) { // jesli nie ma obecnego pliku to tworzymy nowy
                    try {
                        obszar.zapiszRysunek(usingFile);
                        stan.setText("Saved");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    saving();
                }

            } else if (source == saveAs) { //zapisujemy jako nowty plik
            saving();


        } else if (source == quit) { //wyjście z zapytaniem o zapis
            if(stan.getText().equals("Modified")){
                int wybor=JOptionPane.showConfirmDialog(null,"Czy wyjść bez zapisania?","UWAGA",JOptionPane.YES_NO_OPTION);
                if(wybor==JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
            else System.exit(0);

            } else if (source == circle) { //ustalamy tryb rysowania CIRCLE,SQUARE lub PEN
                obszar.setTryb(Tryb.CIRCLE);
                tryb.setText("Circle");
            } else if (source == square) {
                obszar.setTryb(Tryb.SQUARE);
            tryb.setText("Square");
            } else if (source == pen) {
                obszar.setTryb(Tryb.PEN);
            tryb.setText("Pen");
            } else if (source == kolor) { //wybór koloru dla PEN
                Color choseColor = JColorChooser.showDialog(null, "Kolor lini", obszar.getColorPen());
                if (choseColor != null) {
                    obszar.setColorPen(choseColor);
                }
            } else if (source == clear) { //Czyszczenie obszaru
                obszar.wyczysc();
                stan.setText("Modified");

            }


    }

    private void saving() { //funkcja do zapisywania
        JFileChooser jfile = new JFileChooser();
        int wybor = jfile.showSaveDialog(null);
        if (wybor == JFileChooser.APPROVE_OPTION) {
            File file = jfile.getSelectedFile();
            try {
                obszar.zapiszRysunek(file);
                usingFile = file;
                jf.setTitle("Simple Draw: "+usingFile.getName());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            stan.setText("Saved");
        }
    }

}
