package com.company.spacetrans.service;

import com.company.spacetrans.entity.AstronomicalBody;
import com.company.spacetrans.entity.Atmosphere;
import com.company.spacetrans.entity.Planet;
import com.company.spacetrans.service.csv.CsvParser;
import com.company.spacetrans.service.csv.PlanetCSV;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PlanetRepository {

    private final CsvParser parser;

    private final DataManager dataManager;

    public PlanetRepository(CsvParser parser, DataManager dataManager) {
        this.parser = parser;
        this.dataManager = dataManager;
    }

    @Transactional
    public boolean importPlanets(byte[] csvBytes) {
        List<PlanetCSV> planetCSVs = parser.parse(csvBytes, PlanetCSV.class);

        if (planetCSVs != null) {
            final List<String> names = planetCSVs.stream().map(PlanetCSV::getName).collect(Collectors.toList());
            List<Planet> storedPlanets = findPlanets(names);

            Map<String, List<Planet>> storedPlanetsByName = groupByName(storedPlanets);

            for (PlanetCSV planetCSV : planetCSVs) {
                if (storedPlanetsByName.containsKey(planetCSV.getName())) {
                    Planet updatedPlanet = update(storedPlanetsByName.get(planetCSV.getName()).iterator().next(), planetCSV);
                    dataManager.save(updatedPlanet);
                } else {
                    Planet newPlanet = create(planetCSV);
                    dataManager.save(newPlanet);
                }
            }
            return true;
        }
        return false;
    }

    public List<Planet> findPlanets(List<String> names) {
        return dataManager.load(Planet.class)
                          .query("select p from st_Planet p where p.name in :names")
                          .parameter("names", names)
                          .list();
    }

    public List<Planet> findAll() {
        return dataManager.load(Planet.class).all().list();
    }

    private Planet create(PlanetCSV planetCSV) {
        Planet planet = dataManager.create(Planet.class);
        planet.setName(planetCSV.getName());
        update(planet, planetCSV);
        return planet;
    }

    private Atmosphere generateStubAtmosphere(Planet planet) {
        Atmosphere atmosphere = dataManager.create(Atmosphere.class);
        atmosphere.setDescription("Atmosphere for " + planet.getName());
        return atmosphere;
    }

    private Planet update(Planet planet, PlanetCSV planetCSV) {
        planet.setMass(planetCSV.getMass());
        planet.setSemiMajorAxis(planetCSV.getSemiMajorAxis());
        planet.setOrbitalPeriod(planetCSV.getOrbitalPeriod());
        planet.setRotationPeriod(planetCSV.getRotationPeriod());
        planet.setRings(planetCSV.getRings());
        planet.setAtmosphere(generateStubAtmosphere(planet));
        return planet;
    }

    public static Map<String, List<Planet>> groupByName(Collection<Planet> storedPlanets) {
        return storedPlanets
                .stream()
                //todo LOW make mapping to single value
                .collect(Collectors.groupingBy(AstronomicalBody::getName));
    }
}
