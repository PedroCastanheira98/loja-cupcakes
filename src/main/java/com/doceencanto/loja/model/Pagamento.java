package com.doceencanto.loja.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
public class Pagamento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    public Long id;
    @OneToOne
    @JoinColumn(name = "id_pedido")
    public Pedido pedido;
    public String forma;
    public BigDecimal valor;
    public String status = "PENDENTE";
    public LocalDateTime dataPagamento = LocalDateTime.now();
}
