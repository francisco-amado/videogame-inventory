package com.inventory.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OwnerController {

    @GetMapping("/home/inventory")
    public String viewCollectionPage() {
        return "owner/inventory";
    }
}
