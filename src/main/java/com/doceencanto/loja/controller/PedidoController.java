package com.doceencanto.loja.controller;

import com.doceencanto.loja.model.Cliente;
import com.doceencanto.loja.model.Endereco;
import com.doceencanto.loja.repository.ClienteRepository;
import com.doceencanto.loja.repository.PedidoRepository;
import com.doceencanto.loja.service.Carrinho;
import com.doceencanto.loja.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.util.List;

@Controller
public class PedidoController {

    private final Carrinho carrinho;
    private final ClienteRepository clientes;
    private final PedidoRepository pedidos;
    private final PedidoService servico;

    public PedidoController(Carrinho carrinho, ClienteRepository clientes,
                            PedidoRepository pedidos, PedidoService servico) {
        this.carrinho = carrinho;
        this.clientes = clientes;
        this.pedidos = pedidos;
        this.servico = servico;
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        if (carrinho.itens().isEmpty()) {
            return "redirect:/carrinho";
        }
        model.addAttribute("itens", carrinho.itens());
        model.addAttribute("total", carrinho.total());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String finalizar(@RequestParam("rua") String rua,
                            @RequestParam("numero") String numero,
                            @RequestParam(name = "complemento", required = false) String complemento,
                            @RequestParam("bairro") String bairro,
                            @RequestParam("cidade") String cidade,
                            @RequestParam("estado") String estado,
                            @RequestParam("cep") String cep,
                            @RequestParam("forma") String forma,
                            Principal principal) {
        List<Carrinho.ItemCarrinho> itens = carrinho.itens();
        if (itens.isEmpty()) {
            return "redirect:/carrinho";
        }
        Cliente cliente = clientes.findByEmail(principal.getName()).orElseThrow();
        Endereco e = new Endereco();
        e.rua = rua;
        e.numero = numero;
        e.complemento = complemento;
        e.bairro = bairro;
        e.cidade = cidade;
        e.estado = estado.toUpperCase();
        e.cep = cep;
        servico.finalizar(cliente, e, forma, itens, carrinho.total());
        carrinho.limpar();
        return "redirect:/pedidos?novo";
    }

    @GetMapping("/pedidos")
    public String meusPedidos(Principal principal, Model model) {
        Cliente cliente = clientes.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("pedidos", pedidos.findByClienteOrderByDataPedidoDesc(cliente));
        return "pedidos";
    }
}
