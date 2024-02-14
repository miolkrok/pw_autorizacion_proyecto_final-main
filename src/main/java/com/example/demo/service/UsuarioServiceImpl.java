package com.example.demo.service;

import static java.util.Collections.emptyList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.IUsuarioRepository;
import com.example.demo.repository.modelo.Usuario;

@Service
public class UsuarioServiceImpl implements UserDetailsService{

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Implementar la consulta del usuario a partir del username
		Usuario usuario = this.usuarioRepository.consultarPorUserName(username);
		return new User(usuario.getUsername(), usuario.getPassword(), emptyList());
	}

}
