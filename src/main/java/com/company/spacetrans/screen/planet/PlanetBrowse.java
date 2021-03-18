package com.company.spacetrans.screen.planet;

import com.company.spacetrans.entity.Planet;
import com.company.spacetrans.service.PlanetRepository;
import io.jmix.ui.action.list.RefreshAction;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.SingleFileUploadField;
import io.jmix.ui.screen.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

@UiController("st_Planet.browse")
@UiDescriptor("planet-browse.xml")
@LookupComponent("planetsTable")
public class PlanetBrowse extends StandardLookup<Planet> {

    private static final Logger log = LoggerFactory.getLogger(PlanetBrowse.class);

    //todo QA why restricted injection through constructors? throws "Unable to create instance of screen class"
    @Named
    private PlanetRepository planetRepository;

    @Named("planetsTable.refresh")
    private RefreshAction planetsTableRefresh;

    @Subscribe("importPlanets")
    public void onImportPlanetsValueChange(HasValue.ValueChangeEvent<byte[]> event) {
        log.info("Import start");

        if (event.getValue() != null) {
            planetRepository.importPlanets(event.getValue());
        }
    }

    @Subscribe("importPlanets")
    public void onImportPlanetsFileUploadSucceed(SingleFileUploadField.FileUploadSucceedEvent event) {
        planetsTableRefresh.execute();
    }
}