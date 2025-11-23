package dam.m6.uf2.Model;

import java.util.List;

public interface DAO<T> {
   void add(T item);
   List<T> getAll();
   List<T> getAll(int filter);
   List<T> getAll(String filter);
   List<T> getOne(String filter);

}
