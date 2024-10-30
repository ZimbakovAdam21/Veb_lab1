package mk.finki.ukim.mk.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.finki.ukim.mk.lab.service.EventBookingService;
import mk.finki.ukim.mk.lab.service.EventService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "EventListServlet", urlPatterns = "/")
public class EventListServlet extends HttpServlet {
    private final EventService eventService;
    private final SpringTemplateEngine springTemplateEngine;
    private final EventBookingService eventBookingService;


    public EventListServlet(EventService eventService, SpringTemplateEngine springTemplateEngine, EventBookingService eventBookingService) {
        this.eventService = eventService;
        this.springTemplateEngine = springTemplateEngine;
        this.eventBookingService = eventBookingService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);
        context.setVariable("events", eventService.listAll());
        springTemplateEngine.process("listEvents.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eventName =  req.getParameter("eventname");
        String attendeeName =  req.getParameter("attendeename");
        String attendeeAddress =  req.getParameter("attendeeAddress");
        int numberOfTickets =  Integer.parseInt( req.getParameter("numberOfTickets"));
        eventBookingService.placeBooking(eventName, attendeeName, attendeeAddress, numberOfTickets);
        resp.sendRedirect("/events");
    }
}
