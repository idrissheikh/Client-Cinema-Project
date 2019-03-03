package no.oslomet.springsecurityh2demo.controller;

import no.oslomet.springsecurityh2demo.model.Ticket;
import no.oslomet.springsecurityh2demo.model.User;
import no.oslomet.springsecurityh2demo.repository.TicketRepsitory;
import no.oslomet.springsecurityh2demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepsitory ticketRepsitory;

    @GetMapping("/")
    public String home(){
        return "login";
    }

    @GetMapping("/home")
    public String homePage(Model model ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("principal: " + auth.getName());
        Optional<User> user = userRepository.findUserByEmail(auth.getName());
        List<Ticket> ticketList = ticketRepsitory.findAll();

        System.out.println("innlogged user: " + user);
       if(user.isPresent()) model.addAttribute("user", user.get());
         model.addAttribute("ticketes", ticketList);
        System.out.println("ticket inholder: " + ticketList.size());


        return "index";

    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/processRegistration")
    public String register(@ModelAttribute("user") User user){
        String noopPassword = "{noop}"+user.getPassword();
        user.setPassword(noopPassword);
        user.setRoles("USER");
        System.out.println("user to save: " + user);
        userRepository.save(user);
        return "redirect:/";
    }


    /*@GetMapping({"/list"})
    public String ticketListe(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user = userRepository.findUserByEmail(auth.getName());

        if(user.isPresent()) model.addAttribute("user", user.get());

        List<Ticket> ticketList = ticketRepsitory.findAll();
        return "list";
        
    }*/



    public static String getDateTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);

        return strDate;
    }

    @GetMapping("/shipping/{id}")
    public String bestille(@PathVariable("id") String id, Model model) {
        Ticket ticket = this.ticketRepsitory.findById(Long.parseLong(id)).get();


        String date = getDateTime();
        User user = new User();

        model.addAttribute("ticket", ticket);
        model.addAttribute("user", user);

        return "shipping";
    }




}
