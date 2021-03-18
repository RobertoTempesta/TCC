package br.com.fio.cepp.bean;

import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_NERGOCIO;
import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_EXCLUIDO_SUCESSO;
import static br.com.fio.cepp.util.RedirectUtil.redirect;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.fio.cepp.dao.SupervisorDAO;
import br.com.fio.cepp.domain.Supervisor;
import br.com.fio.cepp.service.NegocioException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
@ManagedBean(name = "MBSupervisor")
@ViewScoped
public class SupervisoresBean implements Serializable {

	private Supervisor supervisor = null;
	private List<Supervisor> supervisores = null;
	private SupervisorDAO supervisorDAO = null;

	private static String PAGE_NOVO = "/paginas/privado/novo_supervisor.xhtml";
	private static String CODIGO_SUPERVISOR = "codigoSupervisor";
	private static String SUPERVISOR_SELECIONADO = "supervisorSelecionado";

	@PostConstruct
	public void init() {
		supervisor = new Supervisor();
		supervisorDAO = new SupervisorDAO();
		listar();
	}

	public void listar() {
		try {
			supervisores = new SupervisorDAO().listar();
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}

	public void editar(ActionEvent evento) {
		Supervisor supervisor = (Supervisor) evento.getComponent().getAttributes().get(SUPERVISOR_SELECIONADO);
		redirect(PAGE_NOVO, CODIGO_SUPERVISOR, supervisor.getCodigo());
	}

	public void excluir(ActionEvent evento) {
		try {
			Supervisor supervisor = (Supervisor) evento.getComponent().getAttributes().get(SUPERVISOR_SELECIONADO);
			supervisorDAO.excluir(supervisor);
			supervisores = supervisorDAO.listar();
			Messages.addGlobalInfo(MSG_EXCLUIDO_SUCESSO.getMsg());
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}

	public void detalhesSupervisor(ActionEvent evento) {
		try {
			this.supervisor = (Supervisor) evento.getComponent().getAttributes().get(SUPERVISOR_SELECIONADO);
			RequestContext.getCurrentInstance().execute("PF('dlgDetalhes').show();");
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
}
