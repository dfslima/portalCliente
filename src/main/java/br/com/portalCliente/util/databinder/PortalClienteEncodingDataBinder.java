package br.com.portalCliente.util.databinder;

import java.beans.PropertyEditorSupport;
import java.io.UnsupportedEncodingException;

public class PortalClienteEncodingDataBinder extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) {
		if (text == null) {
			return;
		}
		setValue(enconding(text));
	}

	@Override
	public String getAsText() {
		Object value = getValue();
		return (value != null ? value.toString() : "");
	}

	private  String enconding(String text){

		try {
			return new String(text.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return text;
		}

	}


}
