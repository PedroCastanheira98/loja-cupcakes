package com.doceencanto.loja.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_pedido")
public class ItemPedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    public Long id;
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    public Pedido pedido;
    @ManyToOne
    @JoinColumn(name = "id_produto")
    public Produto produto;
    public int quantidade;
    public BigDecimal precoUnitario;
}
