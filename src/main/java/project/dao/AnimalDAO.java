package project.dao;

import project.entity.Animal;
import project.exception.AnimalNotFound;

import java.util.List;

public interface AnimalDAO {

    void createAnimal(Animal animal);

    void createAnimals(List<Animal> animals);

    Animal createAnimalAndReturnIt(Animal animal);

    List<Animal> getAllAnimals();

    Animal getAnimalById(int id) throws AnimalNotFound;

    Animal updateAnimalById(Animal animal);

    void deleteAnimal(int id);
}