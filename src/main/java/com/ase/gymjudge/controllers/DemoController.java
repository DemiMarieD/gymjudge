package com.ase.gymjudge.controllers;

//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @RequestMapping("/demo/{name}")
    public String sayHello(@PathVariable String name){
        return "Hello " + name + "!";
    }

}
