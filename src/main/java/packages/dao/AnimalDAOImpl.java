package packages.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import packages.entity.Animal;
import packages.exception.AnimalNotFound;
import packages.util.HibernateUtil;

import java.util.List;
import java.util.Set;

public class AnimalDAOImpl implements AnimalDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalDAOImpl.class);
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session session;
    private Transaction transaction;

    @Override
    public void createAnimal(Animal animal) {
        LOGGER.info("Creating animal: {}.", animal);
        openSessionAndTransaction();
        session.persist(animal);
        closeSessionAndTransaction();
        LOGGER.info("Animal {} was created.", animal);
    }

    @Override
    public void createAnimals(Set<Animal> animals) {
        LOGGER.info("Creating animals: ");
        openSessionAndTransaction();
        for (Animal anm : animals) {
            session.persist(anm);
        }
        closeSessionAndTransaction();
        LOGGER.info("Animals {} was created.", animals);
    }

    @Override
    public Animal createAnimalAndReturnIt(Animal animal) {
        LOGGER.info("Creating animal: {}.", animal);
        openSessionAndTransaction();
        int id = (int) session.save(animal);
        closeSessionAndTransaction();
        animal.setId(id);
        LOGGER.info("Animal {} was created.", animal);
        return animal;
    }

    @Override
    public List<Animal> getAllAnimals() {
        LOGGER.info("Getting all animals: ");
        String hql = "from animal";
        openSessionAndTransaction();
        Query query = session.createQuery(hql);
        List<Animal> animals = query.list();
        closeSessionAndTransaction();
        return animals;
    }

    @Override
    public Animal getAnimalById(int id) throws AnimalNotFound {
        LOGGER.info("Getting animals with id: {}.", id);
        session = sessionFactory.openSession();
        Animal animal = session.get(Animal.class, id);
        session.close();
        if (animal == null) {
            throw new AnimalNotFound();
        }
        LOGGER.info("Found animal: {}.", animal);
        return animal;
    }

    @Override
    public Animal updateAnimalById(Animal animal) {
        LOGGER.info("Updating animal: {}.", animal);
        openSessionAndTransaction();
        session.merge(animal);
        closeSessionAndTransaction();
        return animal;
    }

    @Override
    public void deleteAnimal(int id) {
        LOGGER.info("Deleting animal with id: {}.", id);
        try {
            final Animal animalById = getAnimalById(id);
            openSessionAndTransaction();
            session.remove(animalById);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Delete: Id not found!");
        }
        closeSessionAndTransaction();
    }

    private void openSessionAndTransaction() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    private void closeSessionAndTransaction() {
        transaction.commit();
        session.close();
    }
}