import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**Klasa przesuwajaca zwierzeta po planszy*/
class Mover{
    /** Zmienna przechowujaca instancje Wilka*/
    private MyWolf _wolf;
    /** Zmienna przechowujaca instacje klasy glownego okna aplikacji*/
    private MyFrame _frame;
    /** Lista przechowujaca kroliki*/
    private List<MyRabbit> _rabbitsList;
    /** Lista przechowujaca potencjalne numery pol dla krolika*/
    List <Integer> potentialID = new ArrayList<>();
    /** Lista przechowujaca mozliwe numery pol dla krolika*/
    List <Integer> potentialID_distance = new ArrayList<>();
    /**Konstruktor klasy przesuwajacej*/
    public Mover(MyWolf wolf, List<MyRabbit> rabbitsList, MyFrame frame){
        this._wolf = wolf;
        this._rabbitsList = rabbitsList;
        this._frame = frame;
    }
    /**Funkcja przesuwajaca Wilka do najblizszego krolika*/
    public synchronized void goToNearestRabbit(){
        _frame.panelList.get(_wolf.getCurrent_FieldId()).setBackground(Color.GREEN);
        _frame.panelList.get(_wolf.getCurrent_FieldId()).set_SthOnIt(false);

        if(_wolf.get_targetCoordinates().x - _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midX() < 0 && _wolf.get_targetCoordinates().y - _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midY() < 0){
            _wolf.setCurrent_FieldId(_wolf.getCurrent_FieldId() - _frame.xPanelAmount - 1);
        }
        else if(_wolf.get_targetCoordinates().x == _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midX() && _wolf.get_targetCoordinates().y - _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midY() < 0){
            _wolf.setCurrent_FieldId(_wolf.getCurrent_FieldId() - _frame.xPanelAmount);
        }
        else if(_wolf.get_targetCoordinates().x - _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midX() > 0 && _wolf.get_targetCoordinates().y - _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midY() < 0){
            _wolf.setCurrent_FieldId(_wolf.getCurrent_FieldId() - _frame.xPanelAmount + 1);
        }
        else if(_wolf.get_targetCoordinates().x - _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midX() > 0 && _wolf.get_targetCoordinates().y == _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midY()){
            _wolf.setCurrent_FieldId(_wolf.getCurrent_FieldId() + 1);
        }
        else if(_wolf.get_targetCoordinates().x - _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midX() > 0 && _wolf.get_targetCoordinates().y - _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midY() > 0){
            _wolf.setCurrent_FieldId(_wolf.getCurrent_FieldId() + _frame.xPanelAmount + 1);
        }
        else if(_wolf.get_targetCoordinates().x == _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midX() && _wolf.get_targetCoordinates().y - _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midY() > 0){
            _wolf.setCurrent_FieldId(_wolf.getCurrent_FieldId() + _frame.xPanelAmount);
        }
        else if(_wolf.get_targetCoordinates().x - _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midX() < 0 && _wolf.get_targetCoordinates().y - _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midY() > 0){
            _wolf.setCurrent_FieldId(_wolf.getCurrent_FieldId() + _frame.xPanelAmount - 1);
        }
        else if(_wolf.get_targetCoordinates().x - _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midX() < 0 && _wolf.get_targetCoordinates().y == _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midY()){
            _wolf.setCurrent_FieldId(_wolf.getCurrent_FieldId() - 1);
        }

        _frame.panelList.get(_wolf.getCurrent_FieldId()).setBackground(Color.BLACK);
        _frame.panelList.get(_wolf.getCurrent_FieldId()).set_SthOnIt(true);
        _wolf.potentialFieldId.clear();
    }
    /** Funkcja przesuwajaca uciekajacego krolika*/
    public synchronized void runAway(MyRabbit rab){
        if(rab.isAlive == true){


            int maxDistanceToWolf = 0;
            int distanceToNextField;
            int pickedID = 0;

            /** Prawo*/
            if(rab.get_currentFieldID() + 1 >= 0 && rab.get_currentFieldID() + 1 < _frame.panelList.size() && _frame.panelList.get(rab.get_currentFieldID() + 1).get_isSthOnIt() == false){
                distanceToNextField = (int) Point2D.distance(_frame.panelList.get(rab.get_currentFieldID()).get_midX(),_frame.panelList.get(rab.get_currentFieldID()).get_midY(),_frame.panelList.get(rab.get_currentFieldID() + 1).get_midX(),_frame.panelList.get(rab.get_currentFieldID() + 1).get_midY());
                if(distanceToNextField > _frame._x * 2 && distanceToNextField > _frame._y * 2){
                }
                else
                    potentialID.add(rab.get_currentFieldID() + 1);
            }
            /** LEWO*/
            if(rab.get_currentFieldID() - 1 >= 0 && rab.get_currentFieldID() - 1 < _frame.panelList.size() && _frame.panelList.get(rab.get_currentFieldID() - 1).get_isSthOnIt() == false){
                distanceToNextField = (int)Point2D.distance(_frame.panelList.get(rab.get_currentFieldID()).get_midX(),_frame.panelList.get(rab.get_currentFieldID()).get_midY(),_frame.panelList.get(rab.get_currentFieldID() - 1).get_midX(),_frame.panelList.get(rab.get_currentFieldID() - 1).get_midY());
                if(distanceToNextField > _frame._x * 2 && distanceToNextField > _frame._y * 2){
                }
                else
                    potentialID.add(rab.get_currentFieldID() - 1);
            }
            /** Gora */
            if(rab.get_currentFieldID() - _frame.xPanelAmount >= 0 && rab.get_currentFieldID() - _frame.xPanelAmount < _frame.panelList.size() && _frame.panelList.get(rab.get_currentFieldID() - _frame.xPanelAmount).get_isSthOnIt() == false){
                distanceToNextField = (int)Point2D.distance(_frame.panelList.get(rab.get_currentFieldID()).get_midX(),_frame.panelList.get(rab.get_currentFieldID()).get_midY(),_frame.panelList.get(rab.get_currentFieldID() - _frame.xPanelAmount).get_midX(),_frame.panelList.get(rab.get_currentFieldID() - _frame.xPanelAmount).get_midY());
                if(distanceToNextField > _frame._x * 2 && distanceToNextField > _frame._y * 2){
                }
                else
                    potentialID.add(rab.get_currentFieldID() - _frame.xPanelAmount);
            }
            /** Dol */
            if(rab.get_currentFieldID() + _frame.xPanelAmount >= 0 && rab.get_currentFieldID() + _frame.xPanelAmount < _frame.panelList.size() && _frame.panelList.get(rab.get_currentFieldID() + _frame.xPanelAmount).get_isSthOnIt() == false){
                distanceToNextField = (int)Point2D.distance(_frame.panelList.get(rab.get_currentFieldID()).get_midX(),_frame.panelList.get(rab.get_currentFieldID()).get_midY(),_frame.panelList.get(rab.get_currentFieldID() + _frame.xPanelAmount).get_midX(),_frame.panelList.get(rab.get_currentFieldID() + _frame.xPanelAmount).get_midY());
                if(distanceToNextField > _frame._x * 2 && distanceToNextField > _frame._y * 2){
                }
                else
                    potentialID.add(rab.get_currentFieldID() + _frame.xPanelAmount);
            }

            for(int i = 0; i < potentialID.size(); i++){
                int currentDistanceToWolf = (int)Point2D.distance(_frame.panelList.get(potentialID.get(i)).get_midX(),_frame.panelList.get(potentialID.get(i)).get_midY(), _frame.panelList.get(_wolf.getCurrent_FieldId()).get_midX(),_frame.panelList.get(_wolf.getCurrent_FieldId()).get_midY());
                if(currentDistanceToWolf >= maxDistanceToWolf){
                    potentialID_distance.clear();
                    maxDistanceToWolf = currentDistanceToWolf;
                    potentialID_distance.add(potentialID.get(i));
                }
            }

            if(!(potentialID_distance.size() == 0)){
                _frame.panelList.get(rab.get_currentFieldID()).setBackground(Color.GREEN);
                _frame.panelList.get(rab.get_currentFieldID()).set_SthOnIt(false);

                pickedID = _frame.randomVariable.nextInt(potentialID_distance.size());

                rab.set_currentFieldID(potentialID_distance.get(pickedID));
                _frame.panelList.get(rab.get_currentFieldID()).setBackground(Color.GRAY);
                _frame.panelList.get(rab.get_currentFieldID()).set_SthOnIt(true);
                potentialID.clear();
                potentialID_distance.clear();
            }

        }
    }
}
