package project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.entity.Owner;
import project.exception.IdNotFound;
import project.util.HibernateUtil;

import java.util.List;

public class OwnerDAOImpl implements EntityDAO<Owner, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerDAOImpl.class);
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void create(Owner owner) {
        LOGGER.info("Creating owner: {}.", owner);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(owner);
                transaction.commit();
                LOGGER.info("Owner created: {}", owner);
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error creating owner: {}", e.getMessage(), e);
                throw new RuntimeException("Error creating owner!", e);
            }
        }
    }

    @Override
    public void createAll(List<Owner> owners) {
        LOGGER.info("Creating owners: {}", owners);

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                owners.forEach(session::persist);
                transaction.commit();
                LOGGER.info("Owners {} were created.", owners);
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error creating owners: " + e.getMessage());
                throw new RuntimeException("Error creating owners", e);
            }
        }
    }

    @Override
    public Owner createAndReturnIt(Owner owner) {
        LOGGER.info("Creating owner: {}.", owner);

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(owner);
                transaction.commit();
                LOGGER.info("Owner {} with ID {} was created.", owner, owner.getId());
                return owner;
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error creating owner: " + e.getMessage());
                throw new RuntimeException("Error creating owner!", e);
            }
        }
    }

    @Override
    public List<Owner> getAll() {
        LOGGER.info("Getting all owners: ");

        try (Session session = sessionFactory.openSession()) {
            List<Owner> owners = session.createQuery("FROM Owner", Owner.class).getResultList();
            LOGGER.info("Found owners: {} ", owners);
            return owners;
        } catch (Exception e) {
            LOGGER.error("Error getting all owners: {} ", e.getMessage(), e);
            throw new RuntimeException("Error getting all owners!", e);
        }
    }

    @Override
    public Owner getById(int id) throws IdNotFound {
        LOGGER.info("Getting owners with id: {}", id);

        try (Session session = sessionFactory.openSession()) {
            Owner owner = session.get(Owner.class, id);

            if (owner == null) {
                LOGGER.error("Owner with id {} not found.", id);
                throw new IdNotFound("Owner id not found!");
            }
            LOGGER.info("Found owner: {}", owner);
            return owner;
        } catch (Exception e) {
            LOGGER.error("Error getting owner with id {}: {} ", id, e.getMessage(), e);
            throw new RuntimeException("Error getting owner with id " + id, e);
        }
    }

    @Override
    public Owner update(Owner owner) {
        LOGGER.info("Updating owner: {} ", owner);

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(owner);
                transaction.commit();
                LOGGER.info("Owner updated: {} ", owner);
                return owner;
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error updating owner: {} ", e.getMessage(), e);
                throw new RuntimeException("Error updating owner!", e);
            }
        } catch (Exception e) {
            LOGGER.error("Session failed: {} ", e.getMessage(), e);
            throw new RuntimeException("Session operation failed!", e);
        }
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Deleting owner with id: {} ", id);

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Owner owner = session.get(Owner.class, id);
                if (owner != null) {
                    session.remove(owner);
                    transaction.commit();
                    LOGGER.info("Owner with id {} deleted.", id);
                } else {
                    LOGGER.error("Owner id {} not found!", id);
                    throw new IdNotFound("Owner id not found!");
                }
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error deleting owner: {}", e.getMessage(), e);
                throw new RuntimeException("Error deleting owner!", e);
            }

        } catch (IdNotFound e) {
            LOGGER.error("Owner id not found: {}", e.getMessage(), e);
        }
    }

    @Override
    public void deleteAll() {

        LOGGER.info("Deleting all owners. ");

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                List<Owner> owners = session.createQuery("FROM Owner", Owner.class).getResultList();
                owners.forEach(session::remove);
                transaction.commit();
                LOGGER.info("All owners have been deleted.");
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error deleting all owners: {} ", e.getMessage(), e);
                throw new RuntimeException("Error during delete all owners!", e);
            }

        }
    }
}