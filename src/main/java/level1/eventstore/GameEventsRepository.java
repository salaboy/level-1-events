package level1.eventstore;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GameEventsRepository extends CrudRepository<GameEvents, String> {
}

