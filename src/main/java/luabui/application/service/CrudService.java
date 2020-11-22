package luabui.application.service;

import java.util.List;

public interface CrudService<T, ID> {
    List<T> findAll();

    T findById(ID id);

    T save(T newObject);

    void deleteById(ID id);
}