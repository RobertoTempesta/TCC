package br.com.fio.cepp.bean;

import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_NERGOCIO;
import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_SALVO_SUCESSO;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.fio.cepp.dao.AlunoDAO;
import br.com.fio.cepp.dao.PacienteDAO;
import br.com.fio.cepp.dao.SalaAtendimentoDAO;
import br.com.fio.cepp.domain.Aluno;
import br.com.fio.cepp.domain.Paciente;
import br.com.fio.cepp.domain.SalaAtendimento;
import br.com.fio.cepp.domain.Sessao;
import br.com.fio.cepp.domain.service.SessaoService;
import br.com.fio.cepp.service.NegocioException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ManagedBean(name = "MBAgendamento")
@ViewScoped
public class AgendamentoBean implements Serializable {

	private static final long serialVersionUID = -3544815351958277251L;
	
	private List<Paciente> pacientes = null;
	private List<SalaAtendimento> salasAtendimento = null;
	private List<Aluno> alunos = null;
	private Sessao sessao = null;
	private boolean aguardando = true;
	private SessaoService sessaoService = null;
	
	@PostConstruct
	public void init() {
		sessao = new Sessao();
		sessaoService = new SessaoService();
		
		listarPacientes();
	}
	
	public void listarPacientes() {
		try {
			pacientes = new PacienteDAO().listar(aguardando);
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
	
	public void onRowSelect() {
		try {
			salasAtendimento = new SalaAtendimentoDAO().listar();
			alunos = new AlunoDAO().listar();
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
