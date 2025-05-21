package com.humanbooster.dao;

import java.util.List;

public interface GenericDao <T, ID> {
    void create(T entity);
    T readById(ID id);
    List<T> readAll();
    void update(T entity);
    void delete(ID id);
}
