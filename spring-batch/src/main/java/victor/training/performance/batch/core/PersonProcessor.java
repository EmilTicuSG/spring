package victor.training.performance.batch.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import victor.training.performance.batch.core.domain.City;
import victor.training.performance.batch.core.domain.CityRepo;
import victor.training.performance.batch.core.domain.Person;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class PersonProcessor implements ItemProcessor<PersonXml, Person> {
    @Autowired
    private CityRepo cityRepo;

    Set<String> distinctNames = new HashSet<>();
    Map<String, Long> cityNameToId = new HashMap<>();

    @PostConstruct
    public void method() {
        cityNameToId = cityRepo.findAll().stream()
            .collect(Collectors.toMap(City::getName, City::getId));
    }
    
    @Override
    public Person process(PersonXml xml) {
        Person entity = new Person();
        entity.setName(xml.getName());
//        City city;
//        if (cityNameToId.containsKey(xml.getCity())) {
//            Long existingCityId = cityNameToId.get(xml.getCity());
//            city = new City().setId(existingCityId);
//        } else {
//            city = cityRepo.save(new City(xml.getCity()));
//            cityNameToId.put(xml.getCity(), city.getId());
//        }
        Long existingCityId = cityNameToId.get(xml.getCity());
        City city = new City().setId(existingCityId);
        entity.setCity(city);

        // elimina duplicatele
        if (distinctNames.contains(entity.getName())) {
            return null;
        }
        distinctNames.add(entity.getName());
        return entity;
    }

}