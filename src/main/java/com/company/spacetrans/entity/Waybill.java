package com.company.spacetrans.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "ST_WAYBILL")
@Entity(name = "st_Waybill")
public class Waybill {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @InstanceName
    @Column(name = "REFERENCE", nullable = false, unique = true)
    private String reference;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "CREATOR_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "SHIPPER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Customer shipper;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "CONSIGNEE_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Customer consignee;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "DEPARTURE_PORT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Spaceport departurePort;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "DESTINATION_PORT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Spaceport destinationPort;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "CARRIER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Carrier carrier;

    @OnDeleteInverse(DeletePolicy.DENY)
    @Composition
    @OneToMany(mappedBy = "waybill")
    private List<WaybillItem> items;

    @Column(name = "TOTAL_WEIGHT")
    private Double totalWeight;

    @Column(name = "TOTAL_CHARGE", precision = 19, scale = 2)
    private BigDecimal totalCharge;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public List<WaybillItem> getItems() {
        return items;
    }

    public void setItems(List<WaybillItem> items) {
        this.items = items;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public Spaceport getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(Spaceport destinationPort) {
        this.destinationPort = destinationPort;
    }

    public Spaceport getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort(Spaceport departurePort) {
        this.departurePort = departurePort;
    }

    public Customer getConsignee() {
        return consignee;
    }

    public void setConsignee(Customer consignee) {
        this.consignee = consignee;
    }

    public Customer getShipper() {
        return shipper;
    }

    public void setShipper(Customer shipper) {
        this.shipper = shipper;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}