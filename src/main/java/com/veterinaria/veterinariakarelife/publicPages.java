package com.veterinaria.veterinariakarelife;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class publicPages {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/registro")
    public String registro() {
        return "registro";
    }

    @RequestMapping("/blog")
    public String blog() {
        return "blog";
    }

    @RequestMapping("/carrito")
    public String carrito() {
        return "carrito";
    }

    @RequestMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/manual")
    public String manual() {
        return "manual";
    }

    @RequestMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    @RequestMapping("/productos")
    public String productos() {
        return "productos";
    }

    @RequestMapping("/servicios")
    public String servicios() {
        return "servicios";
    }

    @RequestMapping("/adopcion")
    public String adopcion() {
        return "adopcion";
    }

}
