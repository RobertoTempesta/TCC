package br.com.fio.cepp.domain.service;

import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_NERGOCIO;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.fio.cepp.dao.SessaoDAO;
import br.com.fio.cepp.domain.Sessao;
import br.com.fio.cepp.domain.enumeracao.PresencaStatus;
import br.com.fio.cepp.service.NegocioException;

public class SessaoService {
	
	private SessaoDAO sessaoDAO = null;
	
	public SessaoService() {
		if (sessaoDAO == null) {
			sessaoDAO = new SessaoDAO();
		}
	}

	public void salvar(Sessao sessao) {

		if (sessao.getCodigo() == null) {

			List<Sessao> sessoes = null;
			
			try {
				sessoes = sessaoDAO.verificarAgendamentoLivre(sessao);
			} catch (RuntimeException err) {
				throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
			}
			
			if (sessoes != null && !sessoes.isEmpty()) {
				throw new NegocioException("Sessão inválida. Verifique se 'Sala de Atendimento', 'Aluno' e/ou período já estão em uso.");
			}
			
			sessao.setPresencaStatus(PresencaStatus.A);
		}
		
		if (sessao.getPresencaStatus().equals(PresencaStatus.FJ) && StringUtils.isEmpty(sessao.getObservacao())) {
			throw new NegocioException("Coloque uma justificativa em 'Observações'.");
		}

		try {
			sessaoDAO.merge(sessao);
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
}
