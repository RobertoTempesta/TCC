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
import br.com.fio.cepp.dao.PacienteDAO;
import br.com.fio.cepp.dao.TelefoneDAO;
import br.com.fio.cepp.domain.Endereco;
import br.com.fio.cepp.domain.Estado;
import br.com.fio.cepp.domain.FormacaoAcademica;
import br.com.fio.cepp.domain.Paciente;
import br.com.fio.cepp.domain.Pessoa;
import br.com.fio.cepp.domain.Telefone;
import br.com.fio.cepp.domain.enumeracao.EstadoCivil;
import br.com.fio.cepp.domain.enumeracao.Sexo;
import br.com.fio.cepp.domain.enumeracao.Situacao;
import br.com.fio.cepp.domain.service.PacienteService;
import br.com.fio.cepp.service.NegocioException;
import br.com.fio.cepp.util.NegociosUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
@ManagedBean(name = "MBNovoPaciente")
@ViewScoped
public class NovoPacienteBean implements Serializable {

	private List<Estado> estados = null;
	private List<Telefone> telefones = null;
	private Telefone telefone = null;
	private Sexo[] sexos = null;
	private EstadoCivil[] estadoCivil = null;
	private Paciente paciente = null;
	private List<FormacaoAcademica> listFormacaoAcademica = null;
	private PacienteService pacienteService = null;
	
	private static String TELEFONE_SELEC = "telefoneSelecionado";
	private static String PACIENTE_COD = "codigoPaciente";

	@PostConstruct
	public void init() {
		
		loadTela();
		
		sexos = Sexo.values();
		estadoCivil = EstadoCivil.values();
		telefone = new Telefone();
		pacienteService = new PacienteService();
		
		getLoadEdicao();
	}

	private void getLoadEdicao() {
		Optional<Long> codigo = getKey(PACIENTE_COD);
		if(codigo.isPresent()) {
			try {
				paciente = new PacienteDAO().buscar(codigo.get());
				telefones = new TelefoneDAO().buscarPorPessoaCod(paciente.getPessoa().getCodigo());
			} catch (RuntimeException erro) {
				throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
			}
			
		} else {
			paciente = new Paciente();
			paciente.setPessoa(new Pessoa());
			paciente.getPessoa().setEndereco(new Endereco());
			paciente.getPessoa().getEndereco().setEstado(new Estado());
			paciente.setDataCadastro(new Date());
			paciente.setSituacao(Situacao.A);
			
			telefones = new ArrayList<>();
		}
	}

	public void loadTela() {
		try {
			estados = new EstadoDAO().listar();
			listFormacaoAcademica = new FormacaoAcademicaDAO().listar();
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}

	public void salvar() {
		paciente.getPessoa().setTelefones(telefones);
		pacienteService.salvar(this.paciente);
		init();
		Messages.addGlobalInfo(MSG_SALVO_SUCESSO.getMsg());
	}

	public void calculaIdade() {
		this.paciente.getPessoa().setIdade(NegociosUtil.calculaIdade(paciente.getPessoa().getDataNascimento()));
	}

	public void carregarCEP() {
		this.paciente.getPessoa().setEndereco(NegociosUtil.carregarCEP(this.paciente.getPessoa().getEndereco().getCep()));
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
