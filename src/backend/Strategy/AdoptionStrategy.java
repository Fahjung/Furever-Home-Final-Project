package backend.Strategy;
import java.util.ArrayList;
import java.util.List;

import backend.Pet;
import backend.Observer.Observer;
import backend.Observer.Subject;
//strategy design pattern - defines how adoption happens
public abstract class AdoptionStrategy implements Subject {
    private List<Observer> observers = new ArrayList<>();
    protected Pet currentPet;

    @Override
    public void addObserver(Observer o) { observers.add(o); }

    @Override
    public void removeObserver(Observer o) { observers.remove(o); }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.AdoptionComplete(currentPet);
        }
    }

    public void processAdoption(Pet pet) {
        this.currentPet = pet;
        processLogic();
        System.out.println("Processing adoption for " + pet.name + " the " + pet.getType());
        notifyObservers();
    }

    public void processDropOff(Pet pet) {
        this.currentPet = pet;
        processLogic();
        System.out.println("Processing drop off for " + pet.name + " the " + pet.getType());
    }

    protected abstract void processLogic();
}