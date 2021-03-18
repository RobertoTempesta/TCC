package br.com.fio.cepp.bean;

import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_NERGOCIO;
import static br.com.fio.cepp.util.RedirectUtil.redirect;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.fio.cepp.dao.PacienteDAO;
import br.com.fio.cepp.domain.Paciente;
import br.com.fio.cepp.service.NegocioException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ManagedBean(name = "MBPaciente")
@ViewScoped
public class PacienteBean implements Serializable {

	private static final long serialVersionUID = 3395759380539498382L;

	private Paciente paciente = null;
	private List<Paciente> pacientes = null;
	
	private static String PAGE_NOVO = "/paginas/privado/novo_paciente.xhtml";
	private static String CODIGO_PACIENTE = "codigoPaciente";
	private static String PACIENTE_SELECIONADO = "pacienteSelecionado";

	@PostConstruct
	public void init() {
		paciente = new Paciente();
		listar();
	}

	public void listar() {
		try {
			pacientes = new PacienteDAO().listar();
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
	
	public void editar(ActionEvent evento) {
		Paciente paciente = (Paciente) evento.getComponent().getAttributes().get(PACIENTE_SELECIONADO);
		redirect(PAGE_NOVO, CODIGO_PACIENTE, paciente.getCodigo());
	}

	public void detalhesPaciente(ActionEvent evento) {
		try {
			this.paciente = (Paciente) evento.getComponent().getAttributes().get(PACIENTE_SELECIONADO);
			RequestContext.getCurrentInstance().execute("PF('dlgDetalhes').show();");
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			Paciente paciente = (Paciente) evento.getComponent().getAttributes().get(PACIENTE_SELECIONADO);
			PacienteDAO pacienteDAO = new PacienteDAO();
			pacienteDAO.excluir(paciente);

			pacientes = pacienteDAO.listar();
			Messages.addGlobalInfo("Paciente deletado com Sucesso");
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
}
