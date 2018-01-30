package travel.domain.model;

import lombok.*;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

import static lombok.AccessLevel.PACKAGE;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = PACKAGE)
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
@NodeEntity
public class City implements Serializable {

    private static final long serialVersionUID = 7138008179023191546L;

    @GraphId
    private Long id;

    @NonNull
    private String name;

}
