package org.example.repositories;

import org.example.models.TableEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository//помечаем что этот бин - репозиторий
public interface TableRepository extends JpaRepository<TableEntry, String> {
//репозиторий является интерфейсом, который наследуется от другого интерфейса JpaRepository<>
//для него необходимо указать с какой сущность он должен работать, у нас это TableEntry
//и тип данных у поля id данной сущности, у нас это String
}