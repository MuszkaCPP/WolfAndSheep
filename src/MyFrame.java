import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**Klasa okienka aplikacji*/
class MyFrame extends JFrame {
    /** Szerokosc pojedynczego pola*/
    public int _x;
    /** Wysokosc pojedynczego pola*/
    public int _y;
    /** Zmienna przechowujaca pozostala ilosc krolikow na planszy*/
    public int rabbitCounter = 0;
    /** Zmienna przechowujaca ilosc pol horyzontalnych*/
    public int xPanelAmount;
    /** Zmienna przechowujaca ilosc pol wertykalnych*/
    public int yPanelAmount;
    /** Zmienna przechowujaca wylosowana wartosc*/
    public Random randomVariable;
    /** Zmienna przechowujaca wartosc opoznienia*/
    public int _k;
    /** Zmienna przechowujaca wybrane ID dla poczatkowej pozycji wilka*/
    public int pickedIdForWolf;
    /** Lista przechowujaca pola planszy*/
    public List<MyPanel> panelList = new ArrayList<>();
    /** Lista przechowujaca kroliki*/
    public List<MyRabbit> rabbitsList = new ArrayList<>();
    /** Lista przechowujaca ID wybrane dla poszczegolnych krolikow*/
    public List<Integer> pickedIdsForRabbits = new ArrayList<>();
    public MyPanel _panel;
    /** Instancja klasy Wilka*/
    public MyWolf _wolf;
    /** Instancja klasy Mover*/
    public Mover mover;

    /**Zmienna [X] wspomagajaca tworzenie srodkowych punktow w danym polu*/
    private int current_xMid;
    /**Zmienna [Y] wspomagajaca tworzenie srodkowych punktow w danym polu*/
    private int current_yMid;
    /** Tworzenie wilka na podstawie wybranego ID*/
    public void pickFieldIdForWolf(int pickedId) {
        panelList.get(pickedId).setBackground(Color.BLACK);
        panelList.get(pickedId).set_SthOnIt(true);
        _wolf = new MyWolf(pickedId, this);
        this.add(panelList.get(pickedId));
    }
    /** Funkcja tworzaca srodkowe punkty kazdego pola*/
    private void setMiddlePoints(int height, int width, int x, int y) {

        this._x = (int) Math.floor(width / x);
        this._y = (int) Math.floor(height / y);

        this.current_yMid = (5 + _y + 3) / 2;

        if (panelList != null) {
            panelList.clear();
        }

        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                if (i == 0) {
                    this.current_xMid = (5 + _x + 3) / 2;
                }
                /** Warunek na ustawienie pierwszego elementu*/
                if (j == 0 && i == 0) {
                    this.current_xMid = (5 + _x + 3) / 2;
                    _panel = new MyPanel(current_xMid, current_yMid, _x, _y);
                    panelList.add(_panel);
                } else {
                    _panel = new MyPanel(current_xMid, current_yMid, _x, _y);
                    panelList.add(_panel);
                    this.current_xMid += _x;
                }
            }
            this.current_xMid = (5 + _x + 3) / 2;
            this.current_yMid += _y;
        }
        this.current_yMid = (5 + _y + 3) / 2;
    }
    /**Konstruktor klasy glownego okienka aplikcaji*/
    MyFrame(int rabbitAmount, int x, int y, int k) {

        setTitle("Simulation");
        this.xPanelAmount = x;
        this.yPanelAmount = y;
        this.rabbitCounter = rabbitAmount;
        this._k = k;
        randomVariable = new Random();

        setSize(new Dimension(1920, 1080));
        setLayout(new GridLayout(x, y, 3, 3));
        setMiddlePoints(getHeight(), getWidth(), x, y);

        pickedIdForWolf = randomVariable.nextInt(panelList.size());

        int pickedIdForRabbit;

        /**Petla wybierajaca losowe punkty poczatkowe dla wilka i krolikow*/

        for (int l = 0; l < panelList.size(); l++) {

            pickedIdForRabbit = randomVariable.nextInt(panelList.size());

            if (l == pickedIdForWolf || pickedIdForRabbit == pickedIdForWolf) {
            } else {
                if (rabbitsList.size() == rabbitAmount) {
                    break;
                } else {
                    pickedIdsForRabbits.add(pickedIdForRabbit);
                    MyRabbit _rabbit = new MyRabbit(pickedIdForRabbit, this);
                    _rabbit.setMover(mover);
                    rabbitsList.add(_rabbit);
                }
            }
        }

        boolean thisIdIsWrong = false;
        for (int p = 0; p < panelList.size(); p++) {
            if (p == pickedIdForWolf) {
                pickFieldIdForWolf(pickedIdForWolf);
            } else {

                for (int j = 0; j < rabbitCounter; j++) {
                    if (p == pickedIdsForRabbits.get(j)) {
                        thisIdIsWrong = true;
                    }
                }
                if (thisIdIsWrong) {
                    panelList.get(p).setBackground(Color.GRAY);
                    panelList.get(p).set_SthOnIt(true);
                    this.add(panelList.get(p));
                    thisIdIsWrong = false;
                } else {
                    panelList.get(p).setBackground(Color.GREEN);
                    panelList.get(p).set_SthOnIt(false);
                    this.add(panelList.get(p));
                }
            }
        }

        setVisible(true);
        this.mover = new Mover(_wolf,rabbitsList, this);
        _wolf.setMover(mover);

        for(int i = 0; i < rabbitsList.size(); i++){ rabbitsList.get(i).setMover(mover); }
        for (MyRabbit rab :rabbitsList) { rab.start(); }
        _wolf.start();
    }
}
