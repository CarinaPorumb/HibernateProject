package project.dao;

import java.util.List;

public interface EntityDAO<E, I> {

    void create(E e);

    void createAll(List<E> eList);

    E createAndReturnIt(E e);

    List<E> getAll();

    E getById(int id) throws Exception;

    E update(E e);

    void delete(int id);

    void deleteAll();

}