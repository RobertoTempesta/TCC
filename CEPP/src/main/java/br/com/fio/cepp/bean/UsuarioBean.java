package br.com.fio.cepp.bean;

import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_NERGOCIO;
import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_EXCLUIDO_SUCESSO;
import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_SALVO_SUCESSO;
import static br.com.fio.cepp.util.RedirectUtil.getKey;
import static br.com.fio.cepp.util.RedirectUtil.redirect;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.fio.cepp.dao.UsuarioDAO;
import br.com.fio.cepp.domain.Usuario;
import br.com.fio.cepp.enumeracao.MensagensEnum;
import br.com.fio.cepp.service.NegocioException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ManagedBean(name = "MBUsuario")
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = -7249128232412599268L;

	private Usuario usuario = null;
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private List<Usuario> usuarios = null;
	
	private static String NOVO = "/paginas/privado/novo_usuario.xhtml";
	private static String USER_COD = "usuarioCodigo";
	private static String USER_SELEC = "usuarioSelecionado";

	@PostConstruct
	public void init() {
		usuario = getLoadEdicao();
	}

	public void listar() {
		try {
			usuarios = usuarioDAO.listar();
		} catch(RuntimeException er) {
			throw new NegocioException(MensagensEnum.MSG_ERRO_NERGOCIO.getMsg());
		}
	}

	public void editar(ActionEvent evento) {
		Usuario usuario = (Usuario) evento.getComponent().getAttributes().get(USER_SELEC);
		redirect(NOVO, USER_COD, usuario.getCodigo());
	}
	
	private Usuario getLoadEdicao() {
		Optional<Long> codigo = getKey(USER_COD);
		if(codigo.isPresent()) {
			try {
				return usuarioDAO.buscar(codigo.get());	
			} catch (RuntimeException erro) {
				throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
			}
			
		} else {
			return new Usuario();
		}
	}
	
	public void salvar() {
		try {
			usuarioDAO.merge(usuario);
			init();
			Messages.addGlobalInfo(MSG_SALVO_SUCESSO.getMsg());
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
	
	public void excluir(ActionEvent evento) {
		try {
			Usuario usuario = (Usuario) evento.getComponent().getAttributes().get(USER_SELEC);
			usuarioDAO.excluir(usuario);
			listar();
			Messages.addGlobalInfo(MSG_EXCLUIDO_SUCESSO.getMsg());
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
}
