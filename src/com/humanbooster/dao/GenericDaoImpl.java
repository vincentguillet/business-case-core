package src.com.humanbooster.dao;

import java.util.List;

public class GenericDaoImpl<T, ID> implements  GenericDao<T,ID> {

    @Override
    public void create(T entity) {

    }

    @Override
    public T readById(ID id) {
        return null;
    }

    @Override
    public List<T> readAll() {
        return null;
    }

    @Override
    public void update(T entity) {

    }

    @Override
    public void delete(ID id) {

    }
}
