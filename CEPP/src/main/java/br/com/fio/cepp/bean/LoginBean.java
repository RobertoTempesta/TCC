package br.com.fio.cepp.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omnifaces.util.Messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ManagedBean(name = "MBLogin")
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String cpf;
		
	public void preRender(ComponentSystemEvent evt) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		
		if ("true".equals(request.getParameter("invalid"))) {
			Messages.addGlobalError("Usuário ou senha inválido!");
		}
		
		facesContext.getResponseComplete();
	}
	
	public void login() throws ServletException, IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
		
		dispatcher.forward(request, response);
		
		facesContext.getResponseComplete();
		
		//RedirectUtil.redirect("/j_spring-security_check");
	}

}
