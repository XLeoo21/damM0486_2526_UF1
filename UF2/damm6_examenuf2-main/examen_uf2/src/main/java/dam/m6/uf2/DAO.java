package dam.m6.uf2;

import java.util.List;

public interface DAO<T> {
    void add(T item);

    List<T> getAll();
}
