package org.example.dao;

;
import java.util.Map;

public interface Dao<T> {
    T save(T entity);

    T findById(Long key);

    T update(T entity);

    void delete(Long key);

    Map<Long,T> getAll();
}
