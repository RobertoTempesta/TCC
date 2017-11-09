package com.roberto.tcc.clinica.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import com.roberto.tcc.clinica.dao.AlunoDAO;
import com.roberto.tcc.clinica.dao.PacienteDAO;
import com.roberto.tcc.clinica.dao.SalaAtendimentoDAO;
import com.roberto.tcc.clinica.dao.SessaoDAO;
import com.roberto.tcc.clinica.domain.Aluno;
import com.roberto.tcc.clinica.domain.Estado;
import com.roberto.tcc.clinica.domain.Paciente;
import com.roberto.tcc.clinica.domain.SalaAtendimento;
import com.roberto.tcc.clinica.domain.Sessao;
import com.roberto.tcc.clinica.enumeracao.Situacao;
import com.roberto.tcc.clinica.util.Constantes;
import com.roberto.tcc.clinica.util.HibernateUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@ManagedBean(name = "MBPaciente")
@ViewScoped
public class PacienteBean implements Serializable {

	private static final long serialVersionUID = 3395759380539498382L;

	private Paciente paciente = null;
	private Sessao sessao = null;
	private int anoCorrente = Constantes.ANO_CORRENTE;

	private List<Estado> estados = null;
	private List<Paciente> pacientes = null;
	private List<Aluno> alunos = null;
	private List<SalaAtendimento> salas = null;

	private String situacao = null;

	@PostConstruct
	public void init() {
		paciente = new Paciente();
		sessao = new Sessao();
		sessao.setAluno(new Aluno());
		sessao.setSala(new SalaAtendimento());
		sessao.setPaciente(new Paciente());
		carregarPacientes();
	}

