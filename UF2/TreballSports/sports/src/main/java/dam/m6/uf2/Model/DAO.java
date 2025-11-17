package dam.m6.uf2.Model;

import java.util.List;

public interface DAO<T> {
   void add(T item);
   List<T> getAll();
   // List<T> getAll(String filter);
   // boolean checkPass(String name, String pass);
   // delete 
   // update
}
