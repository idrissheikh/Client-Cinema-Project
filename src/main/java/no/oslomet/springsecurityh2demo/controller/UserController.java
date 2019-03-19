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

import java.text.ParseException;
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



    public static String getDateTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);

        return strDate;
    }

    @GetMapping("/shipping/{id}")
    public String bestille(@PathVariable("id") String id, Model model, User user) {

        List<Ticket> ticketList1 = new ArrayList<>();
        Ticket ticket = this.ticketRepsitory.findById(Long.parseLong(id)).get();

        ticketList1.add(ticket);
        model.addAttribute("ticket", ticket);
        ticketRepsitory.save(ticket);
        model.addAttribute("user", user);


        return "order";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id,Model model ){
        this.ticketRepsitory.deleteById(Long.parseLong(id));
        return "redirect:/home";
    }


    @GetMapping("/addTicket")
    public String addTicket(Model model ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName()).get();
        Ticket newTicket = new Ticket();
        model.addAttribute("ticket", newTicket);
        model.addAttribute("user", user);
        return "formTicket";
    }

    @PostMapping("/saveTicket")
    public String saveTicket(@RequestParam("date") String date,
                             @RequestParam("film") String film,
                             @RequestParam("cinema") String cinema) throws ParseException {
        Date d = new SimpleDateFormat("dd-MM-yyyy hh:mm").parse(date);

        Ticket tick = new Ticket(d, film, cinema);

        ticketRepsitory.save(tick);

        return ("redirect:/home");
    }



}
