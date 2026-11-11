package com.doceencanto.loja.controller;

import com.doceencanto.loja.service.Carrinho;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarrinhoController {

    private final Carrinho carrinho;

    public CarrinhoController(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    @GetMapping("/carrinho")
    public String ver(Model model) {
        model.addAttribute("itens", carrinho.itens());
        model.addAttribute("total", carrinho.total());
        return "carrinho";
    }

    @PostMapping("/carrinho/adicionar")
    public String adicionar(@RequestParam("id") Long id) {
        carrinho.adicionar(id);
        return "redirect:/carrinho";
    }

    @PostMapping("/carrinho/remover")
    public String remover(@RequestParam("id") Long id) {
        carrinho.remover(id);
        return "redirect:/carrinho";
    }
}
