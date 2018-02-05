package travel.domain.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import travel.domain.model.City;

import java.util.List;

public interface CityRepository extends GraphRepository<City> {

    @Query("MATCH (c:City)-[:LIVED_IN | TRAVELED_TO]-(:Person)-[:LIVED_IN | TRAVELED_TO]-(cities:City) WHERE c.name =~ {0} RETURN cities")
    List<City> findAllCitiesTheyPassedByCityName(String cityName);

}
