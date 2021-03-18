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

import br.com.fio.cepp.dao.EstadoDAO;
import br.com.fio.cepp.dao.FormacaoAcademicaDAO;
import br.com.fio.cepp.dao.SupervisorDAO;
import br.com.fio.cepp.dao.TelefoneDAO;
import br.com.fio.cepp.domain.Endereco;
import br.com.fio.cepp.domain.Estado;
import br.com.fio.cepp.domain.FormacaoAcademica;
import br.com.fio.cepp.domain.Pessoa;
import br.com.fio.cepp.domain.Supervisor;
import br.com.fio.cepp.domain.Telefone;
import br.com.fio.cepp.domain.enumeracao.Sexo;
import br.com.fio.cepp.domain.service.SupervisorService;
import br.com.fio.cepp.service.NegocioException;
import br.com.fio.cepp.util.NegociosUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
@ManagedBean(name = "MBNovoSupervisor")
@ViewScoped
public class NovoSupervisorBean implements Serializable {

	private List<Estado> estados = null;
	private List<FormacaoAcademica> listFormacaoAcademica = null;
	private Sexo[] sexos = null;
	private Supervisor supervisor = null;
	private List<Telefone> telefones = null;
	private Telefone telefone = null;
	private SupervisorService supervisorService = null;
	
	private static String TELEFONE_SELEC = "telefoneSelecionado";
	private static String SEPERVISOR_COD = "codigoSupervisor";

	@PostConstruct
	public void init() {
		loadTela();
		
		getLoadEdicao();
		
		sexos = Sexo.values();
		telefone = new Telefone();
		supervisorService = new SupervisorService();
	}

	public void loadTela() {
		try {
			estados = new EstadoDAO().listar();
			listFormacaoAcademica = new FormacaoAcademicaDAO().listar();
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
	
	private void getLoadEdicao() {
		Optional<Long> codigo = getKey(SEPERVISOR_COD);
		if(codigo.isPresent()) {
			try {
				supervisor = new SupervisorDAO().buscar(codigo.get());
				telefones = new TelefoneDAO().buscarPorPessoaCod(supervisor.getPessoa().getCodigo());
			} catch (RuntimeException erro) {
				throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
			}
			
		} else {
			supervisor = new Supervisor();
			supervisor.setPessoa(new Pessoa());
			supervisor.getPessoa().setEndereco(new Endereco());
			supervisor.getPessoa().getEndereco().setEstado(new Estado());
			supervisor.setDataCadastro(new Date());
			
			telefones = new ArrayList<>();
		}
	}
	
	public void salvar() {
		this.supervisor.getPessoa().setTelefones(this.telefones);
		supervisorService.salvar(this.supervisor);
		init();
		Messages.addGlobalInfo(MSG_SALVO_SUCESSO.getMsg());
	}

	public void calculaIdade() {
		this.supervisor.getPessoa().setIdade(NegociosUtil.calculaIdade(supervisor.getPessoa().getDataNascimento()));
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

	public void carregarCEP() {
		this.supervisor.getPessoa().setEndereco(NegociosUtil.carregarCEP(this.supervisor.getPessoa().getEndereco().getCep()));
	}

}
