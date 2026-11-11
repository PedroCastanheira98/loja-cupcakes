package com.doceencanto.loja.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    public Long id;
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    public Categoria categoria;
    public String nome;
    public String descricao;
    public BigDecimal preco;
    public String imagemUrl;
    public boolean ativo = true;
}
