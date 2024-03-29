package io.smartraise.controller.view;

import io.smartraise.model.Image;
import io.smartraise.model.fundraise.Charity;
import io.smartraise.model.fundraise.DonationRequest;
import io.smartraise.model.fundraise.Event;
import io.smartraise.model.fundraise.Organization;
import io.smartraise.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EventViewController {

    @Autowired
    private EventService eventService;

    @Autowired
    private DonationService donationService;

    @Autowired
    private CharityService charityService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/event")
    public String createEventForm(@Param("org") String org, Model model, Principal principal) {
        if (principal != null) {
            Organization organization = organizationService.get(org);
            Event event = new Event();
            event.setOrganization(organization);
            model.addAttribute("organization", organization);
            model.addAttribute("event", event);
            System.out.println(event.getStartDate());
            model.addAttribute("charities", charityService.getAllSorted());
            model.addAttribute("selectedCharity", new Charity());
            return "createEvent";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/event")
    public void createEvent(@ModelAttribute("event") Event event,
                            HttpServletResponse response, Principal principal) throws IOException {
        if (principal != null
                && event.getOrganization().getMembers().contains(principal.getName())
                && eventService.create(event)) {
            response.sendRedirect("/event/"+event.getEventId());
//            response.sendRedirect("/");
        } else {
            response.sendRedirect("/");
        }
    }

    @GetMapping("/event/{id}")
    public String getEvent(@PathVariable("id") String id, Model model, Principal principal) throws Exception {
        Event event = eventService.get(id);
        model.addAttribute("event", event);
        model.addAttribute("charityImage", imageService.get(event.getCharity().getCharityId(), Image.ImageType.ORG));
        model.addAttribute("orgImage", imageService.get(event.getOrganization().getOrganizationId(), Image.ImageType.ORG));
        model.addAttribute("donations", donationService.getDonationsByEvent(id));
        model.addAttribute("donationRequest", new DonationRequest());
        return "event";
    }

    @GetMapping("/")
    public String getMember(Model model, Principal principal) throws Exception {
        List<Event> past = eventService.getExpiredEvents();
        List<Event> current = eventService.getCurrentEvents();
        List<Event> future = eventService.getFutureEvents();
        Map<String, String> map = new HashMap<>();
        List[] events = new List[] {past, current, future};
        for (List<Event> eventList: events) {
            for (Event event: eventList) {
                map.put(event.getEventId(), imageService.get(event.getEventId(), Image.ImageType.PROFILE));
            }
        }
        model.addAttribute("past", past);
        model.addAttribute("current", current);
        model.addAttribute("future", future);
        model.addAttribute("images", map);
        if (principal != null) {
            model.addAttribute("user", principal.getName());
        }
        return "home";
    }


    @RequestMapping(value = "/event/{id}/donation",method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String getMember(@PathVariable("id") String id, DonationRequest donationRequest, Principal principal) {
        if (principal != null) {
            donationService.makeDonation(eventService.get(id), memberService.get(principal.getName()), donationRequest.getAmount());
            return "redirect:/event/"+id;
        } else {
            return "redirect:/login";
        }
    }
}
