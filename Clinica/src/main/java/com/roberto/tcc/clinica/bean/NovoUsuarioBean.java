package com.roberto.tcc.clinica.bean;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import com.roberto.tcc.clinica.dao.EstadoDAO;
import com.roberto.tcc.clinica.dao.PessoaDAO;
import com.roberto.tcc.clinica.dao.UsuarioDAO;
import com.roberto.tcc.clinica.domain.Endereco;
import com.roberto.tcc.clinica.domain.Estado;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.domain.Usuario;
import com.roberto.tcc.clinica.security.Criptografia;
import com.roberto.tcc.clinica.util.CEPUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBNovoUsuario")
@ViewScoped
public class NovoUsuarioBean implements Serializable {

	private List<Estado> estados = null;

	private Usuario usuario = null;

	@PostConstruct
	public void init() {
		usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
		usuario.getPessoa().setEndereco(new Endereco());
		usuario.getPessoa().getEndereco().setEstado(new Estado());

		carregarEstados();
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("usuario");
		if (usuario != null) {
			this.usuario = usuario;
		}
	}

	public void carregarEstados() {
		try {
			estados = new EstadoDAO().listar();
		} catch (RuntimeException erro) {
			LogManager.getLogger(NovoPacienteBean.class).log(Level.ERROR, "Erro ao listar os estados: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao listar os Estados");
		}
	}

	public void editar(ActionEvent evento) {
		Usuario usuario =  (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("usuario", usuario);
	}

	public void calculaIdade() {
		GregorianCalendar dataAtual = new GregorianCalendar();
		GregorianCalendar nascimento = new GregorianCalendar();
		if (usuario.getPessoa().getDataNascimento() == null) {
			return;
		}
		nascimento.setTime(usuario.getPessoa().getDataNascimento());
		int anohj = dataAtual.get(Calendar.YEAR);
		int anoNascimento = nascimento.get(Calendar.YEAR);
		this.usuario.getPessoa().setIdade(new Integer(anohj - anoNascimento));
	}

	public void salvar() {
		try {

			this.usuario = Criptografia.gerarSenhaCrip(this.usuario, this.usuario.getSenha());

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.salvarPessoa(this.usuario);
			RequestContext.getCurrentInstance().execute("PF('dlgConfirma').show();");
		} catch (RuntimeException | NoSuchAlgorithmException | UnsupportedEncodingException erro) {
			LogManager.getLogger(NovoUsuarioBean.class).log(Level.ERROR, "Erro ao salvar o Usuario: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Usuário");
		}
	}

	public void verificaCPF() {

		try {
			String cpf = usuario.getPessoa().getCPF();
			cpf = cpf.replace(".", "");
			cpf = cpf.replace("-", "");
			cpf = cpf.replace("_", "");
			if (cpf == null || cpf.equals("")) {
				Messages.addGlobalWarn("Informe um CPF!");
				return;
			}

			Pessoa pessoa = new PessoaDAO().buscarCPF(this.usuario.getPessoa().getCPF());
			if (pessoa != null) {
				Usuario user = new UsuarioDAO().buscarCodigoPes(pessoa.getCodigo());
				if (user != null && this.usuario.getCodigo() == null) {
					Messages.addGlobalWarn("Essa Pessoa já é um Usuário do Sistema!");
					this.usuario.setPessoa(new Pessoa());
					return;
				}
				this.usuario.setPessoa(pessoa);
			}

		} catch (RuntimeException erro) {
			LogManager.getLogger(NovoUsuarioBean.class).log(Level.ERROR, "Erro ao carregar o CPF: ", erro);
			Messages.addGlobalError("Ocorreu um erro interno ao verificar o CPF informado!");
		}

	}

	public void carregarCEP() {

		String cep = usuario.getPessoa().getEndereco().getCEP();
		cep = cep.replace(".", "");
		cep = cep.replace("-", "");
		cep = cep.replace("_", "");

		if (cep == null || cep.equals("")) {
			Messages.addGlobalWarn("Digite um CEP válido!");
			return;
		}

		try {
			JSONObject json = new CEPUtil().capturaJson(cep);
			boolean erro = json.isNull("erro");

			if (!erro) {
				Messages.addGlobalWarn("CEP informado não existe!");
				return;
			}
			Endereco end = new Endereco();
			end.setCEP(json.getString("cep"));
			end.setBairro(json.getString("bairro"));
			end.setRua(json.getString("logradouro"));

			end.setCidade(json.getString("localidade"));

			end.setEstado(new EstadoDAO().buscarSigla(json.getString("uf")));

			usuario.getPessoa().setEndereco(end);

		} catch (MalformedURLException erro) {
			LogManager.getLogger(NovoUsuarioBean.class).log(Level.ERROR, "Erro ao carregar o CEP: ", erro);
			Messages.addGlobalError("Ocorreu um erro interno ao buscar o endereço");
		} catch (IOException erro) {
			LogManager.getLogger(NovoUsuarioBean.class).log(Level.ERROR, "Erro ao carregar o CEP: ", erro);
			Messages.addGlobalError("Ocorreu um erro interno ao buscar o endereço");
		}
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
