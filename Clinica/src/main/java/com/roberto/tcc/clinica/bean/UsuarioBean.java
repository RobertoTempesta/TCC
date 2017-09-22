package com.roberto.tcc.clinica.bean;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.omnifaces.util.Messages;

import com.roberto.tcc.clinica.dao.CidadeDAO;
import com.roberto.tcc.clinica.dao.EstadoDAO;
import com.roberto.tcc.clinica.dao.PessoaDAO;
import com.roberto.tcc.clinica.dao.UsuarioDAO;
import com.roberto.tcc.clinica.domain.Cidade;
import com.roberto.tcc.clinica.domain.Endereco;
import com.roberto.tcc.clinica.domain.Estado;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.domain.Usuario;
import com.roberto.tcc.clinica.security.Criptografia;
import com.roberto.tcc.clinica.util.CEPUtil;

@ManagedBean(name = "MBUsuario")
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = -7249128232412599268L;

	private static final Logger logger = LogManager.getLogger(UsuarioBean.class);

	private Usuario usuario = null;
	private Pessoa pessoa = null;
	private Endereco endereco = null;
	private Cidade cidade = null;
	private Estado estado = null;

	private List<Estado> estados = null;
	private List<Usuario> usuarios = null;

	private UsuarioDAO usuarioDAO = null;
	private PessoaDAO pessoaDAO = null;
	private CidadeDAO cidadeDAO = null;
	private EstadoDAO estadoDAO = null;

	public void iniciarDomain() {
		usuario = new Usuario();
		pessoa = new Pessoa();
		endereco = new Endereco();
		cidade = new Cidade();
		estado = new Estado();
	}

	public void iniciarDAO() {
		usuarioDAO = new UsuarioDAO();
		pessoaDAO = new PessoaDAO();
		cidadeDAO = new CidadeDAO();
		estadoDAO = new EstadoDAO();
	}

	public void inicial() {
		iniciarDAO();
		iniciarDomain();
	}
	
	public void excluir(ActionEvent evento) {
		try {
			Usuario usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
			usuarioDAO.excluir(usuario);

			usuarios = usuarioDAO.listar();
			Messages.addGlobalInfo("Usuário deletado com Sucesso");
		} catch (RuntimeException erro) {
			logger.error("Erro ao excluir usuário: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar deletar o Usuário");
		}
	}
	
	public void listarUsuarios() {
		try {
			usuarios = usuarioDAO.listar();
		}catch(RuntimeException erro) {
			logger.error("Ocorreu um erro ao tentar listar os usuarios: "+erro);
		}
	}
	
	public void editar(ActionEvent evento) {
		try {
			Usuario usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("codigo",
					usuario.getCodigo());

			telaNovoUsuario();

		} catch (Exception erro) {
			logger.error("Erro ao direcionar pagina: " + erro);
		}
	}
	
	public void iniciaEdicao() {
		Long codigo = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("codigo");
		if (codigo != null) {
			this.usuario = usuarioDAO.buscar(codigo);
			this.pessoa = usuario.getPessoa();
			this.endereco = pessoa.getEndereco();
			this.cidade = endereco.getCidade();
			this.estado = cidade.getEstado();
		}

	}

	public void salvar() {
		try {
			this.cidade.setEstado(this.estado);
			this.endereco.setCidade(this.cidade);
			this.pessoa.setEndereco(this.endereco);
			this.pessoa = pessoaDAO.salvarPesEndereco(this.pessoa);
			
			this.usuario = Criptografia.gerarSenhaCrip(this.usuario, this.usuario.getSenha());
			this.usuario.setPessoa(this.pessoa);
			
			usuarioDAO.merge(this.usuario);
			Messages.addGlobalInfo("Usuario salvo com sucesso");
			telaInicial();
		} catch (RuntimeException erro) {
			logger.error("Ocorreu um erro ao tentar salvar o usuario: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Usuário");
		}
	}

	public void verificaCPF() {

		try {
			String cpf = pessoa.getCPF();
			cpf = cpf.replace(".", "");
			cpf = cpf.replace("-", "");
			cpf = cpf.replace("_", "");
			if (cpf == null || cpf.equals("")) {
				Messages.addGlobalWarn("Informe um CPF!");
				return;
			}

			Pessoa pessoa = pessoaDAO.buscarCPF(this.pessoa.getCPF());
			if (pessoa != null) {
				Usuario user = usuarioDAO.buscarCodigoPes(pessoa.getCodigo());
				if(user != null && this.usuario.getCodigo() == null) {
					Messages.addGlobalWarn("Essa Pessoa já é um Usuário do Sistema!");
					this.pessoa = new Pessoa();
					return;
				}
				this.pessoa = pessoa;
				this.endereco = pessoa.getEndereco();
				this.cidade = this.endereco.getCidade();
				this.estado = this.cidade.getEstado();
			}

		} catch (RuntimeException erro) {
			logger.error("Erro ao verificar CPF: " + erro);
			Messages.addGlobalError("Ocorreu um erro interno ao verificar o CPF informado!");
		}

	}

	public void carregarCEP() {

		String cep = endereco.getCEP();
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

			endereco.setCEP(json.getString("cep"));
			endereco.setBairro(json.getString("bairro"));
			endereco.setRua(json.getString("logradouro"));

			String nomeCidade = json.getString("localidade");
			cidade = cidadeDAO.buscarNome(nomeCidade);
			if (cidade == null) {
				cidade = new Cidade();
				cidade.setNome(nomeCidade);
			}
			estado = estadoDAO.buscarSigla(json.getString("uf"));

		} catch (MalformedURLException erro) {
			logger.error("Erro ao buscar o CEP: " + erro);
			Messages.addGlobalError("Ocorreu um erro interno ao buscar o endereço");
		} catch (IOException erro) {
			logger.error("Erro ao buscar o CEP: " + erro);
			Messages.addGlobalError("Ocorreu um erro interno ao buscar o endereço");
		}
	}

	public void carregarEstados() {
		try {
			estados = estadoDAO.listar();
		} catch (RuntimeException erro) {
			logger.error("Erro ao listar os estados: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao listar os Estados");
		}
	}

	public void telaNovoUsuario() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("novo_usuario.xhtml");
		} catch (IOException erro) {
			logger.error("Ocorreu um erro ao tentar redirecionar a tela[novo_usuario]: " + erro);
		}
	}

	public void telaInicial() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("usuario.xhtml");
		} catch (IOException erro) {
			logger.error("Ocorreu um erro ao tentar redirecionar a tela[usuarios] " + erro);
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
