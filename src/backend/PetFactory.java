package backend;
//factory design pattern 
public class PetFactory {
    public static Pet createPet(String type, String name, int age, String sex, String desc, String imagePath) {
        if (type.equals("Dog")) return new Dog(name, age, sex, desc, imagePath);
        if (type.equals("Cat")) return new Cat(name, age, sex, desc, imagePath);
        if (type.equals("Bunny")) return new Bunny(name, age, sex, desc, imagePath);
        if (type.equals("Goat")) return new Goat(name, age, sex, desc, imagePath);
        return new CustomPet(name, age, sex, type, desc, imagePath);
    } 
}