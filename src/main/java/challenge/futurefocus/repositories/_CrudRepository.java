package challenge.futurefocus.repositories;

import java.util.List;
import java.util.Optional;

public interface _CrudRepository<T> {

    void add(T object);
    void deleteById(int id);
    List<T> get();
    Optional<T> getById(int id);



}

