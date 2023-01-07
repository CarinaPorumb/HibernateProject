package packages.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import packages.entity.Owner;
import packages.exception.OwnerNotFound;
import packages.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class OwnerDAOImpl implements OwnerDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerDAOImpl.class);
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static Session session;
    private static Transaction transaction;

    @Override
    public void createOwner(Owner owner) {
        LOGGER.info("Creating owner: {}.", owner);
        openSessionAndTransaction();
        session.persist(owner);
        closeSessionAndTransaction();
        LOGGER.info("Owner {} was created.", owner);
    }

    @Override
    public Owner createOwnerAndReturnIt(Owner owner) {
        LOGGER.info("Creating owner: {}.", owner);
        openSessionAndTransaction();
        int id = (int) session.save(owner);
        closeSessionAndTransaction();
        owner.setId(id);
        LOGGER.info("Owner {} was created.", owner);
        return owner;
    }

    @Override
    public List<Owner> getAllOwners() {
        LOGGER.info("Getting all owners: ");
        String hql = "from owner";
        session = sessionFactory.openSession();
        Query query = session.createQuery(hql);
        List<Owner> owners = query.list();
        session.close();
        return owners;
    }

    @Override
    public Owner getOwnerById(int id) throws OwnerNotFound {
        LOGGER.info("Getting owners with id: {}", id);
        session = sessionFactory.openSession();
        Owner owner = session.get(Owner.class, id);
        session.close();
        Optional.ofNullable(owner).orElseThrow(OwnerNotFound::new);
        return owner;
    }

    @Override
    public Owner updateOwner(Owner owner) {
        LOGGER.info("Updating owner: {}.", owner);
        openSessionAndTransaction();
        session.merge(owner);
        closeSessionAndTransaction();
        return owner;
    }

    @Override
    public void deleteOwner(int id) {
        LOGGER.info("Deleting owner with id: {}.", id);
        try {
            final Owner ownerById = getOwnerById(id);
            openSessionAndTransaction();
            session.remove(ownerById);
        } catch (OwnerNotFound exception) {
            exception.printStackTrace();
        }
        closeSessionAndTransaction();
    }

    private static void openSessionAndTransaction() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    private static void closeSessionAndTransaction() {
        transaction.commit();
        session.close();
    }
}