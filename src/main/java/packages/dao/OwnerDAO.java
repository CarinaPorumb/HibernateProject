package packages.dao;

import packages.entity.Owner;
import packages.exception.OwnerNotFound;

import java.util.List;

public interface OwnerDAO {

    void createOwner(Owner owner);

    Owner createOwnerAndReturnIt(Owner owner);

    List<Owner> getAllOwners();

    Owner getOwnerById(int id) throws OwnerNotFound;

    Owner updateOwner(Owner owner);

    void deleteOwner(int id);
}