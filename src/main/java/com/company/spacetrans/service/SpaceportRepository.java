package com.company.spacetrans.service;

import com.company.spacetrans.entity.AstronomicalBody;
import com.company.spacetrans.entity.Moon;
import com.company.spacetrans.entity.Planet;
import com.company.spacetrans.entity.Spaceport;
import io.jmix.core.DataManager;
import io.jmix.core.FluentLoader;
import io.jmix.core.querycondition.PropertyCondition;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class SpaceportRepository {

    private static final Logger LOG = LoggerFactory.getLogger(SpaceportRepository.class);

    private final DataManager dataManager;

    public SpaceportRepository(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Transactional
    public void unsetDefaultOtherSpaceports(Spaceport defaultPort) {
        FluentLoader.ByQuery<Spaceport> query = null;
        AstronomicalBody astronomicalBody = getAstronomicalBody(defaultPort);
        if (astronomicalBody instanceof Moon) {
            query = dataManager.load(Spaceport.class)
                               .query("select s from st_Spaceport s where s.moon = :moon and s <> :changed and s.isDefault = true")
                               .parameter("moon", defaultPort.getMoon())
                               .parameter("changed", defaultPort);

        } else if (astronomicalBody instanceof Planet) {
            query = dataManager.load(Spaceport.class)
                               .query("select s from st_Spaceport s where s.planet = :planet and s <> :changed and s.isDefault = true")
                               .parameter("planet", defaultPort.getPlanet())
                               .parameter("changed", defaultPort);
        }

        if (query != null) {
            List<Spaceport> portsOnSameSpaceObject = query.list();
            for (Spaceport port : portsOnSameSpaceObject) {
                port.setIsDefault(false);
                dataManager.save(port);
            }
        }
    }

    public List<Spaceport> findAllSpaceports(AstronomicalBody astronomicalBody) {
        return findAllSpaceportsQuery(astronomicalBody).list();
    }

    public Spaceport findDefaultSpaceport(AstronomicalBody astronomicalBody) {
        FluentLoader.ByQuery<Spaceport> query = findAllSpaceportsQuery(astronomicalBody);
        PropertyCondition isDefaultCondition = new PropertyCondition();
        isDefaultCondition.setProperty(Spaceport.IS_DEFAULT);
        List<Spaceport> defaultSpaceports = query.condition(isDefaultCondition).list();
        switch (defaultSpaceports.size()) {
            case 0:
                return null;

            case 1:
                return defaultSpaceports.iterator().next();

            default:
                LOG.warn("AstronomicalBody '{}' must not have more than one default spaceport: {}", astronomicalBody, defaultSpaceports.size());
                return defaultSpaceports.iterator().next();
        }
    }

    private FluentLoader.ByQuery<Spaceport> findAllSpaceportsQuery(AstronomicalBody astronomicalBody) {
        FluentLoader.ByQuery<Spaceport> query;
        if (astronomicalBody instanceof Moon) {
            query = dataManager.load(Spaceport.class)
                               .query("select s from st_Spaceport s where s.moon = :moon")
                               .parameter("moon", astronomicalBody);

        } else if (astronomicalBody instanceof Planet) {
            query = dataManager.load(Spaceport.class)
                               .query("select s from st_Spaceport s where s.planet = :planet")
                               .parameter("planet", astronomicalBody);
        } else {
            throw new RuntimeException("Unreachable statement");
        }

        return query;
    }

    public AstronomicalBody getAstronomicalBody(Spaceport port) {
        Planet planet = port.getPlanet();
        Moon moon = port.getMoon();
        if (planet != null && moon != null) {
            throw new ContextedRuntimeException("Spaceport '" + port + "' cant have planet '" + planet + "' and moon '" + moon + "' simultaneously")
                    .addContextValue("port", port)
                    .addContextValue("planet", planet)
                    .addContextValue("moon", moon);
        }
        return planet != null ? planet : port.getMoon();
    }
}
