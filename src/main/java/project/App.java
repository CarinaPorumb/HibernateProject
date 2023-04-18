package project;

import com.github.javafaker.Faker;
import project.dao.AnimalDAO;
import project.dao.AnimalDAOImpl;
import project.dao.OwnerDAO;
import project.dao.OwnerDAOImpl;
import project.entity.Animal;
import project.entity.HealthCertificate;
import project.entity.Owner;
import project.entity.Toy;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        AnimalDAO animal = new AnimalDAOImpl();
        animal.createAnimals(createAnimal());
    }

    public static List<Animal> createAnimal() {
        List<Animal> animals = new ArrayList<>();
        Faker faker = new Faker();
        OwnerDAO ownerDAO = new OwnerDAOImpl();

        Animal animal1 = new Animal();
        animal1.setName("Rocco");
        animal1.setAge(2);
        animal1.setWeight(5);
        animals.add(animal1);

        Animal animal2 = new Animal();
        animal2.setName("Lady");
        animal2.setAge(2);
        animal2.setWeight(4);
        animals.add(animal2);

        for (Animal animal : animals) {
            HealthCertificate hc = new HealthCertificate();
            hc.setType("Travel Health Certificate");
            hc.setPrice(faker.number().randomDigitNotZero());
            animal.setHealthCertificate(hc);
        }

        animals.forEach(elem -> {
            elem.addToyToAnimal(new Toy(faker.superhero().name()));
            elem.addToyToAnimal(new Toy(faker.superhero().name()));
        });

        Owner owner1 = ownerDAO.createOwnerAndReturnIt(new Owner(faker.funnyName().name()));
        Owner owner2 = ownerDAO.createOwnerAndReturnIt(new Owner(faker.funnyName().name()));
        Owner owner3 = ownerDAO.createOwnerAndReturnIt(new Owner(faker.funnyName().name()));
        Owner owner4 = ownerDAO.createOwnerAndReturnIt(new Owner(faker.funnyName().name()));

        animals.forEach(elem -> {
            elem.addOwnerToAnimal(owner3);
            elem.addOwnerToAnimal(owner4);
            elem.addOwnerToAnimal(owner2);
            elem.addOwnerToAnimal(owner1);
        });

        return animals;
    }
}