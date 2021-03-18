package br.com.fio.cepp.enumeracao;

import lombok.Getter;

@Getter
public enum MensagensEnum {

	MSG_ERRO_NERGOCIO("Ocorreu um erro inesperado. Tente novamente mais tarde e/ou entre em contato com o administrador do sistema."),
	MSG_ERRO_TEL_INVALIDO("Telefone inválido."),
	MSG_ERRO_TEL_OBRIG("Necessário adicionar um Telefone."),
	MSG_ERRO_CPF_INVALIDO("CPF é inválido."),
	MSG_ERRO_CEP_INVALIDO("CEP é inválido."),
	MSG_SALVO_SUCESSO("Salvo com sucesso."),
	MSG_EXCLUIDO_SUCESSO("Excluido com sucesso."),
	MSG_OPERACAO_CUMCLUIDA_SUCESSO("Operação concluída com sucesso.");
	
	private String msg;
	
	private MensagensEnum(String msg) {
		this.msg = msg;
	}
}
