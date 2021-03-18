package br.com.fio.cepp.util;

import static br.com.fio.cepp.enumeracao.MensagensEnum.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import br.com.fio.cepp.dao.EstadoDAO;
import br.com.fio.cepp.domain.Endereco;
import br.com.fio.cepp.service.NegocioException;

public class NegociosUtil {

	public static Integer calculaIdade(Date data) {
		Calendar dataNascimento = Calendar.getInstance();
		dataNascimento.setTime(data);
		Calendar hoje = Calendar.getInstance();

		int idade = hoje.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR);

		if (hoje.get(Calendar.MONTH) < dataNascimento.get(Calendar.MONTH)) {
			idade--;
		} else {
			if (hoje.get(Calendar.MONTH) == dataNascimento.get(Calendar.MONTH)
					&& hoje.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
				idade--;
			}
		}

		return idade;
	}

	public static boolean validaCPF(String cpf) {

		cpf = replaceCPF(cpf);

		if (cpf == null || cpf.length() != 11 || isCPFPadrao(cpf))

			return false;
		try {
			Long.parseLong(cpf);
		} catch (NumberFormatException e) { // CPF não possui somente números
			return false;
		}

		if (!calcDigVerif(cpf.substring(0, 9)).equals(cpf.substring(9, 11)))

			return false;

		return true;

	}

	public static String replaceCPF(String cpf) {
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");
		cpf = cpf.replace("_", "");

		return cpf;
	}

	private static boolean isCPFPadrao(String cpf) {

		if (cpf.equals("11111111111") || cpf.equals("22222222222")

				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999")) {

			return true;

		}

		return false;

	}

	private static String calcDigVerif(String num) {
		Integer primDig, segDig;

		int soma = 0, peso = 10;
		for (int i = 0; i < num.length(); i++)

			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;

		if (soma % 11 == 0 | soma % 11 == 1)
			primDig = new Integer(0);
		else
			primDig = new Integer(11 - (soma % 11));
		soma = 0;
		peso = 11;
		for (int i = 0; i < num.length(); i++)
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;

		soma += primDig.intValue() * 2;
		if (soma % 11 == 0 | soma % 11 == 1)
			segDig = new Integer(0);
		else
			segDig = new Integer(11 - (soma % 11));

		return primDig.toString() + segDig.toString();

	}

	public static Endereco carregarCEP(String cep) {

		cep = cep.replace(".", "");
		cep = cep.replace("-", "");
		cep = cep.replace("_", "");

		JSONObject json = capturaJson(cep);
		boolean erro = json != null && json.isNull("erro");

		if (!erro) {
			throw new NegocioException(MSG_ERRO_CEP_INVALIDO.getMsg());
		}

		Endereco endereco = new Endereco();
		endereco.setCep(json.getString("cep"));
		endereco.setBairro(json.getString("bairro"));
		endereco.setRua(json.getString("logradouro"));
		endereco.setCidade(json.getString("localidade"));

		try {

			endereco.setEstado(new EstadoDAO().buscarPorSigla(json.getString("uf")));

		} catch (RuntimeException ex) {
			throw new NegocioException("Ocorreu um erro interno ao buscar o endereço");
		}

		return endereco;

	}

	private static JSONObject capturaJson(String CEP) {

		JSONObject json = null;
		try {
			InputStream is = new URL("https://viacep.com.br/ws/" + CEP + "/json/").openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			json = new JSONObject(jsonText);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
}
