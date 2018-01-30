package travel.domain.repository;

import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import travel.domain.model.City;

import java.util.List;

public interface CityRepository extends GraphRepository<City> {

    @Query("MATCH (p:Person)-[:LIVED_IN|:TRAVELED_TO]->(c:City), (p)<-[:LIVED_IN|:TRAVELED_TO]->(c1:City) WHERE c.name = {0} RETURN c1")
    @Depth(value = 2)
    List<City> findAllCitiesTheyPassedByCityName(String cityName);

}
