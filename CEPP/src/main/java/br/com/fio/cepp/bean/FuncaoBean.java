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

import br.com.fio.cepp.dao.FuncaoDAO;
import br.com.fio.cepp.domain.Funcao;
import br.com.fio.cepp.service.NegocioException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
@ManagedBean(name = "MBFuncao")
@ViewScoped
public class FuncaoBean implements Serializable {

	private List<Funcao> funcoes = null;
	private Funcao funcao = null;
	private FuncaoDAO funcaoDAO = null;
	
	private static String PAGE_NOVO = "/paginas/privado/novo_cargo.xhtml";
	private static String COD_FUNC = "codigoFuncao";
	private static String FUNC_SELECIONADA = "funcaoSelecionado";

	@PostConstruct
	public void init() {
		funcaoDAO = new FuncaoDAO();
		this.funcao = getLoadEdicao();
	}

	private Funcao getLoadEdicao() {
		Optional<Long> codigo = getKey(COD_FUNC);
		if(codigo.isPresent()) {
			try {
				return funcaoDAO.buscar(codigo.get());	
			} catch (RuntimeException erro) {
				throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
			}
			
		} else {
			return new Funcao();
		}
	}

	public void novo() {
		funcao = new Funcao();
	}

	public void listar() {
		try {
			funcoes = funcaoDAO.listar("descricao");
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}

	public void salvar() {
		try {
			funcaoDAO.merge(funcao);
			novo();
			Messages.addGlobalInfo(MSG_SALVO_SUCESSO.getMsg());
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}

	public void editar(ActionEvent evento) {
		Funcao funcao = (Funcao) evento.getComponent().getAttributes().get(FUNC_SELECIONADA);
		redirect(PAGE_NOVO, COD_FUNC, funcao.getCodigo());
	}

	public void excluir(ActionEvent evento) {
		try {
			Funcao funcao = (Funcao) evento.getComponent().getAttributes().get(FUNC_SELECIONADA);
			funcaoDAO.excluir(funcao);
			listar();
			Messages.addGlobalInfo(MSG_EXCLUIDO_SUCESSO.getMsg());
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}

}
