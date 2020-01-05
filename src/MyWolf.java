import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**Klasa wilka*/
class MyWolf extends Thread{
    /**Zmienna przechowujaca aktualny numer pola wilka*/
    private int current_FieldId;
    /** Zmienna przechowujaca instacje klasy glownego okna aplikacji*/
    private MyFrame _frame;
    /** Zmienna przechowujaca koordynaty najblizszego krolika*/
    private Point _targetCoordinates;
    /** Zmienna przechowujaca instancje klasy poruszajacej*/
    private Mover _mover;
    /**Zmienna przechowujaca numer pola potencjalnie najblizszego krolika*/
    public int _targetID = 0;
    /**Zmienna przechowujaca numer pola najblizszego krolika*/
    public int _targetArrayIndex;
    /** Zmienna przechowujaca numer wylosowany numer z mozliwej puli najblizszych krolikow*/
    public int pickedFieldID;
    /**Lista potencjalnie najblizszych numerow pol, na ktorych znajduja sie kroliki*/
    public List<Integer> potentialFieldId = new ArrayList<>();
    /**Konstruktor klasy Wilka*/
    public MyWolf(int start_FieldId, MyFrame frame){ this.current_FieldId = start_FieldId; this._frame = frame; }

    public int getCurrent_FieldId(){ return this.current_FieldId; }
    public Point get_targetCoordinates(){ return this._targetCoordinates; }

    public void set_targetCoordinates(Point targetCoordinates){ this._targetCoordinates = targetCoordinates; }
    public void setCurrent_FieldId(int _FieldId){ this.current_FieldId = _FieldId; }
    public void setMover(Mover mover){ this._mover = mover; }
    /**Funkcja znajdujaca najblizszego krolika*/
    public void lookForNearestRabbit(){
        int smallestDistanceToRabbit = 10000;
        int distanceToRabbit;

        potentialFieldId.clear();

        for(int i = 0; i < _frame.rabbitsList.size(); i++){

            distanceToRabbit = (int) Point2D.distance(_frame.panelList.get( _frame.rabbitsList.get(i).get_currentFieldID()).get_midX(),_frame.panelList.get( _frame.rabbitsList.get(i).get_currentFieldID()).get_midY(), _frame.panelList.get(this.current_FieldId).get_midX(), _frame.panelList.get(this.current_FieldId).get_midY());

            if(distanceToRabbit < smallestDistanceToRabbit && _frame.rabbitsList.get(i).isAlive == true){

                smallestDistanceToRabbit = distanceToRabbit;
                potentialFieldId.clear();
                _targetID = _frame.rabbitsList.get(i).get_currentFieldID();
                potentialFieldId.add(_targetID);

            }
            else if(distanceToRabbit == smallestDistanceToRabbit &&_frame.rabbitsList.get(i).isAlive == true){
                _targetID = _frame.rabbitsList.get(i).get_currentFieldID();
                potentialFieldId.add(_targetID);
            }
        }

        pickedFieldID = _frame.randomVariable.nextInt(potentialFieldId.size());

        for(int i = 0; i < _frame.rabbitsList.size(); i++){
            if(_frame.rabbitsList.get(i).get_currentFieldID() ==  potentialFieldId.get(pickedFieldID) && _frame.rabbitsList.get(i).isAlive == true){
                _targetArrayIndex = i;
            }
        }
        this.set_targetCoordinates(new Point(_frame.panelList.get(potentialFieldId.get(pickedFieldID)).get_midX(),_frame.panelList.get(potentialFieldId.get(pickedFieldID)).get_midY()));
    }

    @Override
    public void run() {
        try {
            int randomTime = _frame.randomVariable.nextInt((int)(_frame._k * 1.5));
            while(randomTime < 0.5 * _frame._k){
                randomTime = _frame.randomVariable.nextInt((int)(_frame._k * 1.5));
            }

            while(_frame.rabbitCounter > 0){
                Thread.sleep(randomTime);
                lookForNearestRabbit();
                _mover.goToNearestRabbit();
                try{
                    if(current_FieldId == _frame.rabbitsList.get(_targetArrayIndex).get_currentFieldID()){
                        _frame.rabbitCounter --;
                        _frame.rabbitsList.get(_targetArrayIndex).isAlive = false;
                        _frame.panelList.get(_targetArrayIndex).setBackground(Color.GREEN);
                        potentialFieldId.clear();
                        Thread.sleep(randomTime * 5);
                    }
                }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            System.out.println("KONIEC");
        }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}
