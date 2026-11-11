package com.doceencanto.loja.service;

import com.doceencanto.loja.model.*;
import com.doceencanto.loja.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final EnderecoRepository enderecos;
    private final PedidoRepository pedidos;
    private final PagamentoRepository pagamentos;
    private final EntregaRepository entregas;

    public PedidoService(EnderecoRepository enderecos, PedidoRepository pedidos,
                         PagamentoRepository pagamentos, EntregaRepository entregas) {
        this.enderecos = enderecos;
        this.pedidos = pedidos;
        this.pagamentos = pagamentos;
        this.entregas = entregas;
    }

    // Grava tudo de uma vez: endereço, pedido, itens, pagamento (fictício) e entrega.
    // Se der erro em qualquer parte, nada é salvo.
    @Transactional
    public Pedido finalizar(Cliente cliente, Endereco endereco, String forma,
                            List<Carrinho.ItemCarrinho> itens, java.math.BigDecimal total) {
        endereco.cliente = cliente;
        enderecos.save(endereco);

        Pedido pedido = new Pedido();
        pedido.cliente = cliente;
        pedido.endereco = endereco;
        pedido.dataPedido = LocalDateTime.now();
        pedido.status = "PAGO";
        pedido.valorTotal = total;
        for (Carrinho.ItemCarrinho i : itens) {
            ItemPedido item = new ItemPedido();
            item.pedido = pedido;
            item.produto = i.produto();
            item.quantidade = i.quantidade();
            item.precoUnitario = i.produto().preco;
            pedido.itens.add(item);
        }
        pedidos.save(pedido);

        Pagamento pg = new Pagamento();
        pg.pedido = pedido;
        pg.forma = forma;
        pg.valor = total;
        pg.status = "APROVADO";
        pg.dataPagamento = LocalDateTime.now();
        pagamentos.save(pg);
        pedido.pagamento = pg;

        Entrega en = new Entrega();
        en.pedido = pedido;
        en.status = "AGUARDANDO";
        en.dataPrevista = LocalDate.now().plusDays(2);
        entregas.save(en);
        pedido.entrega = en;

        return pedido;
    }
}
