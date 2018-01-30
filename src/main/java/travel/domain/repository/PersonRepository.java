package travel.domain.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import travel.domain.model.City;
import travel.domain.model.Person;

import java.util.List;

public interface PersonRepository extends GraphRepository<Person> {

    Person findByName(String fernando);

    @Query("MATCH (p:Person)-[:LIVED_IN|:TRAVELED_TO]->(c:City) WHERE p.name = {0} RETURN c")
    List<City> findAllCitiesByPerson(String personName);
}
