package com.doceencanto.loja.repository;

import com.doceencanto.loja.model.Cliente;
import com.doceencanto.loja.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteOrderByDataPedidoDesc(Cliente cliente);
    List<Pedido> findAllByOrderByDataPedidoDesc();
}
