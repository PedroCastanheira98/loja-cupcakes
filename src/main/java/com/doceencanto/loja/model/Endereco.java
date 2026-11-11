package com.doceencanto.loja.model;

import jakarta.persistence.*;

@Entity
@Table(name = "enderecos")
public class Endereco {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    public Long id;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    public Cliente cliente;
    public String rua;
    public String numero;
    public String complemento;
    public String bairro;
    public String cidade;
    public String estado;
    public String cep;
}
