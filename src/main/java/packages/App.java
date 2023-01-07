package packages;

import com.github.javafaker.Faker;
import packages.dao.AnimalDAO;
import packages.dao.AnimalDAOImpl;
import packages.dao.OwnerDAO;
import packages.dao.OwnerDAOImpl;
import packages.entity.Animal;
import packages.entity.HealthCertificate;
import packages.entity.Owner;
import packages.entity.Toy;

import java.util.HashSet;
import java.util.Set;

public class App {

    public static void main(String[] args) {
        AnimalDAO animal = new AnimalDAOImpl();
        animal.createAnimals(createAnimal());

    }

    public static Set<Animal> createAnimal() {
        Set<Animal> animals = new HashSet<>();
        Faker faker = new Faker();
        OwnerDAO ownerDAO = new OwnerDAOImpl();

        Animal animal = new Animal();
        animal.setName("Rocco");
        animal.setAge(2);
        animal.setWeight(5);
        animals.add(animal);

        Animal animal2 = new Animal();
        animal2.setName("Lady");
        animal2.setAge(2);
        animal2.setWeight(4);
        animals.add(animal2);

        HealthCertificate hc = new HealthCertificate();
        hc.setType("Travel Health Certificate");
        hc.setPrice(faker.number().randomDigitNotZero());
        hc.setAnimal(animal);

        animals.forEach(anm -> anm.setHealthCertificate(hc));

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