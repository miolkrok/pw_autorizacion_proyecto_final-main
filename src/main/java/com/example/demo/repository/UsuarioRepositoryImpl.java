package com.example.demo.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.demo.repository.modelo.Usuario;

@Repository
@Transactional
public class UsuarioRepositoryImpl implements IUsuarioRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Usuario consultarPorUserName(String username) {
		TypedQuery<Usuario> myQuery = this.entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.username =:datoNombre", Usuario.class);
		myQuery.setParameter("datoNombre", username);
		return myQuery.getSingleResult();
	}

}
