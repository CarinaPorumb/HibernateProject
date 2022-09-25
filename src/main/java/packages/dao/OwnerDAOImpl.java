package packages.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import packages.entity.Owner;
import packages.util.HibernateUtil;

import java.util.List;

public class OwnerDAOImpl implements OwnerDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerDAOImpl.class);
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static Session session;
    private static Transaction transaction;

    @Override
    public void createOwner(Owner owner) {
    }

    @Override
    public Owner createOwnerAndReturnIt(Owner owner) {
        return null;
    }

    @Override
    public List<Owner> getAllOwners() {
        return null;
    }

    @Override
    public Owner getOwnerById(int id) {
        return null;
    }

    @Override
    public Owner updateOwner(Owner owner) {
        return null;
    }

    @Override
    public void deleteOwner(int id) {

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
