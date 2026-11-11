package com.doceencanto.loja.service;

import com.doceencanto.loja.model.Cliente;
import com.doceencanto.loja.repository.ClienteRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Ensina o Spring a procurar o cliente no banco na hora do login
@Service
public class UsuarioService implements UserDetailsService {

    private final ClienteRepository clientes;

    public UsuarioService(ClienteRepository clientes) {
        this.clientes = clientes;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente c = clientes.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado"));
        return User.withUsername(c.email).password(c.senhaHash).roles(c.tipo).build();
    }
}
