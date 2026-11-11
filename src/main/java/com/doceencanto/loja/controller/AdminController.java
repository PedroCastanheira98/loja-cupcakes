package com.doceencanto.loja.controller;

import com.doceencanto.loja.model.Produto;
import com.doceencanto.loja.repository.CategoriaRepository;
import com.doceencanto.loja.repository.PedidoRepository;
import com.doceencanto.loja.repository.ProdutoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;

@Controller
public class AdminController {

    private final PedidoRepository pedidos;
    private final ProdutoRepository produtos;
    private final CategoriaRepository categorias;

    public AdminController(PedidoRepository pedidos, ProdutoRepository produtos,
                           CategoriaRepository categorias) {
        this.pedidos = pedidos;
        this.produtos = produtos;
        this.categorias = categorias;
    }

    @GetMapping("/admin")
    public String painel(Model model) {
        model.addAttribute("pedidos", pedidos.findAllByOrderByDataPedidoDesc());
        model.addAttribute("produtos", produtos.findAll());
        model.addAttribute("categorias", categorias.findAll());
        return "admin";
    }

    @PostMapping("/admin/produtos")
    public String novoProduto(@RequestParam("nome") String nome,
                              @RequestParam(name = "descricao", required = false) String descricao,
                              @RequestParam("preco") BigDecimal preco,
                              @RequestParam("categoria") Long idCategoria) {
        Produto p = new Produto();
        p.nome = nome;
        p.descricao = descricao;
        p.preco = preco;
        p.categoria = categorias.findById(idCategoria).orElseThrow();
        p.ativo = true;
        produtos.save(p);
        return "redirect:/admin?salvo";
    }

    @GetMapping("/admin/produtos/{id}/editar")
    public String editar(@PathVariable("id") Long id, Model model) {
        model.addAttribute("produto", produtos.findById(id).orElseThrow());
        model.addAttribute("categorias", categorias.findAll());
        return "admin-editar";
    }

    @PostMapping("/admin/produtos/{id}")
    public String atualizar(@PathVariable("id") Long id,
                            @RequestParam("nome") String nome,
                            @RequestParam(name = "descricao", required = false) String descricao,
                            @RequestParam("preco") BigDecimal preco,
                            @RequestParam("categoria") Long idCategoria) {
        Produto p = produtos.findById(id).orElseThrow();
        p.nome = nome;
        p.descricao = descricao;
        p.preco = preco;
        p.categoria = categorias.findById(idCategoria).orElseThrow();
        produtos.save(p);
        return "redirect:/admin?salvo";
    }

    // Se o cupcake já foi vendido, o banco não deixa excluir (para não perder o histórico).
    // Nesse caso o site avisa e sugere "Esconder".
    @PostMapping("/admin/produtos/{id}/excluir")
    public String excluir(@PathVariable("id") Long id) {
        try {
            produtos.deleteById(id);
            produtos.flush();
        } catch (Exception e) {
            return "redirect:/admin?erroExcluir";
        }
        return "redirect:/admin?excluido";
    }

    @PostMapping("/admin/produtos/{id}/ativo")
    public String alternarAtivo(@PathVariable("id") Long id) {
        Produto p = produtos.findById(id).orElseThrow();
        p.ativo = !p.ativo;
        produtos.save(p);
        return "redirect:/admin";
    }
}
