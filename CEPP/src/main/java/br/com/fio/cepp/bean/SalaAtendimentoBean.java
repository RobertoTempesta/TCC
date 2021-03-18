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

import br.com.fio.cepp.dao.SalaAtendimentoDAO;
import br.com.fio.cepp.domain.SalaAtendimento;
import br.com.fio.cepp.service.NegocioException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
@ManagedBean(name = "MBSala")
@ViewScoped
public class SalaAtendimentoBean implements Serializable {

	private SalaAtendimento sala = null;
	private List<SalaAtendimento> salas = null;
	private SalaAtendimentoDAO salaDAO = null;
	
	private static String PAGE_NOVO = "/paginas/privado/nova_sala.xhtml";
	private static String COD_SALA = "codigoSala";

	@PostConstruct
	public void init() {
		salaDAO = new SalaAtendimentoDAO();
		this.sala = getLoadEdicao();
	}
	
	private SalaAtendimento getLoadEdicao() {
		Optional<Long> codigo = getKey(COD_SALA);
		if(codigo.isPresent()) {
			try {
				return salaDAO.buscar(codigo.get());	
			} catch (RuntimeException erro) {
				throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
			}
			
		} else {
			return new SalaAtendimento();
		}
	}

	public void listar() {
		try {
			salas = salaDAO.listar("descricao");
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}

	public void salvar() {
		try {
			salaDAO.merge(sala);
			novo();
			Messages.addGlobalInfo(MSG_SALVO_SUCESSO.getMsg());
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}

	}

	public void novo() {
		sala = new SalaAtendimento();
	}

	public void excluir(ActionEvent evento) {
		try {
			SalaAtendimento sala = (SalaAtendimento) evento.getComponent().getAttributes().get("salaSelecionada");

			salaDAO.excluir(sala);
			listar();
			Messages.addGlobalInfo(MSG_EXCLUIDO_SUCESSO.getMsg());
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}

	public void editar(ActionEvent evento) {
		SalaAtendimento sala = (SalaAtendimento) evento.getComponent().getAttributes().get("salaSelecionada");
		redirect(PAGE_NOVO, COD_SALA, sala.getCodigo());
	}
}
