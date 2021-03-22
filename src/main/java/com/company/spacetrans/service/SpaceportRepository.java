package com.company.spacetrans.service;

import com.company.spacetrans.entity.Spaceport;
import io.jmix.core.DataManager;
import io.jmix.core.FluentLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class SpaceportRepository {

    private final DataManager dataManager;

    public SpaceportRepository(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Transactional
    public void unsetDefaultOtherSpaceports(Spaceport defaultPort) {
        FluentLoader.ByQuery<Spaceport> query = null;
        if (defaultPort.getMoon() != null) {
            query = dataManager.load(Spaceport.class)
                               .query("select s from st_Spaceport s where s.moon = :moon and s <> :changed and s.isDefault = true")
                               .parameter("moon", defaultPort.getMoon())
                               .parameter("changed", defaultPort);
        } else if (defaultPort.getPlanet() != null) {
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
}
