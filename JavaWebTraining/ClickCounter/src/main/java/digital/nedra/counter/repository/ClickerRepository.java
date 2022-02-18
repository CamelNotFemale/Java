package digital.nedra.counter.repository;

import digital.nedra.counter.entities.Click;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClickerRepository extends CrudRepository<Click, Integer> {
    /*@Override
    Optional<Click> findById(Integer integer);*/
    @Query(value = "select * from click c where c.id = :id for update", nativeQuery = true)
    Optional<Click> findClickForUpdate(Integer id);
}
