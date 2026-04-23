package backend.Strategy;

import backend.Pet;

public class DropOffStrategy extends AdoptionStrategy {
    @Override
    protected void processLogic() {
        System.out.println("Taking in paperwork and confirming drop off...");
    }
    @Override
    public void processDropOff(Pet pet) {
        this.currentPet = pet;
        processLogic();
        notifyObservers();
    }
}
