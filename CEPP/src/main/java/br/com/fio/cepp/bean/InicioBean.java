package br.com.fio.cepp.bean;

import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_NERGOCIO;
import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_SALVO_SUCESSO;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import br.com.fio.cepp.dao.AlunoDAO;
import br.com.fio.cepp.dao.PacienteDAO;
import br.com.fio.cepp.dao.SalaAtendimentoDAO;
import br.com.fio.cepp.dao.SessaoDAO;
import br.com.fio.cepp.domain.Aluno;
import br.com.fio.cepp.domain.SalaAtendimento;
import br.com.fio.cepp.domain.Sessao;
import br.com.fio.cepp.domain.enumeracao.PresencaStatus;
import br.com.fio.cepp.domain.service.SessaoService;
import br.com.fio.cepp.enumeracao.MensagensEnum;
import br.com.fio.cepp.service.NegocioException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
@ManagedBean(name = "MBInicio")
@ViewScoped
public class InicioBean implements Serializable {

	private ScheduleModel scheduleSessoes;
	private Number nPacientes = 0;
	private Number nEmEspera = 0;
	private Number nAlunos = 0;
	private Number nSessoes = 0;
	private SessaoDAO sessaoDAO = new SessaoDAO();
	private List<SalaAtendimento> salasAtendimento = null;
	private List<Aluno> alunos = null;
	private AlunoDAO alunoDAO = null;
	private Sessao sessao = null;
	private PresencaStatus[] listPresenStatus = null;
	private SessaoService sessaoService = null;
	
	@PostConstruct
	public void init() {
		scheduleSessoes = new DefaultScheduleModel();
		sessaoDAO = new SessaoDAO();
		alunoDAO = new AlunoDAO();
		listPresenStatus = PresencaStatus.values();
		sessaoService = new SessaoService();
		carregarInfo();
		
		try {

			List<Sessao> sessoes = sessaoDAO.listar();

			for (Sessao s : sessoes) {

				DefaultScheduleEvent evento = new DefaultScheduleEvent();
				evento.setEndDate(s.getDataFim());
				evento.setStartDate(s.getDataInicio());
				evento.setData(s.getCodigo());
				evento.setTitle(s.getPaciente().getPessoa().getNome());
				evento.setDescription(s.getPaciente().getNumeroCaso());
				evento.setAllDay(false);
				evento.setEditable(false);

				scheduleSessoes.addEvent(evento);

			}

		} catch (RuntimeException erro) {
			throw new NegocioException(MensagensEnum.MSG_ERRO_NERGOCIO.getMsg());
		}
	}
	
	public void carregarInfo() {
		PacienteDAO pacienteDAO = new PacienteDAO();
		
		try {
			nPacientes = pacienteDAO.getNumeroPacientes();
			nEmEspera = pacienteDAO.getNumeroPacientesAguardando();
			nAlunos = alunoDAO.getNumeroAlunos();
			nSessoes = sessaoDAO.getNumeroSessoesAtendidas();
		} catch (RuntimeException erro) {
			throw new NegocioException(MensagensEnum.MSG_ERRO_NERGOCIO.getMsg());
		}	
	}
	
	public void eventSelect(SelectEvent selectEvent) {
		ScheduleEvent evento = (ScheduleEvent) selectEvent.getObject();
				
		try {
			salasAtendimento = new SalaAtendimentoDAO().listar();
			alunos = new AlunoDAO().listar();
			
			sessao = sessaoDAO.buscar((Long) evento.getData());
			
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
	
	public void salvar() {
		sessaoService.salvar(sessao);
		init();
		Messages.addGlobalInfo(MSG_SALVO_SUCESSO.getMsg());
	}
}
