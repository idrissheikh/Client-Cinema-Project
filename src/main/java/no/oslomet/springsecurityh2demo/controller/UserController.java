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
import java.util.*;

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

    /*@PostMapping("/saveBestilling")
    public String saveBestilling(@ModelAttribute("ticket") Ticket ticket ,

                                 @RequestParam("id") long userId,
                                 @RequestParam("id") long ticketId

    ) {



        List<Ticket> ticketList1 = new ArrayList<>();
        ticket = ticketRepsitory.findById(ticketId).get();
        ticketList1.add(ticket);

        model.addAttribute("ticket", ticket);
        ticketRepsitory.save(ticket);

        return "redirect:/order";
    } */


    public static String getDateTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);

        return strDate;
    }

    @GetMapping("/shipping/{id}")
    public String bestille(@PathVariable("id") String id, Model model, User user) {

        Ticket ticket = this.ticketRepsitory.findById(Long.parseLong(id)).get();

        List<Ticket> ticketList1 = new ArrayList<>();
        ticketList1.add(ticket);
        model.addAttribute("ticket", ticket);
        ticketRepsitory.save(ticket);
        model.addAttribute("user", user);


        return "order";
    }




}
