package br.com.fio.cepp.bean;

import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_NERGOCIO;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.fio.cepp.dao.PacienteDAO;
import br.com.fio.cepp.dao.SessaoDAO;
import br.com.fio.cepp.domain.Paciente;
import br.com.fio.cepp.domain.Sessao;
import br.com.fio.cepp.domain.enumeracao.PresencaStatus;
import br.com.fio.cepp.service.NegocioException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ManagedBean(name = "MBHistorico")
@ViewScoped
public class HistoricoBean implements Serializable {

	private static final long serialVersionUID = -1491364411935644220L;
	
	private List<Paciente> pacientes = null;
	private Paciente paciente = null;
	private List<Sessao> litSessao = null;
	private PacienteDAO pacienteDAO = null;
	private SessaoDAO sessaoDAO = null;
	
	private Number presencas = 0;
	private Number faltasJ = 0;
	private Number faltasNJ = 0;
	
	@PostConstruct
	public void init() {
		//sessao = new Sessao();
		pacienteDAO = new PacienteDAO();
		sessaoDAO = new SessaoDAO();
		
		listarPacientes();
	}
	
	public void listarPacientes() {
		try {
			pacientes = new PacienteDAO().listar();
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
	
	public void onRowSelect() {
		try {
			this.paciente = pacienteDAO.buscar(paciente.getCodigo());
			this.litSessao = sessaoDAO.listar(this.paciente.getCodigo());
			
			this.presencas = sessaoDAO.getPresencaStatus(PresencaStatus.P, this.paciente.getCodigo());	
			this.faltasJ = sessaoDAO.getPresencaStatus(PresencaStatus.FJ, this.paciente.getCodigo());	
			this.faltasNJ = sessaoDAO.getPresencaStatus(PresencaStatus.FN, this.paciente.getCodigo());
			
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
	
}
