package br.com.fio.cepp.bean;

import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_NERGOCIO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.omnifaces.util.Faces;

import br.com.fio.cepp.domain.Aluno;
import br.com.fio.cepp.enumeracao.MensagensEnum;
import br.com.fio.cepp.service.NegocioException;
import br.com.fio.cepp.util.HibernateUtil;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Getter
@Setter
@ManagedBean(name = "MBRelatorios")
@ViewScoped
public class RelatoriosBean implements Serializable {

	private static final long serialVersionUID = 7958592051834493832L;
	
	private void imprimirNovo(String nomeRelatorio) {
		InputStream inputStream = null;
		ByteArrayOutputStream relatorio = null;
		try {

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("LOGO", Faces.getRealPath("/resources/ultima-layout/images/logo-clinica.png"));
			
			/**if (situacao == null || situacao.equals("") || situacao.equals("TODOS")) {
				inputStream = Faces.getResourceAsStream("/relatorios/paciente/relacao_pacientes.jasper");
			} else {
				parametros.put("SITUACAO", situacao);
				inputStream = Faces.getResourceAsStream("/relatorios/paciente/relacao_pacientes_situacao.jasper");
			}*/

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
			throw new NegocioException(MensagensEnum.MSG_ERRO_NERGOCIO.getMsg());
		}
	}

	public void imprimirPacientes() {
		InputStream inputStream = null;
		ByteArrayOutputStream relatorio = null;
		try {

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("LOGO", Faces.getRealPath("/resources/imagens/logo_pequeno.png"));
			
			/**if (situacao == null || situacao.equals("") || situacao.equals("TODOS")) {
				inputStream = Faces.getResourceAsStream("/relatorios/paciente/relacao_pacientes.jasper");
			} else {
				parametros.put("SITUACAO", situacao);
				inputStream = Faces.getResourceAsStream("/relatorios/paciente/relacao_pacientes_situacao.jasper");
			}*/

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

		}
	}
	
	public void imprimir() {
		InputStream inputStream = null;
		ByteArrayOutputStream relatorio = null;
		try {

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("LOGO", Faces.getRealPath("/resources/imagens/logo_pequeno.png"));
			/**if (opcao == null || opcao.equals("") || opcao.equals("TODOS")) {
				inputStream = Faces.getResourceAsStream("/relatorios/aluno/relacao_alunos.jasper");
			} else {
				inputStream = Faces.getResourceAsStream("/relatorios/aluno/relacao_alunos_pacientes.jasper");
				parametros.put("CAMINHO_SUB_RELATORIO", Faces.getRealPath("/relatorios/aluno/pacientes.jasper"));
			}*/

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
			parametros.put("CAMINHO_SUB_RELATORIO", Faces.getRealPath("/relatorios/aluno/sub_aluno_pacientes.jasper"));

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
			
		}
	}
}