	public void carregarPacientes() {
		try {
			pacientes = new PacienteDAO().listar("codigo");
		} catch (RuntimeException erro) {
			LogManager.getLogger(PacienteBean.class).log(Level.ERROR, "Erro ao listar os Pacientes", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar os pacientes.");
		}
	}

	public void carregarObjetosSessao() {
		try {
			alunos = new AlunoDAO().listar();
			salas = new SalaAtendimentoDAO().listar();
		} catch (RuntimeException erro) {
			LogManager.getLogger(PacienteBean.class).log(Level.ERROR, "Erro ao carregar os alunos e as salas: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao carregar os objetos da Tela!");
		}
	}

	public void capturaPaciente(ActionEvent evento) {
		this.paciente = (Paciente) evento.getComponent().getAttributes().get("pacienteSelecionado");
		carregarObjetosSessao();
	}

	public void salvar() {
		try {
			
			if (paciente == null || paciente.getNumeroCaso().equals("")) {
				Messages.addGlobalError("Informe o Numero do Caso");
				return;
			}

			if (sessao.getSala() == null || sessao.getSala().equals("")) {
				Messages.addGlobalError("É necessário selecionar uma Sala para o Atendimento");
				return;
			}

			if (sessao.getAluno() == null || sessao.getAluno().equals("")) {
				Messages.addGlobalError("É necessário selecionar um Aluno");
				return;
			}
			
			if (paciente.getSituacao().equals(Situacao.AGUARDANDO)) {
				paciente.setSituacao(Situacao.EM_ANDAMENTO);
			}
			
			if (paciente.getNumeroCaso() == null || paciente.getNumeroCaso().equals("")) {
				paciente.setNumeroCaso(paciente.getNumeroCaso() + anoCorrente);
			}
			
			SessaoDAO sessaoDAO = new SessaoDAO();
			if (sessao.getCodigo() == null) {

				List<Sessao> listaSessao = sessaoDAO.verificaPossibilidade(sessao);

				for (Sessao s : listaSessao) {

					if (s.getSala().getCodigo() == sessao.getSala().getCodigo()) {
						Messages.addGlobalError(
								"Não é possivel salvar essa Sessão, verifique se a Sala selecionada já não está em uso!");
						return;
					}
					if (s.getAluno().getCodigo() == sessao.getAluno().getCodigo()) {
						Messages.addGlobalError(
								"Não é possivel salvar essa Sessão, verifique se Aluno selecionada já não está atendendo!");
						return;
					}
				}
			}
			
			sessao.setPaciente(paciente);
			
			sessaoDAO.salvarPrimeiraSessao(sessao);
			carregarPacientes();
			Messages.addGlobalInfo("Sessão salva com Sucesso!");
			RequestContext.getCurrentInstance().execute("PF('dlgSessao').hide();");
		} catch (RuntimeException erro) {
			LogManager.getLogger(PacienteBean.class).log(Level.ERROR, "Erro ao salvar a Sessão: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao Salvar a Sessão");
		}
	}

	public void detalhesPaciente(ActionEvent evento) {
		try {
			this.paciente = (Paciente) evento.getComponent().getAttributes().get("pacienteSelecionado");

			RequestContext.getCurrentInstance().execute("PF('dlgDetalhes').show();");
		} catch (RuntimeException erro) {
			LogManager.getLogger(PacienteBean.class).log(Level.ERROR, "Erro ao selecionar o Paciente: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar detalhes do Paciente!");
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			Paciente paciente = (Paciente) evento.getComponent().getAttributes().get("pacienteSelecionado");
			PacienteDAO pacienteDAO = new PacienteDAO();
			pacienteDAO.excluir(paciente);

			pacientes = pacienteDAO.listar();
			Messages.addGlobalInfo("Paciente deletado com Sucesso");
		} catch (RuntimeException erro) {
			LogManager.getLogger(PacienteBean.class).log(Level.ERROR, "Erro ao excluir Paciente: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar deletar o Paciente!");
		}
	}

	public String cssCampo(Paciente paciente) {

		if (paciente.getNumeroCaso() == null || paciente.getNumeroCaso().equals("")) {
			return "color: #20B2AA";
		} else if (paciente.getFaltas_injustificadas() >= 4) {
			return "color: red";
		} else {
			return "";
		}

	}

	public void imprimir() {
		InputStream inputStream = null;
		ByteArrayOutputStream relatorio = null;
		try {

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("LOGO", Faces.getRealPath("/resources/imagens/logo_pequeno.png"));
			if (situacao == null || situacao.equals("") || situacao.equals("TODOS")) {
				inputStream = Faces.getResourceAsStream("/relatorios/paciente/relacao_pacientes.jasper");
			} else {
				parametros.put("SITUACAO", situacao);
				inputStream = Faces.getResourceAsStream("/relatorios/paciente/relacao_pacientes_situacao.jasper");
			}

			Connection conexao = HibernateUtil.getConexao();

			relatorio = new ByteArrayOutputStream();

			JasperReport jasper = (JasperReport) JRLoader.loadObject(inputStream);
			JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conexao);
			JasperExportManager.exportReportToPdfStream(print, relatorio);

			HttpServletResponse response = Faces.getResponse();
			response.reset();
			response.setContentType("application/pdf");
			response.setContentLength(relatorio.size());
			response.setHeader("Content-disposition", "inline; relacao_pacientes.pdf");
			response.getOutputStream().write(relatorio.toByteArray());
			response.getOutputStream().flush();
			response.getOutputStream().close();

			Faces.responseComplete();

		} catch (JRException | IOException erro) {
			LogManager.getLogger(PacienteBean.class).log(Level.ERROR, "Ocorreu um erro ao tentar gerar o relatório:",
					erro);
		}
	}
	
	public Number numeroPacientes() {
		return new PacienteDAO().buscaNumeroPacientesAguardando();
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public List<SalaAtendimento> getSalas() {
		return salas;
	}

	public void setSalas(List<SalaAtendimento> salas) {
		this.salas = salas;
	}

	public int getAnoCorrente() {
		return anoCorrente;
	}

	public void setAnoCorrente(int anoCorrente) {
		this.anoCorrente = anoCorrente;
	}

}
