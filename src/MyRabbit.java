import java.util.ArrayList;
import java.util.List;

/**Klasa krolika*/
class MyRabbit extends Thread{
    /**Zmienna przechowujaca numery potencjalnych pol do ucieczki*/
    public List<Integer> potentialFieldId = new ArrayList<>();
    /**Zmienna przechowujaca stan zycia krolika*/
    public boolean isAlive = true;
    /** Zmienna przechowujaca instancje klasy poruszajacej*/
    private Mover _mover;
    /**Zmienna przechowujaca aktualny numer pola krolika*/
    private int _currentFieldID;
    /** Zmienna przechowujaca instacje klasy glownego okna aplikacji*/
    private MyFrame _frame;
    /** Konstruktor klasy Krolika*/
    public MyRabbit(int start_FieldId, MyFrame frame){ this._currentFieldID = start_FieldId; this._frame = frame;}

    public int get_currentFieldID(){ return  this._currentFieldID; }

    public void setMover(Mover mover){ this._mover = mover; }
    public void set_currentFieldID(int ID){ this._currentFieldID = ID; }

    @Override
    public void run() {
        try {
            int randomTime = _frame.randomVariable.nextInt((int)(_frame._k * 1.5));
            while(randomTime < 0.5 * _frame._k){
                randomTime = _frame.randomVariable.nextInt((int)(_frame._k * 1.5));
            }
            while(isAlive == true){
                _mover.runAway(this);
                Thread.sleep(randomTime);
            }
        }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}
