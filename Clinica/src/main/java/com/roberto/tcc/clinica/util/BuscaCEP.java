package com.roberto.tcc.clinica.util;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.roberto.tcc.clinica.domain.Cidade;
import com.roberto.tcc.clinica.domain.Endereco;
import com.roberto.tcc.clinica.domain.Estado;

public class BuscaCEP {

	private String CEP;

	public BuscaCEP(String CEP) {
		this.CEP = CEP;
	}

	public Endereco buscaEndereco()  throws IOException{
		Endereco endereco = null;
		try {
			endereco = new Endereco();
			endereco.setCEP(CEP);
			endereco.setRua(getEndereco());
			if(endereco.getRua().equals(CEP)) {
				return null;
			}
			endereco.setBairro(getBairro());
			Estado estado = new Estado();
			estado.setSigla(getUF());
			Cidade cidade = new Cidade();
			cidade.setEstado(estado);
			cidade.setNome(getCidade());
			endereco.setCidade(cidade);
		} catch (IOException erro) {
			throw erro;
		}

		return endereco;
	}

	private String getEndereco() throws IOException {

		// ***************************************************
		try {

			Document doc = Jsoup.connect("http://www.qualocep.com/busca-cep/" + CEP).timeout(120000).get();
			Elements urlPesquisa = doc.select("span[itemprop=streetAddress]");
			for (Element urlEndereco : urlPesquisa) {
				return urlEndereco.text();
			}

		} catch (SocketTimeoutException e) {

		} catch (HttpStatusException w) {

		}
		return CEP;
	}

	private String getBairro() throws IOException {

		// ***************************************************
		try {

			Document doc = Jsoup.connect("http://www.qualocep.com/busca-cep/" + CEP).timeout(120000).get();
			Elements urlPesquisa = doc.select("td:gt(1)");
			for (Element urlBairro : urlPesquisa) {
				return urlBairro.text();
			}

		} catch (SocketTimeoutException e) {

		} catch (HttpStatusException w) {

		}
		return CEP;
	}

	private String getCidade() throws IOException {

		// ***************************************************
		try {

			Document doc = Jsoup.connect("http://www.qualocep.com/busca-cep/" + CEP).timeout(120000).get();
			Elements urlPesquisa = doc.select("span[itemprop=addressLocality]");
			for (Element urlCidade : urlPesquisa) {
				return urlCidade.text();
			}

		} catch (SocketTimeoutException e) {

		} catch (HttpStatusException w) {

		}
		return CEP;
	}

	private String getUF() throws IOException {

		// ***************************************************
		try {

			Document doc = Jsoup.connect("http://www.qualocep.com/busca-cep/" + CEP).timeout(120000).get();
			Elements urlPesquisa = doc.select("span[itemprop=addressRegion]");
			for (Element urlUF : urlPesquisa) {
				return urlUF.text();
			}

		} catch (SocketTimeoutException e) {

		} catch (HttpStatusException w) {

		}
		return CEP;
	}

}
