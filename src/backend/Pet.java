package backend;
//defines what is being adopted
public abstract class Pet {
    public String name;
    protected int age;
    protected String sex;
    protected String desc;
    protected String imagePath;

    public Pet(String name, int age, String sex, String desc, String imagePath) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.desc = desc;
        this.imagePath = imagePath;
    }
    public abstract String getType();
    
    public int getAge() {
        return age;
    }
    public String getSex() {
        return sex;
    }
    public String getImagePath() {
        return (imagePath == null || imagePath.isEmpty()) ? "default.png" : imagePath;
    }
    public String getDesc() {
        return desc;
    }
}

class Dog extends Pet {
    public Dog(String name, int age, String sex, String desc, String imagePath) {
        super(name, age, sex, desc, imagePath);
    }

    @Override
    public String getType() {
        return "Dog";
    }
}

class Cat extends Pet {
    public Cat(String name, int age, String sex, String desc, String imagePath) {
        super(name, age, sex, desc, imagePath);
    }

    @Override
    public String getType() {
        return "Cat";
    }
}

class Bunny extends Pet {
    public Bunny(String name, int age, String sex, String desc, String imagePath) {
        super(name, age, sex, desc, imagePath);
    }

    @Override
    public String getType() {
        return "Bunny";
    }
}

class Goat extends Pet {
    public Goat(String name, int age, String sex, String desc, String imagePath) {
        super(name, age, sex, desc, imagePath);
    }

    @Override
    public String getType() {
        return "Goat";
    }
}

class CustomPet extends Pet {
    private String customType;

    public CustomPet(String name, int age, String sex, String customType, String desc, String imagePath) {
        super(name, age, sex, desc, imagePath);
        this.customType = customType;
    }

    @Override
    public String getType() {
        return customType;
    }
}