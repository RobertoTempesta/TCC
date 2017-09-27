package com.roberto.tcc.clinica.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


import com.roberto.tcc.clinica.domain.Usuario;

public class Criptografia {

	public static Usuario gerarSenhaCrip(Usuario usuario, String textoPuro)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		try {

			UUID uuid = UUID.randomUUID();
			String salt = uuid.toString();

			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			textoPuro = textoPuro + salt;
			byte messageDigest[] = algorithm.digest(textoPuro.getBytes("UTF-8"));

			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			String senha = hexString.toString();
			usuario.setSenha(senha);
			usuario.setSalt(salt);

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException erro) {
			throw erro;
		}

		return usuario;
	}

	public static String gerarSenhaCriptografada(String textoPuro)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		String senha = null;
		
		try {

			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(textoPuro.getBytes("UTF-8"));

			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			senha = hexString.toString();

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException erro) {
			throw erro;
		}

		return senha;
	}
}
