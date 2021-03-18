package br.com.fio.cepp.domain.service;

import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_CPF_INVALIDO;
import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_NERGOCIO;
import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_TEL_OBRIG;

import br.com.fio.cepp.dao.PacienteDAO;
import br.com.fio.cepp.dao.PessoaDAO;
import br.com.fio.cepp.domain.Paciente;
import br.com.fio.cepp.domain.Pessoa;
import br.com.fio.cepp.service.NegocioException;
import br.com.fio.cepp.util.NegociosUtil;

public class PacienteService {

	private PacienteDAO pacienteDAO = null;
	
	public PacienteService() {
		if (pacienteDAO == null) {
			pacienteDAO = new PacienteDAO();
		}
	}
	
	public void salvar(Paciente paciente) {

		if (paciente.getPessoa().getTelefones().isEmpty()) {
			throw new NegocioException(MSG_ERRO_TEL_OBRIG.getMsg());
		}

		if (!NegociosUtil.validaCPF(paciente.getPessoa().getCpf())) {
			throw new NegocioException(MSG_ERRO_CPF_INVALIDO.getMsg());
		}
		
		Pessoa pessoa = null;

		try {
			pessoa = new PessoaDAO().buscarPorCPF(NegociosUtil.replaceCPF(paciente.getPessoa().getCpf()));
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
		
		if (pessoa != null) {
			throw new NegocioException(MSG_ERRO_CPF_INVALIDO.getMsg());
		}

		try {
			pacienteDAO.merge(paciente);
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
}
