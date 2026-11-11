package com.doceencanto.loja.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    public Long id;
    public String nome;
    public String email;
    public String senhaHash;
    public String telefone;
    public String tipo = "CLIENTE";
    public LocalDateTime dataCadastro = LocalDateTime.now();
}
