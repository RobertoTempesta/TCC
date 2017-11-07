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
import com.roberto.tcc.clinica.domain.Aluno;
import com.roberto.tcc.clinica.util.HibernateUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@SuppressWarnings("serial")
@ManagedBean(name = "MBAluno")
@ViewScoped
public class AlunoBean implements Serializable {

	private Aluno aluno = null;
	private List<Aluno> alunos = null;

	private String opcao = null;

	@PostConstruct
	public void init() {
		aluno = new Aluno();
		listarAlunos();
	}

	public void excluir(ActionEvent evento) {
		try {
			Aluno aluno = (Aluno) evento.getComponent().getAttributes().get("alunoSelecionado");
			AlunoDAO aDAO = new AlunoDAO();
			aDAO.excluir(aluno);
			alunos = aDAO.listar();
			Messages.addGlobalInfo("Aluno excluido com sucesso");
		} catch (RuntimeException erro) {
			LogManager.getLogger(AlunoBean.class).log(Level.ERROR, "Erro ao deletar o aluno: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Aluno");
		}
	}

	public void listarAlunos() {
		try {
			alunos = new AlunoDAO().listar();
		} catch (RuntimeException erro) {
			LogManager.getLogger(AlunoBean.class).log(Level.ERROR, "Erro ao listar os alunos: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Alunos");
		}
	}

	public void detalhesAluno(ActionEvent evento) {
		try {
			this.aluno = (Aluno) evento.getComponent().getAttributes().get("alunoSelecionado");

			RequestContext.getCurrentInstance().execute("PF('dlgDetalhes').show();");
		} catch (RuntimeException erro) {
			LogManager.getLogger(AlunoBean.class).log(Level.ERROR, "Erro ao carregar os alunos: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar os detalhes do Aluno");
		}
	}

	public void imprimir() {
		InputStream inputStream = null;
		ByteArrayOutputStream relatorio = null;
		try {

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("LOGO", Faces.getRealPath("/resources/imagens/logo_pequeno.png"));
			if (opcao == null || opcao.equals("") || opcao.equals("TODOS")) {
				inputStream = Faces.getResourceAsStream("/relatorios/aluno/relacao_alunos.jasper");
			} else {
				inputStream = Faces.getResourceAsStream("/relatorios/aluno/relacao_alunos_pacientes.jasper");
				parametros.put("CAMINHO_SUB_RELATORIO",
						Faces.getResourceAsStream("/relatorios/aluno/pacientes.jasper"));
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
			response.setHeader("Content-disposition", "inline; relacao_alunos.pdf");
			response.getOutputStream().write(relatorio.toByteArray());
			response.getOutputStream().flush();
			response.getOutputStream().close();

			Faces.responseComplete();

		} catch (JRException | IOException erro) {
			LogManager.getLogger(AlunoBean.class).log(Level.ERROR, "Ocorreu um erro ao tentar gerar o relatório:",
					erro);
		}
	}

	public void imprimirAluno(ActionEvent evento) {
		Aluno aluno = (Aluno) evento.getComponent().getAttributes().get("alunoSelecionado");
		InputStream inputStream = null;
		ByteArrayOutputStream relatorio = null;
		try {

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("LOGO", Faces.getRealPath("/resources/imagens/logo_pequeno.png"));
			parametros.put("CODIGO_ALUNO", aluno.getCodigo());
			parametros.put("CAMINHO_SUB_RELATORIO", Faces.getResourceAsStream("/relatorios/aluno/sub_aluno_pacientes.jasper"));
			
			inputStream = Faces.getResourceAsStream("/relatorios/aluno/aluno_pacientes.jasper");

			Connection conexao = HibernateUtil.getConexao();

			relatorio = new ByteArrayOutputStream();

			JasperReport jasper = (JasperReport) JRLoader.loadObject(inputStream);
			JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conexao);
			JasperExportManager.exportReportToPdfStream(print, relatorio);

			HttpServletResponse response = Faces.getResponse();
			response.reset();
			response.setContentType("application/pdf");
			response.setContentLength(relatorio.size());
			response.setHeader("Content-disposition", "inline; relacao_alunos.pdf");
			response.getOutputStream().write(relatorio.toByteArray());
			response.getOutputStream().flush();
			response.getOutputStream().close();

			Faces.responseComplete();

		} catch (JRException | IOException erro) {
			LogManager.getLogger(AlunoBean.class).log(Level.ERROR, "Ocorreu um erro ao tentar gerar o relatório:",
					erro);
		}
	}

	public String getOpcao() {
		return opcao;
	}

	public void setOpcao(String opcao) {
		this.opcao = opcao;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

}
