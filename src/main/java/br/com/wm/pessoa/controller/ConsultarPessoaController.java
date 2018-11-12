package br.com.wm.pessoa.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.wm.model.PessoaModel;
import br.com.wm.repository.PessoaRepository;

@Named(value="consultarPessoaController")
@ViewScoped
public class ConsultarPessoaController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject transient
	private PessoaModel pessoaModel;

	@Produces 
	private List<PessoaModel> pessoas;
	
	@Inject transient
	private PessoaRepository pessoaRepository;

	public List<PessoaModel> getPessoas() {
		return pessoas;
	}
	public void setPessoas(List<PessoaModel> pessoas) {
		this.pessoas = pessoas;
	}		
	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}
	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	/***
	 * CARREGA AS PESSOAS NA INICIALIZAÇÃO 
	 */
	@PostConstruct
	public void init(){
		
		//RETORNAR AS PESSOAS CADASTRADAS
		this.pessoas = pessoaRepository.getPessoas();
	}
	
	/***
	 * CARREGA INFORMAÇÕES DE UMA PESSOA PARA SER EDITADA
	 * @param pessoaModel
	 */
	public void editar(PessoaModel pessoaModel){
		
		/*PEGA APENAS A PRIMEIRA LETRA DO SEXO PARA SETAR NO CAMPO(M OU F)*/
		pessoaModel.setSexo(pessoaModel.getSexo().substring(0, 1));
		
		this.pessoaModel = pessoaModel;
				
	}
	
	/***
	 * ATUALIZA O REGISTRO QUE FOI ALTERADO
	 */
	public void alterarRegistro(){
		
		this.pessoaRepository.alterarRegistro(this.pessoaModel);	
		
				
		/*RECARREGA OS REGISTROS*/
		this.init();
	}
	
	/***
	 * EXCLUINDO UM REGISTRO
	 * @param pessoaModel
	 */
	public void excluirPessoa(PessoaModel pessoaModel){
		
		//EXCLUI A PESSOA DO BANCO DE DADOS
		this.pessoaRepository.excluirRegistro(pessoaModel.getCodigo());
		
		//REMOVENDO A PESSOA DA LISTA
		//ASSIM QUE É A PESSOA É REMOVIDA DA LISTA O DATATABLE É ATUALIZADO
		this.pessoas.remove(pessoaModel);
		
	}
		
}