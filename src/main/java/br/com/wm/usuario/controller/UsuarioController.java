package br.com.wm.usuario.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.wm.model.UsuarioModel;
import br.com.wm.repository.UsuarioRepository;
import br.com.wm.repository.entity.UsuarioEntity;
import br.com.wm.uteis.Uteis;

@Named(value="usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1L;
		
	@Inject
	private UsuarioModel usuarioModel;

	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Inject
	private UsuarioEntity usuarioEntity;
	
	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}
	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}
	
	public UsuarioModel getUsuarioSession(){
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		return (UsuarioModel)facesContext.getExternalContext().getSessionMap().get("usuarioAutenticado");
	}
	
	public String logout(){
						
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		return "/index.xhtml?faces-redirect=true";
	}
	public String efetuarLogin(){
			
		if(StringUtils.isEmpty(usuarioModel.getUsuario()) || StringUtils.isBlank(usuarioModel.getUsuario())){
			
			Uteis.mensagem("Favor informar o login!");
			return null;
		}
		else if(StringUtils.isEmpty(usuarioModel.getSenha()) || StringUtils.isBlank(usuarioModel.getSenha())){
			
			Uteis.mensagem("Favor informara senha!");
			return null;
		}
		else{	

			usuarioEntity = usuarioRepository.validaUsuario(usuarioModel);
			
			if(usuarioEntity!= null){
							
				usuarioModel.setSenha(null);
				usuarioModel.setCodigo(usuarioEntity.getCodigo());
				
				
				FacesContext facesContext = FacesContext.getCurrentInstance();
				
				facesContext.getExternalContext().getSessionMap().put("usuarioAutenticado", usuarioModel);
				
				
				return "sistema/home?faces-redirect=true";
			}
			else{
				
				Uteis.mensagem("Não foi possível efetuar o login com esse usuário e senha!");
				return null;
			}
		}
		
		
	}
	
}