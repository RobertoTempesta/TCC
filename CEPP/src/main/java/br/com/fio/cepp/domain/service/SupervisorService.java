package br.com.fio.cepp.domain.service;

import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_CPF_INVALIDO;
import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_NERGOCIO;
import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_TEL_OBRIG;

import br.com.fio.cepp.dao.PessoaDAO;
import br.com.fio.cepp.dao.SupervisorDAO;
import br.com.fio.cepp.domain.Pessoa;
import br.com.fio.cepp.domain.Supervisor;
import br.com.fio.cepp.service.NegocioException;
import br.com.fio.cepp.util.NegociosUtil;

public class SupervisorService {

	private SupervisorDAO supervisorDAO = null;

	public SupervisorService() {
		if (supervisorDAO == null) {
			supervisorDAO = new SupervisorDAO();
		}
	}
	
	public void salvar(Supervisor supervisor) {

		if (supervisor.getPessoa().getTelefones().isEmpty()) {
			throw new NegocioException(MSG_ERRO_TEL_OBRIG.getMsg());
		}

		if (!NegociosUtil.validaCPF(supervisor.getPessoa().getCpf())) {
			throw new NegocioException(MSG_ERRO_CPF_INVALIDO.getMsg());
		}
		
		Pessoa pessoa = null;

		try {
			pessoa = new PessoaDAO().buscarPorCPF(NegociosUtil.replaceCPF(supervisor.getPessoa().getCpf()));
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
		
		if (pessoa != null) {
			throw new NegocioException(MSG_ERRO_CPF_INVALIDO.getMsg());
		}

		try {
			supervisorDAO.merge(supervisor);
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
}
