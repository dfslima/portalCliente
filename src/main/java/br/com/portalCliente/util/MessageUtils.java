package br.com.portalCliente.util;

import flexjson.JSONSerializer;


public abstract class MessageUtils {

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
