package br.imobifrn.mbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.imobifrn.entidades.Anuncio;
import br.imobifrn.entidades.Usuario;
import br.imobifrn.exception.UsuarioExistenteException;
import br.imobifrn.fachada.AnuncioFachada;
import br.imobifrn.fachada.UsuarioFachada;

@ManagedBean(name="usuarioMB")
@SessionScoped
public class UsuarioMB {
	
	@EJB
	AnuncioFachada anuncioFachada;
	
	@EJB
	UsuarioFachada usuarioFachada;
	
	private Usuario usuario;
	private String confirmarSenha;
	private String mensagem;
	private String login, senha;
	private Usuario usuarioLogado;

	private boolean logado;
	
	public UsuarioMB() {
		super();
	}
	
	@PostConstruct
	void postConstruct() {
		usuario = new Usuario();
		confirmarSenha = "";
		mensagem = "";
		logado = false;
	}
	
	public String criarUsuario() throws UsuarioExistenteException{
		if (login != null && !login.equals("") &&
			senha != null && !senha.equals("") &&
			confirmarSenha != null && !confirmarSenha.equals("")) {
			if (senha.equals(confirmarSenha)) {
				usuario = new Usuario(login, senha);
				usuarioFachada.criarUsuario(usuario);
				usuario = new Usuario();
				return "index.xhtml";
			}
			else {
				this.setMensagem("As senha não correspondem");
			}
		}
		else {
			this.setMensagem("Todos os campos precisam ser preenchidos");
		}
		return "cadastroUsuario.xhtml";
	}
	
	public String efetuarLogin() {
		if (!login.isEmpty() && !senha.isEmpty() ) {
			usuario = usuarioFachada.autenticar(login, senha);
			if (usuario == null) {
				this.setMensagem("Informa��es n�o correspondem a um usu�rio v�lido!");
			}
			else {
				usuarioLogado = usuario;
				logado = true;
			}
		} else {
			this.setMensagem("Forne�a as informa��es de login e senha!");
		}
		System.out.println(login);
		return "index.xhtml";
	}
	
	public String logout() {
		usuario = new Usuario();
		login = "";
		logado = false;
		return "index.xhtml";
	}
	
	public List<Anuncio> getAnunciosUsuarioLogado()
	{
		if(isLogado())
			return anuncioFachada.getAnunciosUsuarioLogado(usuario.getId());
		
		this.setMensagem("N�o existe usuario logado");
		return null;
	}
	
	
	/*
	 * Gets e Setters	
	 * */
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public String getMensagem() {
		String retorno = mensagem;
		mensagem = "";
		return retorno;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public boolean isComMensagem() {
		return !mensagem.isEmpty();
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public boolean isLogado() {
		return logado;
	}
	
	public boolean isNotLogado() {
		return !logado;
	}
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
