package com.doceencanto.loja.controller;

import com.doceencanto.loja.service.Carrinho;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.security.Principal;

// Deixa disponível em TODAS as páginas: nome do usuário, se é admin e itens do carrinho
@ControllerAdvice
public class GlobalAdvice {

    private final Carrinho carrinho;

    public GlobalAdvice(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    @ModelAttribute("nomeUsuario")
    public String nomeUsuario(Principal principal) {
        return principal == null ? null : principal.getName();
    }

    @ModelAttribute("ehAdmin")
    public boolean ehAdmin(HttpServletRequest request) {
        return request.isUserInRole("ADMIN");
    }

    @ModelAttribute("qtdCarrinho")
    public int qtdCarrinho() {
        return carrinho.quantidadeTotal();
    }
}
