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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping({"/list"})
    public String ticketListe(Model model){
        List<Ticket> ticketList = ticketRepsitory.findAll();
        return "list";
        
    }



}
