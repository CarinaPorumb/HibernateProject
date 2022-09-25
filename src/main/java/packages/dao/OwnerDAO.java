package packages.dao;

import packages.entity.Owner;

import java.util.List;

public interface OwnerDAO {

    void createOwner(Owner owner);

    Owner createOwnerAndReturnIt(Owner owner);

    List<Owner> getAllOwners();

    Owner getOwnerById(int id);

    Owner updateOwner(Owner owner);

    void deleteOwner(int id);
}
