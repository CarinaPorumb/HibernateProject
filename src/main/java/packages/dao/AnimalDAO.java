package packages.dao;

import packages.entity.Animal;

import java.util.List;

public interface AnimalDAO {

    void createAnimal(Animal animal);

    Animal createAnimalAndReturnIt(Animal animal);

    List<Animal> getAllAnimals();

    Animal getAnimalById(int id) throws Exception;

    Animal updateAnimalById(Animal animal);

    void deleteAnimal(int id);
}
