package br.com.portalCliente.util;

import flexjson.JSONSerializer;


public abstract class MessageUtils {

	public static final String MESSAGE_ERROR_GENERAL = "Ops! Ocorreu um erro ao salvar a seguradora. Favor, tente novamente";
	public static final String MESSAGE_ERROR_EDIT_PROPOSAL = "Já existe uma proposta cadastrada com esse número";

	private boolean erro = false;
	private String message;
	
	
	public void erro(String message){
		this.erro = true;
		this.message = message;		
	}
	
	private boolean isErro() {
		return erro;
	}

	private String getMessage() {
		return message;
	}

	public String messageToJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}
	
}
