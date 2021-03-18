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

import br.com.fio.cepp.dao.AlunoDAO;
import br.com.fio.cepp.domain.Aluno;
import br.com.fio.cepp.domain.Funcao;
import br.com.fio.cepp.domain.Pessoa;
import br.com.fio.cepp.domain.Supervisor;
import br.com.fio.cepp.service.NegocioException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
@ManagedBean(name = "MBAluno")
@ViewScoped
public class AlunoBean implements Serializable {

	private Aluno aluno = null;
	private List<Aluno> alunos = null;
	private AlunoDAO alunoDAO = null;

	private String opcao = null;
	private static String ALUNO_SELC = "alunoSelecionado";
	private static String ALUNO_COD = "alunoCodigo";
	private static String NOVO_ALUNO = "/paginas/privado/novo_aluno.xhtml"; 

	@PostConstruct
	public void init() {
		aluno = new Aluno();
		aluno.setPessoa(new Pessoa());
		aluno.setSupervisor(new Supervisor());
		aluno.setFuncao(new Funcao());
		
		alunoDAO = new AlunoDAO();
		
		listar();
	}

	public void excluir(ActionEvent evento) {
		try {
			Aluno aluno = (Aluno) evento.getComponent().getAttributes().get(ALUNO_SELC);
			alunoDAO.excluir(aluno);
			listar();
			Messages.addGlobalInfo(MSG_EXCLUIDO_SUCESSO.getMsg());
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}

	public void listar() {
		try {
			alunos = alunoDAO.listar();
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
	
	public void editar(ActionEvent evento) {
		Aluno aluno = (Aluno) evento.getComponent().getAttributes().get(ALUNO_SELC);
		redirect(NOVO_ALUNO, ALUNO_COD, aluno.getCodigo());

	}

	public void detalhesAluno(ActionEvent evento) {
		try {
			this.aluno = (Aluno) evento.getComponent().getAttributes().get(ALUNO_SELC);
			RequestContext.getCurrentInstance().execute("PF('dlgDetalhes').show();");
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
}
