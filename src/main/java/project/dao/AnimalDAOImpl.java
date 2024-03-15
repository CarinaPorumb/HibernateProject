package project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.entity.Animal;
import project.exception.IdNotFound;
import project.util.HibernateUtil;

import java.util.List;

public class AnimalDAOImpl implements EntityDAO<Animal, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalDAOImpl.class);
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void create(Animal animal) {
        LOGGER.info("Creating animal: {}. ", animal);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(animal);
                transaction.commit();
                LOGGER.info("Animal created: {} ", animal);
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error creating animal: {} ", e.getMessage(), e);
                throw new RuntimeException("Error creating animal. ", e);
            }
        }
    }

    @Override
    public void createAll(List<Animal> animals) {
        LOGGER.info("Creating animals: {} ", animals);

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                animals.forEach(session::persist);
                transaction.commit();
                LOGGER.info("Animals {} were created. ", animals);
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error creating animals: " + e.getMessage());
                throw new RuntimeException("Error creating animals. ", e);
            }
        }
    }

    @Override
    public Animal createAndReturnIt(Animal animal) {
        LOGGER.info("Creating animal: {} ", animal);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(animal);
                transaction.commit();
                LOGGER.info("Animal {} with ID {} was created ", animal, animal.getId());
                return animal;
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error creating animal: " + e.getMessage());
                throw new RuntimeException("Error creating animal ", e);
            }
        }
    }

    @Override
    public List<Animal> getAll() {
        LOGGER.info("Getting all animals: ");

        try (Session session = sessionFactory.openSession()) {
            List<Animal> animals = session.createQuery("FROM Animal", Animal.class).getResultList();
            LOGGER.info("Found animals: {} ", animals);
            return animals;
        } catch (Exception e) {
            LOGGER.error("Error getting all animals: {}", e.getMessage(), e);
            throw new RuntimeException("Error getting all animals", e);
        }
    }

    @Override
    public Animal getById(int id) throws IdNotFound {
        LOGGER.info("Getting animals with id: {}.", id);
        try (Session session = sessionFactory.openSession()) {
            Animal animal = session.get(Animal.class, id);

            if (animal == null) {
                LOGGER.error("Animal with id {} not found!", id);
                throw new IdNotFound("Animal id not found");
            }
            LOGGER.info("Found animal: {}", animal);
            return animal;
        } catch (Exception e) {
            LOGGER.error("Error getting animal with id {}: {} ", id, e.getMessage(), e);
            throw new RuntimeException("Error getting animal with id " + id, e);
        }
    }

    @Override
    public Animal update(Animal animal) {
        LOGGER.info("Updating animal: {} ", animal);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(animal);
                transaction.commit();
                LOGGER.info("Animal updated: {} ", animal);
                return animal;
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error updating animal: {} ", e.getMessage(), e);
                throw new RuntimeException("Error updating animal. ", e);
            }
        } catch (Exception e) {
            LOGGER.error("Session fainled: {} ", e.getMessage(), e);
            throw new RuntimeException("Session failed!", e);
        }
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Deleting animal with id: {} ", id);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Animal animal = session.get(Animal.class, id);
                if (animal != null) {
                    session.remove(animal);
                    transaction.commit();
                    LOGGER.info("Animal with id {} deleted.", id);
                } else {
                    LOGGER.error("Animal id {} not found! ", id);
                    throw new IdNotFound("Animal id not found!");
                }
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error deleting animal: {} ", e.getMessage(), e);
                throw new RuntimeException("Error deleting animal! ", e);
            }
        } catch (IdNotFound e) {
            LOGGER.error("Delete: Animal id not found: {}", e.getMessage(), e);
        }
    }

    @Override
    public void deleteAll() {
        LOGGER.info("Deleting all animals. ");

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                List<Animal> animals = session.createQuery("FROM Animal", Animal.class).getResultList();
                animals.forEach(session::remove);
                transaction.commit();
                LOGGER.info("All animals have been deleted. ");
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error deleting all animals: {} ", e.getMessage(), e);
                throw new RuntimeException("Error during delete all animals.", e);
            }

        }
    }
}