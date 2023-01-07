package packages.dao;

import packages.entity.Animal;
import packages.exception.AnimalNotFound;

import java.util.List;
import java.util.Set;

public interface AnimalDAO {

    void createAnimal(Animal animal);

    public void createAnimals(Set<Animal> animals);

    Animal createAnimalAndReturnIt(Animal animal);

    List<Animal> getAllAnimals();

    Animal getAnimalById(int id) throws AnimalNotFound;

    Animal updateAnimalById(Animal animal);

    void deleteAnimal(int id);
}