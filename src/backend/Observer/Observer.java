package backend.Observer;

import backend.Pet;

//observer design pattern 
//observer - user and shelter system
public interface Observer {
    void AdoptionComplete(Pet pet);
    void DropOffComplete(Pet pet);
}
