package com.poe.trajetfacile.controller;

import com.poe.trajetfacile.business.service.BookingService;
import com.poe.trajetfacile.business.service.RideService;
import com.poe.trajetfacile.domain.Booking;
import com.poe.trajetfacile.domain.User;
import com.poe.trajetfacile.exception.RideIsFullBusinessException;
import com.poe.trajetfacile.form.BookARideForm;
import com.poe.trajetfacile.repository.RideRepository;
import com.poe.trajetfacile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/book")
public class BookingController {

    @Autowired
    private RideService rideService;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BookingService bookingService;

    @PostMapping
    public String bookARide(Principal principal, @Valid BookARideForm form, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "redirect:/ride/list";
        }
        User user = userRepository.findByLogin(principal.getName());
        Booking booking = null;
        try {
            booking = bookingService.bookARide(user.getId(), form.getRideId());
        } catch (RideIsFullBusinessException e) {
            redirectAttributes.addFlashAttribute("message", "Ce trajet est déjà complet.");
            return "redirect:/ride/list";
        }
        model.addAttribute("book", booking);
        return "ride/booked";
    }


    @GetMapping("{userId}")
    public String listBookings(@PathVariable("userId") String userId) {
        bookingService.findAllForUser(Long.valueOf(userId));
        return "userBookings";
    }

}
