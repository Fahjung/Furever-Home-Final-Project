package backend.Observer;
import java.util.ArrayList;
import java.util.List;
import backend.*;
//keeps track of pets in shelter system
public class ShelterSystem implements Observer{
    private static ShelterSystem instance;
    private List<Pet> petList = new ArrayList<>();

    public List<Pet> getPetList() {
        return this.petList;
    }
    public ShelterSystem() {
        petList.add(PetFactory.createPet("Dog", "Waan", 3, "Female", "Likes sweet liver", "https://kalmpets.com.au/wp-content/uploads/2024/10/aggressive-dog.jpg"));
        petList.add(PetFactory.createPet("Cat", "William", 2, "Male", "Does not meow", "https://tf-cmsv2-smithsonianmag-media.s3.amazonaws.com/filer/Surprising-Science-Feral-Cats-631.jpg"));
        petList.add(PetFactory.createPet("Goat", "Billy", 4, "Male", "Screams at night", "https://www.rspcasa.org.au/wp-content/uploads/2020/10/Baby-goat-in-tree.jpg"));
        petList.add(PetFactory.createPet("Cat", "Somchai", 2, "Male", "Misses his girlfriend, Somying", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQbJXc4hIEgsaeuTTFaV6lS9Mz9DGtc0d00nw&s"));
        petList.add(PetFactory.createPet("Bunny", "Chen", 67, "Male", "Imported from China + special skill is Kungfu", "https://pbs.twimg.com/media/Gw2LSNpa8AASSla.jpg"));
        petList.add(PetFactory.createPet("Dog", "Nacho", 2, "Male", "Only understands Spanish", "https://media.istockphoto.com/id/152514029/photo/a-mexican-dog-wearing-a-red-hat.jpg?s=612x612&w=0&k=20&c=tXElfIhbUiZjBrrJ0ghGdCsvpiKINF7qnmPidvUH2LM="));
        petList.add(PetFactory.createPet("Bunny", "Wang", 3, "Female", "Bites leather", "https://www.flowerchimp.com/cdn/shop/files/bunny-love-1112412685_1946x.jpg?v=1768194549"));
        petList.add(PetFactory.createPet("Bunny", "Wong", 3, "Male", "Bites Wang", "https://media.karousell.com/media/photos/products/2025/12/28/esther_bunny____furyu___1766944190_5f051990_progressive.jpg"));
        petList.add(PetFactory.createPet("Goat", "Dilly", 4, "Male", "Screams at day", "https://www.lollypop.org/wp-content/uploads/2018/01/Blg_Photo-2.jpg"));
        petList.add(PetFactory.createPet("Dog", "Duum", 6, "Male","Emo", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTF_4649nvi7IWIpbUGC__Awq7a6XXbR_hAHA&s"));
        petList.add(PetFactory.createPet("Cat", "Ma Ruay", 2, "Female", "Brings in wealth", "https://www.purina.co.uk/sites/default/files/2022-06/Thai.jpg?itok=XxHltSRg"));
        petList.add(PetFactory.createPet("Pony", "Twilight Sparkle", 20, "Female", "Here to conquer", "https://media.wired.com/photos/59337ed17965e75f5f3c8503/3:2/w_2560%2Cc_limit/MLP-Twilight.jpg"));
        petList.add(PetFactory.createPet("Pony", "Fluttershy", 20, "Female", "Easily spooked", "https://m.media-amazon.com/images/I/71dcfvU5taL._AC_UF1000,1000_QL80_.jpg"));
        petList.add(PetFactory.createPet("Pony", "Rarity", 20, "Female", "Loves sparkly things", "https://cdn.mobygames.com/covers/1830706-my-little-pony-rarity-loves-fashion-iphone-front-cover.jpg"));
        petList.add(PetFactory.createPet("Pony", "Rainbow Dash", 20, "Female", "Sonic booms", "https://preview.redd.it/tell-me-some-fun-facts-about-rainbow-dash-that-i-never-knew-v0-213pgqz1ft9e1.jpg?width=640&crop=smart&auto=webp&s=99db6eb4d8cfdcd859e8f051d0e9811cd61c5e00"));
        petList.add(PetFactory.createPet("Pony", "Apple Jack", 20, "Female", "*Warning* very aggressive", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPyeB31d9BISAaYDmC0febj-EF3FEEowwQ0A&s"));
        petList.add(PetFactory.createPet("Pony", "Pinkie Pie", 200, "Female", "Has diabetes", "https://lh5.googleusercontent.com/proxy/AP4tHT3wlQc_5DuWzhvxWB8uHlG6HF9IvIrZ0qEREqN5b1IKO6mJenx5fBKvpUeqZYyNkJPVqQjp6dZCNYtlM1eyjPpEsnaqy_WYGKkjt3mgPchI8M7zF9E7Anpa8SsSTcs"));
    }

    public static ShelterSystem getInstance() {
        if (instance == null) {
            instance = new ShelterSystem();
        }
        return instance;
    }

    public void addPet(Pet pet) {
        petList.add(pet);
    }
    public void removePet(Pet pet) {
        petList.remove(pet);
    }

    @Override
    public void AdoptionComplete(Pet pet) {
        removePet(pet);
        System.out.println("Shelter Update: " + pet.name + " the " + pet.getType() + " has been adopted.");
    }

    @Override
    public void DropOffComplete(Pet pet) {
        addPet(pet);
        System.out.println("Shelter Update: " + pet.name + " the " + pet.getType() + " has been dropped off.");
    }

    public Pet findPetByName(String name) {
    for (Pet pet : petList) {
        if (pet.name.equalsIgnoreCase(name)) {
            return pet;
        }
    }
    return null;
    }

    public List<Pet> findPetsByType(String type) {
        List<Pet> results = new ArrayList<>();
        for (Pet pet : petList) {
            if (pet.getType().equalsIgnoreCase(type)) {
                results.add(pet);
            }
        }
        return results;
    }
}
