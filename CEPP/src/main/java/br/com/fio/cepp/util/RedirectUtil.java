package br.com.fio.cepp.util;

import java.io.IOException;
import java.util.Optional;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class RedirectUtil {

	public static void redirect(String pagina, String key, Long codigo) {
		Optional<String> input1 = Optional.ofNullable(key);
		Optional<Long> input2 = Optional.ofNullable(codigo);

		if (input1.isPresent() && input2.isPresent()) {
			setKey(input1.get(), input2.get());
		}

		redirect(pagina);
	}

	public static void redirect(String pagina) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externoContext = FacesContext.getCurrentInstance().getExternalContext();
			String contePath = externoContext.getRequestContextPath();

			externoContext.redirect(contePath + pagina);
			facesContext.responseComplete();
		} catch (IOException err) {
			throw new FacesException();
		}
	}

	public static Optional<Long> getKey(String key) {
		return Optional.ofNullable((Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(key));
	}
	
	private static void setKey(String key, Long codigo) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put(key, codigo);
	}
}
