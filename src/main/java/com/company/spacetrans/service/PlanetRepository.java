package com.company.spacetrans.service;

import com.company.spacetrans.entity.AstronomicalBody;
import com.company.spacetrans.entity.Atmosphere;
import com.company.spacetrans.entity.Planet;
import com.company.spacetrans.service.csv.ImportPlanets;
import com.company.spacetrans.service.csv.PlanetCSV;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PlanetRepository {

    private final ImportPlanets importer;

    private final DataManager dataManager;

    public PlanetRepository(ImportPlanets importer, DataManager dataManager) {
        this.importer = importer;
        this.dataManager = dataManager;
    }

    @Transactional
    public boolean importPlanets(byte[] csvBytes) {
        List<PlanetCSV> planetCSVs = importer.parse(csvBytes);

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
                .collect(Collectors.groupingBy(AstronomicalBody::getName));
    }
}
