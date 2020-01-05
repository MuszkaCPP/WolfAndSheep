import javax.swing.*;
import java.awt.*;

/**Klasa pojedynczego pola znajdujacego sie na planszy*/
class MyPanel extends JPanel {

    /**Zmienna przechowujaca punkt srodkowy [X] pojedynczego pola*/
    public int _midX;
    /**Zmienna przechowujaca punkt srodkowy [Y] pojedynczego pola*/
    public int _midY;
    /**Zmienna przechowujaca stan, czy cos stoi na tym polu*/
    private boolean isSthOnIt = false;

    public boolean get_isSthOnIt(){ return this.isSthOnIt; }
    public int get_midX(){ return this._midX; }
    public int get_midY() { return this._midY; }

    public void set_SthOnIt(boolean b){ this.isSthOnIt = b; }

    /**Konstruktor pojedynczego pola*/
    public MyPanel(int current_xMid, int current_yMid, int width, int height){
        this._midX = current_xMid;
        this._midY = current_yMid;
        setSize(new Dimension(width,height));
    }
}
