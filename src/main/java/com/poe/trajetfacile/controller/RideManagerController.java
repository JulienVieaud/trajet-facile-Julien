package com.poe.trajetfacile.controller;

import com.poe.trajetfacile.business.service.RideService;
import com.poe.trajetfacile.domain.Ride;
import com.poe.trajetfacile.domain.User;
import com.poe.trajetfacile.form.BookARideForm;
import com.poe.trajetfacile.form.OfferARideForm;
import com.poe.trajetfacile.repository.RideRepository;
import com.poe.trajetfacile.repository.UserRepository;
import com.poe.trajetfacile.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;

@Controller
@RequestMapping("/ride")
public class RideManagerController {

    @Autowired
    private RideService rideService;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showForm(OfferARideForm form, @RequestParam(name = "ride", required = false) String rideId, Principal principal, Model model) {
        if (rideId != null && !rideId.isEmpty()) {
            Ride ride = rideRepository.findOne(Long.valueOf(rideId));
            model.addAttribute("ride", ride);
        }
        System.out.println("LoggedIn user : " + principal.getName());


        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "ride/create";
    }

    @PostMapping
    public String offerARide(@Valid OfferARideForm form, BindingResult bindingResult, Principal principal, Model model, RedirectAttributes redirectAttributes) {

        User user = userRepository.findByLogin(principal.getName());

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "ride/create";
        }
        Date convertedDateMinutePrecision = DateUtils.convert(form.getStartDate(), form.getStartHours(), form.getStartMinutes());
        Ride ride = rideService.offerARide(convertedDateMinutePrecision, form.getFromCity(), form.getToCity(), form.getCost(), form.getSeats(), user.getId());
        redirectAttributes.addAttribute("ride", ride.getId());
        return "redirect:/ride";
    }

    @GetMapping("list")
    public String list(BookARideForm form, @ModelAttribute(value = "message") String message, Model model) {

        Iterable<Ride> rides;
        rides = rideRepository.findAll();
        Iterable<User> users = userRepository.findAll();

        model.addAttribute("rides", rides);
        model.addAttribute("users", users);
        model.addAttribute("message", message);
        return "ride/list";
    }


    @GetMapping("{id}")
    public String find(@PathVariable(value = "id") String rideId, BookARideForm bookARideForm, Model model) {
        Ride ride = rideRepository.findOne(Long.valueOf(rideId));
        model.addAttribute("ride", ride);
        return "ride/rideline";
    }

    @GetMapping("search")
    public String search(Model model, @RequestParam(name = "search", required = true) String search, BookARideForm form) {

        Iterable<Ride> rides;
        System.out.println("searching " + search);
        if (search != null && !search.isEmpty()) {
            System.out.println("searching town for " + search);
            rides = rideRepository.findAllByToCityLikeIgnoreCaseOrFromCityLikeIgnoreCase("%" + search + "%", "%" + search + "%");
        } else {
            rides = rideRepository.findAll();
        }

        Iterable<User> users = userRepository.findAll();

        model.addAttribute("rides", rides);
        model.addAttribute("search", search);
        return "ride/list"
                ;
    }
    @GetMapping("searchAjax")
    public String searchAjax(Model model, @RequestParam(name = "search", required = true) String search) {
        Iterable<Ride> rides;
        System.out.println("searching " + search);
        if (search != null && !search.isEmpty()) {
            System.out.println("searching town for " + search);
            rides = rideRepository.findAllByToCityLikeIgnoreCaseOrFromCityLikeIgnoreCase("%" + search + "%", "%" + search + "%");
        } else {
            rides = rideRepository.findAll();
        }

        Iterable<User> users = userRepository.findAll();
        model.addAttribute("rides", rides);
        return "ride/rides";
    }


    @MessageMapping("/newRide") // écoute sur ce canal
    @SendTo("/topic/resp") // répond sur ce canal
    public Ride newRideNotification(Ride ride) {
        System.out.println("new ride on air ! " + ride.getId());
        return ride;
    }


}
