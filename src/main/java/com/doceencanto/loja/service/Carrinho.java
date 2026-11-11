package com.doceencanto.loja.service;

import com.doceencanto.loja.model.Produto;
import com.doceencanto.loja.repository.ProdutoRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// O carrinho fica guardado na sessão: cada visitante tem o seu
@Component
@SessionScope
public class Carrinho {

    private final ProdutoRepository produtos;
    private final Map<Long, Integer> itens = new LinkedHashMap<>();

    public Carrinho(ProdutoRepository produtos) {
        this.produtos = produtos;
    }

    public void adicionar(Long idProduto) {
        itens.merge(idProduto, 1, Integer::sum);
    }

    public void remover(Long idProduto) {
        itens.remove(idProduto);
    }

    public void limpar() {
        itens.clear();
    }

    public int quantidadeTotal() {
        return itens.values().stream().mapToInt(Integer::intValue).sum();
    }

    public List<ItemCarrinho> itens() {
        List<ItemCarrinho> lista = new ArrayList<>();
        itens.forEach((id, qtd) ->
            produtos.findById(id).ifPresent(p -> lista.add(new ItemCarrinho(p, qtd))));
        return lista;
    }

    public BigDecimal total() {
        return itens().stream().map(ItemCarrinho::subtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public record ItemCarrinho(Produto produto, int quantidade) {
        public BigDecimal subtotal() {
            return produto.preco.multiply(BigDecimal.valueOf(quantidade));
        }
    }
}
