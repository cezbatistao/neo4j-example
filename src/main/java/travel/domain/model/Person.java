package travel.domain.model;

import lombok.*;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static lombok.AccessLevel.PACKAGE;
import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = PACKAGE)
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
@NodeEntity
public class Person implements Serializable {

    private static final long serialVersionUID = -281586553477409265L;

    @GraphId
    private Long id;

    @NonNull
    private String name;

    @Relationship(type = "LIVED_IN", direction = OUTGOING)
    private List<City> cityLived;

    @Relationship(type = "TRAVELED_TO", direction = OUTGOING)
    private List<City> cityTraveled;

    public void livedIn(City city) {
        if (cityLived == null) {
            cityLived = newArrayList();
        }

        cityLived.add(city);
    }

    public void traveledTo(City city) {
        if (cityTraveled == null) {
            cityTraveled = newArrayList();
        }

        cityTraveled.add(city);
    }

    public void traveledTo(City... cities) {
        Arrays.stream(cities).forEach(this::traveledTo);
    }
}
