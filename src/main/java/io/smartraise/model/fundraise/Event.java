package io.smartraise.model.fundraise;

import io.smartraise.model.accounts.Address;
import io.smartraise.util.CascadeSave;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.*;

public class Event {

    // TODO add tags

    @Id
    private final String eventId;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Address address;
    private double goal;
    @DBRef
    @CascadeSave
    private final Organization organization;
    @DBRef
    @CascadeSave
    private final Charity charity;

    public Event() {
        this.eventId = UUID.randomUUID().toString();
        this.name = "";
        this.description = "";
        this.startDate = Calendar.getInstance().getTime();
        this.endDate = Calendar.getInstance().getTime();
        this.organization =  new Organization();
        this.charity = new Charity();
        this.goal = 0;
        this.address = new Address();
    }

    public String getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Charity getCharity() {
        return charity;
    }

    public double getGoal() {
        return goal;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
