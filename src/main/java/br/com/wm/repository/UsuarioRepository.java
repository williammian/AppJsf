package br.com.wm.repository;

import java.io.Serializable;

import javax.persistence.Query;

import br.com.wm.model.UsuarioModel;
import br.com.wm.repository.entity.UsuarioEntity;
import br.com.wm.uteis.Uteis;


public class UsuarioRepository implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	public UsuarioEntity validaUsuario(UsuarioModel usuarioModel){
		
		try {

			//QUERY QUE VAI SER EXECUTADA (UsuarioEntity.findUser) 	
			Query query = Uteis.jpaEntityManager().createNamedQuery("UsuarioEntity.findUser");
			
			//PARÂMETROS DA QUERY
			query.setParameter("usuario", usuarioModel.getUsuario());
			query.setParameter("senha", usuarioModel.getSenha());
			
			//RETORNA O USUÁRIO SE FOR LOCALIZADO
			return (UsuarioEntity)query.getSingleResult();
			
		} catch (Exception e) {
			
			return null;
		}

		
		
	}
}
