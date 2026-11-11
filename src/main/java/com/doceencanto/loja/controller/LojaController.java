package com.doceencanto.loja.controller;

import com.doceencanto.loja.model.Produto;
import com.doceencanto.loja.repository.CategoriaRepository;
import com.doceencanto.loja.repository.ProdutoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class LojaController {

    private final ProdutoRepository produtos;
    private final CategoriaRepository categorias;

    public LojaController(ProdutoRepository produtos, CategoriaRepository categorias) {
        this.produtos = produtos;
        this.categorias = categorias;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Produto> todos = produtos.findByAtivoTrue();
        model.addAttribute("destaques", todos.stream().limit(3).toList());
        return "home";
    }

    @GetMapping("/vitrine")
    public String vitrine(@RequestParam(name = "categoria", required = false) Long categoria, Model model) {
        model.addAttribute("categorias", categorias.findAll());
        model.addAttribute("categoriaAtual", categoria);
        model.addAttribute("produtos", categoria == null
            ? produtos.findByAtivoTrue()
            : produtos.findByAtivoTrueAndCategoria_Id(categoria));
        return "vitrine";
    }
}
