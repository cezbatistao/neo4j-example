package travel.domain.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import travel.domain.model.City;
import travel.domain.model.Person;
import travel.infrastructure.config.Application;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@Transactional
public class PersonRepositoryIntegrationTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CityRepository cityRepository;

    private City toronto;
    private City roma;
    private City berlin;
    private City brussels;
    private City paris;
    private City newYork;
    private City tokyo;

    @Before
    public void setup() {
        Person joao = new Person("João");
        Person ricardo = new Person("Ricardo");
        Person carolina = new Person("Carolina");
        Person maria = new Person("Maria");
        Person fernando = new Person("Fernando");
        Person fabio = new Person("Fábio");

        toronto = new City("Toronto");
        roma = new City("Roma");
        berlin = new City("Berlim");
        brussels = new City("Bruxelas");
        paris = new City("Paris");
        newYork = new City("Nova York");
        tokyo = new City("Tóquio");

        cityRepository.save(newArrayList(toronto, roma, berlin, brussels, paris, newYork, tokyo));

        joao.livedIn(newYork);
        joao.traveledTo(paris);

        ricardo.livedIn(toronto);
        ricardo.traveledTo(roma, brussels);

        carolina.traveledTo(roma);

        maria.livedIn(berlin);
        maria.traveledTo(newYork, tokyo);

        fernando.livedIn(berlin);
        fernando.traveledTo(brussels, paris, tokyo);

        fabio.traveledTo(newYork);

        personRepository.save(newArrayList(joao, ricardo, carolina, maria, fernando, fabio));
    }

    @Test
    public void testFindPersonByName() {
        Person fernando = personRepository.findByName("Fernando");

        assertThat(fernando).isNotNull();
        assertThat(fernando.getCityLived()).isNotEmpty()
                .hasSize(1)
                .contains(berlin)
                .doesNotContain(toronto, roma, brussels, paris, newYork, tokyo);
        assertThat(fernando.getCityTraveled()).isNotEmpty()
                .hasSize(3)
                .contains(brussels, paris, tokyo)
                .doesNotContain(toronto, roma, berlin, newYork);
    }

    @Test
    public void testFindPersonsHowKnownCity() {
        List<City> allCities = personRepository.findAllCitiesByPerson("Ricardo");

        assertThat(allCities).isNotEmpty()
                .hasSize(3)
                .contains(toronto, roma, brussels)
                .doesNotContain(berlin, paris, newYork, tokyo);
    }

    @Test
    public void testVerifyCitiesWhoPassedByNewYork() {
        List<City> allCities = cityRepository.findAllCitiesTheyPassedByCityName("Nova York");

        assertThat(allCities).isNotEmpty()
                .hasSize(3)
                .contains(tokyo, paris, berlin)
                .doesNotContain(toronto, roma, brussels, newYork);
    }
}
