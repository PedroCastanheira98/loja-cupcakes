package com.doceencanto.loja.repository;

import com.doceencanto.loja.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByAtivoTrue();
    List<Produto> findByAtivoTrueAndCategoria_Id(Long idCategoria);
}
