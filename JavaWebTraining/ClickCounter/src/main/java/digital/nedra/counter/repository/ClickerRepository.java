package digital.nedra.counter.repository;

import digital.nedra.counter.entities.Click;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ClickerRepository extends CrudRepository<Click, Integer> {
    @Override
    Optional<Click> findById(Integer integer);

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Click save(Click entity);
}
