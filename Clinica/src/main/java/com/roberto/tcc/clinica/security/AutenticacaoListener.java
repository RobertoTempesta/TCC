package com.roberto.tcc.clinica.security;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import com.roberto.tcc.clinica.bean.LoginBean;
import com.roberto.tcc.clinica.domain.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {


		if (!ehPaginaPublica()) {
			// Pega o atributo da sessão
			LoginBean autenticacaoBean = Faces.getSessionAttribute("MBLogin");

			if (autenticacaoBean == null) {
				Faces.navigate("/paginas/publico/login.xhtml");
				return;
			}

			Usuario userLogado = autenticacaoBean.getUsuarioLogado();
			if (userLogado == null) {
				Faces.navigate("/paginas/publico/login.xhtml");
				return;
			}
		}

	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
	
	private boolean ehPaginaPublica() {
		// Pega a pagina atual da sessão
		String paginaAtual = Faces.getViewId();

		if(paginaAtual.contains("login.xhtml") || paginaAtual.contains("404.xhtml") 
				|| paginaAtual.contains("access.xhtml") || paginaAtual.contains("error.xhtml")) {
			
			return true;
		}
		
		return false;
	}

}
