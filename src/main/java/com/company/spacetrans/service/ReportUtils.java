package com.company.spacetrans.service;

import com.company.spacetrans.entity.Customer;
import com.company.spacetrans.entity.Spaceport;
import com.company.spacetrans.entity.User;
import com.company.spacetrans.entity.Waybill;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlanBuilder;
import io.jmix.core.FluentLoader;
import io.jmix.core.querycondition.LogicalCondition;
import io.jmix.core.querycondition.PropertyCondition;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class ReportUtils {

    private static Logger LOG = LoggerFactory.getLogger(ReportUtils.class);

    private static ReportUtils instance;

    private final DataManager dataManager;

    public ReportUtils(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @PostConstruct
    public void postConstruct() {
        instance = this;
    }

    @PreDestroy
    public void preDestroy() {
        ReportUtils.instance = null;
    }

    public List<Map<String, Object>> loadData(@Nullable User creator, @Nullable Customer shipper, @Nullable Customer consignee) {
        LogicalCondition andCondition = LogicalCondition.and();
        if (creator != null) {
            andCondition.add(PropertyCondition.equal(Waybill.CREATOR, creator));
        }
        if (shipper != null) {
            andCondition.add(PropertyCondition.equal(Waybill.SHIPPER, shipper));
        }
        if (consignee != null) {
            andCondition.add(PropertyCondition.equal(Waybill.CONSIGNEE, consignee));
        }

        FluentLoader<Waybill> load = dataManager.load(Waybill.class);
        FluentLoader.ByCondition<Waybill> loadByCondition;
        if (!andCondition.getConditions().isEmpty()) {
            loadByCondition = load.condition(andCondition);
        } else {
            loadByCondition = load.all();
        }

        List<Map<String, Object>> result = Lists.newArrayList();
        for (Waybill w : loadByCondition.list()) {
            Map<String, Object> map = new HashMap<>();
            map.put("waybill_id", w.getId());
            map.put("waybill_reference", w.getReference());
            map.put("waybill_creator", w.getCreator());
            map.put("waybill_shipper", w.getShipper());
            map.put("waybill_consignee", w.getConsignee());
            map.put("waybill_departure_port", w.getDeparturePort());
            map.put("waybill_destination_port", w.getDestinationPort());
            map.put("waybill_carrier", w.getCarrier());
            result.add(map);
        }

        LOG.info("Report data loaded: User = {}", creator);

        return result;
    }

    //todo HIGH learn how build fetch plans
    private Consumer<FetchPlanBuilder> buildFetchPlanConfigurer() {
        return fetchPlanBuilder -> {
            Consumer<FetchPlanBuilder> spaceportBuilder = fb -> fb.add(Spaceport.ID, Spaceport.NAME);
            fetchPlanBuilder.addAll(Waybill.ID,
                                    Waybill.REFERENCE,
                                    Waybill.CREATOR,
                                    Waybill.SHIPPER,
                                    Waybill.CONSIGNEE,
                                    Waybill.CARRIER)
                            .add(Waybill.DEPARTURE_PORT, spaceportBuilder)
                            .add(Waybill.DESTINATION_PORT, spaceportBuilder);
        };
    }

    public static ReportUtils getInstance() {
        return instance;
    }
}

/* todo HIGH rewrite on groovy stored script
import com.company.spacetrans.entity.Waybill
import io.jmix.core.querycondition.PropertyCondition

def result = []

def waybills = dataManager
                   .load(Waybill.class)
                   .condition(PropertyCondition.equal(Waybill.CREATOR,  params['p_waybill_creator']))
                   .list()
waybills.each { w ->

    result.add(
        [
            'waybill_id': w.getId(),
            'waybill_reference': w.getReference(),
            'waybill_creator': w.getCreator(),
            'waybill_shipper': w.getShipper(),
            'waybill_consignee': w.getConsignee(),
            'waybill_departure_port': w.getDeparturePort(),
            'waybill_destination_port': w.getDestinationPort(),
            'waybill_carrier': w.getCarrier()
        ]
    )
}

return result
* */