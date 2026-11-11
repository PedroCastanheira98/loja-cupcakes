package com.doceencanto.loja.controller;

import com.doceencanto.loja.model.Cliente;
import com.doceencanto.loja.repository.ClienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final ClienteRepository clientes;
    private final PasswordEncoder encoder;

    public AuthController(ClienteRepository clientes, PasswordEncoder encoder) {
        this.clientes = clientes;
        this.encoder = encoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String salvar(@RequestParam("nome") String nome,
                         @RequestParam("email") String email,
                         @RequestParam(name = "telefone", required = false) String telefone,
                         @RequestParam("senha") String senha,
                         Model model) {
        if (clientes.existsByEmail(email)) {
            model.addAttribute("erro", "Este e-mail já está cadastrado.");
            return "cadastro";
        }
        if (senha.length() < 6) {
            model.addAttribute("erro", "A senha precisa ter pelo menos 6 caracteres.");
            return "cadastro";
        }
        Cliente c = new Cliente();
        c.nome = nome;
        c.email = email;
        c.telefone = telefone;
        c.senhaHash = encoder.encode(senha);
        clientes.save(c);
        return "redirect:/login?cadastrado";
    }
}
