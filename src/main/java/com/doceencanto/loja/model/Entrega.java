package com.doceencanto.loja.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "entregas")
public class Entrega {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrega")
    public Long id;
    @OneToOne
    @JoinColumn(name = "id_pedido")
    public Pedido pedido;
    public String status = "AGUARDANDO";
    public LocalDate dataPrevista;
    public LocalDateTime dataEntrega;
}
