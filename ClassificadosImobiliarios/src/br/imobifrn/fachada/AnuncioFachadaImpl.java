package br.imobifrn.fachada;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.imobifrn.entidades.Anuncio;
import br.imobifrn.entidades.Usuario;
import br.imobifrn.negocio.Imobifrn;

@Stateless
public class AnuncioFachadaImpl implements AnuncioFachada {
	
	@EJB
	Imobifrn imobifrn;
	
	public AnuncioFachadaImpl() {
		super();
	}
	
	public boolean criarAnuncio(Anuncio anuncio){
		return imobifrn.criarAnuncio(anuncio);
	}

	public List<Anuncio> getAnuncios() {
		return imobifrn.getAnuncios();
	}
	
	public boolean criarUsuario(Usuario usuario) {
		return imobifrn.criarUsuario(usuario);
	}
}
