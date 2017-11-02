package com.roberto.tcc.clinica.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.omnifaces.util.Messages;

import com.roberto.tcc.clinica.dao.AlunoDAO;
import com.roberto.tcc.clinica.dao.PacienteDAO;
import com.roberto.tcc.clinica.dao.SessaoDAO;
import com.roberto.tcc.clinica.domain.Sessao;

@SuppressWarnings("serial")
@ManagedBean(name = "MBInicio")
@ViewScoped
public class InicioBean implements Serializable {

	private List<Sessao> sessoesDia = null;
	
	private Date periodo = null;
	private Date periodo2 = null;

	@PostConstruct
	public void init() {
		try {
			
			periodo = new Date();
			periodo2 = new Date();
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(periodo);
			calendar.set(Calendar.AM_PM, Calendar.AM);
			calendar.set(Calendar.HOUR, 1);
			periodo = calendar.getTime();
			
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(periodo2);
			calendar2.set(Calendar.AM_PM, Calendar.PM);
			calendar2.add(Calendar.DATE, +7);
			calendar2.set(Calendar.HOUR, 11);
			periodo2 = calendar2.getTime();
			
			sessoesDia = new SessaoDAO().listar(periodo, periodo2);
		} catch (RuntimeException erro) {
			LogManager.getLogger(InicioBean.class).log(Level.ERROR, "Erro ao listar as sessões do dia: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao listar as Sessões do dia!");
		}
	}
	
	public Number numeroPacientes() {
		return new PacienteDAO().buscaNumeroPacientes();
	}
	
	public Number numeroAlunos() {
		return new AlunoDAO().buscaNumeroAlunos();
	}
	
	public Number numeroSessoes() {
		return new SessaoDAO().buscaNumeroSessoes();
	}

	public List<Sessao> getSessoesDia() {
		return sessoesDia;
	}

	public void setSessoesDia(List<Sessao> sessoesDia) {
		this.sessoesDia = sessoesDia;
	}

	public String getPeriodo() {
		return new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR")).format(periodo);
	}

	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}

	public String getPeriodo2() {
		return new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR")).format(periodo2);
	}

	public void setPeriodo2(Date periodo2) {
		this.periodo2 = periodo2;
	}

}
