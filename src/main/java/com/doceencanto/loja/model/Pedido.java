package com.doceencanto.loja.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    public Long id;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    public Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "id_endereco")
    public Endereco endereco;
    public LocalDateTime dataPedido = LocalDateTime.now();
    public String status = "PENDENTE";
    public BigDecimal valorTotal;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    public List<ItemPedido> itens = new ArrayList<>();
    @OneToOne(mappedBy = "pedido")
    public Pagamento pagamento;
    @OneToOne(mappedBy = "pedido")
    public Entrega entrega;
}
