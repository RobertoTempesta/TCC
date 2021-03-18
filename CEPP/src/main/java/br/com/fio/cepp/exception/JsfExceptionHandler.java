package br.com.fio.cepp.exception;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.omnifaces.util.Messages;

import br.com.fio.cepp.service.NegocioException;
import static br.com.fio.cepp.util.RedirectUtil.redirect;
import javassist.NotFoundException;

public class JsfExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	private static String PAGE_ERROR = "/paginas/publico/error.xhtml";
	private static String PAGE_404 = "/paginas/publico/404.xhtml";
	private static String PAGE_ACCESS = "/paginas/publico/access.xhtml";

	public JsfExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();

		while (events.hasNext()) {
			ExceptionQueuedEvent event = events.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			Throwable exception = context.getException();
			NegocioException negocioException = getNegocioException(exception);

			boolean handle = false;

			try {
				if (exception instanceof ViewExpiredException) {
					handle = true;
					redirect(PAGE_ACCESS);
				} else if (exception instanceof NotFoundException) {
					handle = true;
					redirect(PAGE_404);
				} else if (negocioException != null) {
					handle = true;
					Messages.addGlobalError(negocioException.getMessage());
				} else {
					handle = true;
					redirect(PAGE_ERROR);
				}
			} finally {
				if (handle) {
					events.remove();
				}
			}
		}

		getWrapped().handle();
	}

	private NegocioException getNegocioException(Throwable exception) {
		if (exception instanceof NegocioException) {
			return (NegocioException) exception;
		} else if (exception.getCause() != null) {
			return getNegocioException(exception.getCause());
		}
		return null;
	}

}
