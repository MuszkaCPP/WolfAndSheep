import javax.swing.*;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.*;

/**Klasa glowna programu*/
public class Symulacja {
    /** Zmienna przechowujaca ilosc krolikow podana przez uzytkownika*/
    private int _rabbitAmount;
    /** Zmienna przechowujaca opoznienie podane przez uzytkownika*/
    private int _movementLag;
    /** Zmienna przechowujaca ilosc pol horyzontalnych podana przez uzytkownika*/
    private int _sizeX;
    /** Zmienna przechowujaca ilosc pol wertykalnych podana przez uzytkownika*/
    private int _sizeY;

    /**Funkcja glowna klasy glownej programu*/
    public static void main(String[] args) throws Exception {
        Symulacja sym = new Symulacja();
        sym.takeProgramParameters();
    }

    /**Funkcje pobierajaca dane potrzebne do uruchomienia programu*/
    public void takeProgramParameters() throws Exception{
        try{
            this._sizeX = Integer.parseInt(JOptionPane.showInputDialog(null,"Podaj szerokosc planszy"));

            try{
                this._sizeY = Integer.parseInt(JOptionPane.showInputDialog(null,"Podaj wysokosc planszy"));

                try{
                    this._rabbitAmount = Integer.parseInt(JOptionPane.showInputDialog(null,"Podaj ilosc krolikow"));
                    if(_rabbitAmount >= _sizeX * _sizeY){
                        JOptionPane.showMessageDialog(null, "Podano zla liczbe krolikow!");
                    }
                    else{
                        try{
                            this._movementLag = Integer.parseInt(JOptionPane.showInputDialog(null,"Podaj opoznienie"));
                            MyFrame frame = new MyFrame(_rabbitAmount, _sizeX,_sizeY,_movementLag);
                        }
                        catch(Exception ex){
                            JOptionPane.showMessageDialog(null, "Podano zly parametr!");
                        }
                    }
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Podano zly parametr!");
                }
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Podano zly parametr!");
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Podano zly parametr!");
        }
    }
}

