package br.com.fio.cepp.domain.service;

import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_CPF_INVALIDO;
import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_NERGOCIO;
import static br.com.fio.cepp.enumeracao.MensagensEnum.MSG_ERRO_TEL_OBRIG;

import br.com.fio.cepp.dao.AlunoDAO;
import br.com.fio.cepp.dao.PessoaDAO;
import br.com.fio.cepp.domain.Aluno;
import br.com.fio.cepp.domain.Pessoa;
import br.com.fio.cepp.service.NegocioException;
import br.com.fio.cepp.util.NegociosUtil;

public class AlunoService {

	private AlunoDAO alunoDAO = null;

	public AlunoService() {
		if (alunoDAO == null) {
			alunoDAO = new AlunoDAO();
		}
	}

	public void salvar(Aluno aluno) {

		if (aluno.getPessoa().getTelefones().isEmpty()) {
			throw new NegocioException(MSG_ERRO_TEL_OBRIG.getMsg());
		}

		if (!NegociosUtil.validaCPF(aluno.getPessoa().getCpf())) {
			throw new NegocioException(MSG_ERRO_CPF_INVALIDO.getMsg());
		}
		
		Pessoa pessoa = null;

		try {
			pessoa = new PessoaDAO().buscarPorCPF(NegociosUtil.replaceCPF(aluno.getPessoa().getCpf()));
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
		
		if (pessoa != null) {
			throw new NegocioException(MSG_ERRO_CPF_INVALIDO.getMsg());
		}

		try {
			alunoDAO.merge(aluno);
		} catch (RuntimeException erro) {
			throw new NegocioException(MSG_ERRO_NERGOCIO.getMsg());
		}
	}
}
