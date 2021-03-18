package com.company.spacetrans.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;

@JmixEntity
@Table(name = "ST_WAY_BILL")
@Entity(name = "st_WayBill")
public class WayBill extends AbstractEntity {

    @Column(name = "REFERENCE")
    private String reference;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "CREATOR_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "SHIPPER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Customer shipper;

    @JoinColumn(name = "CONSIGNEE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer consignee;

    @JoinColumn(name = "DEPARTURE_PORT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Spaceport departurePort;

    @JoinColumn(name = "DESTINATION_PORT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Spaceport destinationPort;

    @JoinColumn(name = "CARRIER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Carrier carrier;

    //todo add items etc

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


    //todo other attributes


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