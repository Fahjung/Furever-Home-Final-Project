package backend.Observer;

import backend.Pet;

public class User implements Observer {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void AdoptionComplete(Pet pet) {
        System.out.println(name + ", you have successfully adopted " + pet.name + "the " + pet.getType());
    }

    @Override
    public void DropOffComplete(Pet pet) {
        System.out.println(name + ", you have successfully adopted " + pet.name + "the " + pet.getType());
    }
}