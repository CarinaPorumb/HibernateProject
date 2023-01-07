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

public class App {
    public static void main(String[] args) {

        AnimalDAO animal = new AnimalDAOImpl();
        animal.createAnimal(createAnimal());

    }

    public static Animal createAnimal() {
        Faker faker = new Faker();
        OwnerDAO owner = new OwnerDAOImpl();
        Animal animal = new Animal();
        animal.setName(faker.funnyName().name());
        animal.setAge(faker.number().numberBetween(1, 15));
        animal.setWeight(faker.number().randomDigit());

        HealthCertificate hc = new HealthCertificate();
        hc.setType("Travel Health Certificate");
        hc.setPrice(faker.number().randomDigitNotZero());
        hc.setAnimal(animal);
        animal.setHealthCertificate(hc);

        animal.addToyToAnimal(new Toy(faker.superhero().name()));
        animal.addToyToAnimal(new Toy(faker.superhero().name()));

        animal.addOwnerToAnimal(owner.createOwnerAndReturnIt(new Owner(faker.name().fullName())));
        animal.addOwnerToAnimal(owner.createOwnerAndReturnIt(new Owner(faker.name().name())));
        animal.addOwnerToAnimal(owner.createOwnerAndReturnIt(new Owner(faker.funnyName().name())));
        return animal;
    }
}