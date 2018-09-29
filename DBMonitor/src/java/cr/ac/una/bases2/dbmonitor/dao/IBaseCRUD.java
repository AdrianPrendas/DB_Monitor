package cr.ac.una.bases2.dbmonitor.dao;

import java.util.List;

/**
 *
 * @author _Adrián_Prendas_
 */
public interface IBaseCRUD<K, T> {
    T create(T t);
    T read(K k);
    List<T> readAll();
    boolean update(T t);
    boolean delete(K t); 
}
