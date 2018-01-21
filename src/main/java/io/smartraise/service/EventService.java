package io.smartraise.service;

import io.smartraise.model.donations.Donation;
import io.smartraise.model.fundraise.Event;
import io.smartraise.service.skeleton.ItemService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface EventService extends ItemService<Event> {

    List<Event> getEventsFromOrganization(UUID organizationId) throws Exception;

    List<Event> getCurrentEventsFromOrganization(UUID organizationId) throws Exception;

    Set<Event> getEventsByQueries(List<String> queries);
}
