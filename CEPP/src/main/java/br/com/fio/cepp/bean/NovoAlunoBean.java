package br.com.fio.cepp.bean;

import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_NERGOCIO;
import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_SALVO_SUCESSO;
import static br.com.fio.cepp.util.RedirectUtil.getKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.fio.cepp.dao.AlunoDAO;
import br.com.fio.cepp.dao.EstadoDAO;
import br.com.fio.cepp.dao.FormacaoAcademicaDAO;
import br.com.fio.cepp.dao.FuncaoDAO;
import br.com.fio.cepp.dao.SupervisorDAO;
import br.com.fio.cepp.dao.TelefoneDAO;
import br.com.fio.cepp.domain.Aluno;
import br.com.fio.cepp.domain.Endereco;
import br.com.fio.cepp.domain.Estado;
import br.com.fio.cepp.domain.FormacaoAcademica;
import br.com.fio.cepp.domain.Funcao;
import br.com.fio.cepp.domain.Pessoa;
import br.com.fio.cepp.domain.Supervisor;
import br.com.fio.cepp.domain.Telefone;
import br.com.fio.cepp.domain.enumeracao.Sexo;
import br.com.fio.cepp.domain.service.AlunoService;
import br.com.fio.cepp.service.NegocioException;
import br.com.fio.cepp.util.NegociosUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
@ManagedBean(name = "MBNovoAluno")
@ViewScoped
public class NovoAlunoBean implements Serializable {

	private List<Estado> estados = null;
	private List<Funcao> funcoes = null;
	private List<Supervisor> supervisores = null;
	private Aluno aluno = null;
	private List<Telefone> telefones = null;
	private Telefone telefone = null;
	private List<FormacaoAcademica> listFormacaoAcademica = null;
	private Sexo[] sexos = null;
	private AlunoService alunoService = null;
	
	private static String TELEFONE_SELEC = "telefoneSelecionado";
	private static String ALUNO_COD = "alunoCodigo";

	@PostConstruct
	public void init() {
		loadTela();
		
		getLoadEdicao();
		
		sexos = Sexo.values();
		telefone = new Telefone();
		alunoService = new AlunoService();
		
	}

	public void loadTela() {
		try {
			estados = new EstadoDAO().listar();
			funcoes = new FuncaoDAO().listar();
			supervisores = new SupervisorDAO().listar();
			listFormacaoAcademica = new FormacaoAcademicaDAO().listar();
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
	
	private void getLoadEdicao() {
		Optional<Long> codigo = getKey(ALUNO_COD);
		if(codigo.isPresent()) {
			try {
				aluno = new AlunoDAO().buscar(codigo.get());
				telefones = new TelefoneDAO().buscarPorPessoaCod(aluno.getPessoa().getCodigo());
			} catch (RuntimeException erro) {
				throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
			}
			
		} else {
			aluno = new Aluno();
			aluno.setPessoa(new Pessoa());
			aluno.getPessoa().setEndereco(new Endereco());
			aluno.getPessoa().getEndereco().setEstado(new Estado());
			aluno.setDataCadastro(new Date());
			
			telefones = new ArrayList<>();
		}
	}

	public void salvar() {
		this.aluno.getPessoa().setTelefones(this.telefones);
		alunoService.salvar(this.aluno);
		init();
		Messages.addGlobalInfo(MSG_SALVO_SUCESSO.getMsg());
	}
	
	public void calculaIdade() {
		this.aluno.getPessoa().setIdade(NegociosUtil.calculaIdade(aluno.getPessoa().getDataNascimento()));
	}
	
	public void carregarCEP() {
		this.aluno.getPessoa().setEndereco(NegociosUtil.carregarCEP(this.aluno.getPessoa().getEndereco().getCep()));
	}
	
	public void adicionaTelefone() {
		if (!this.telefones.contains(this.telefone)) {
			this.telefones.add(this.telefone);
		}
		
		this.telefone = new Telefone();
	}
	
	public void removerTelefone(ActionEvent evento) {
		Telefone telefone = (Telefone) evento.getComponent().getAttributes().get(TELEFONE_SELEC);
		this.telefones.remove(telefone);
	}

}
