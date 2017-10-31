package br.com.portalCliente.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import br.com.portalCliente.util.MessageUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import br.com.portalCliente.dto.ResultsJSON;
import br.com.portalCliente.util.databinder.PortalClienteEncodingDataBinder;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public abstract class AbstractController<T> extends MessageUtils {
	private Class<T> classe;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		TimeZone timezone = TimeZone.getTimeZone("GMT-03");
		TimeZone.setDefault(timezone);
		dateFormat.setTimeZone(timezone);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.registerCustomEditor(String.class, new PortalClienteEncodingDataBinder());

    }

	@SuppressWarnings("unchecked")
	protected String toJson(String attributeName, Object value) {
		JSONObject obj = new JSONObject();
		obj.put(attributeName, value);
		return obj.toJSONString();
	}
	
	protected String toJson(long counts, List<?> results) {
		ResultsJSON json = new ResultsJSON(counts, results);
		return json.toJson();
	}
	
	public Object fromJson(Class object ,String json) {
		return new JSONDeserializer<T>().use(null, object).deserialize(json);
	}
	
	public T fromJson(String json) {
		instanciarClasse();
		return new JSONDeserializer<T>().use(null, classe).deserialize(json);
	}
	
	public List<T> fromJsonArray(String json) {
		instanciarClasse();
		return new JSONDeserializer<List<T>>().use("values", classe).deserialize(json);
	}

	public String toJson(Object object) {
		return new JSONSerializer().exclude("*.class").serialize(object);
	}
	
	public String toJson(List<?> lista) {
		instanciarClasse();
		return new JSONSerializer().exclude("*.class").serialize(lista);
	}

	public String toJson(Object object, String... include) {
		return new JSONSerializer().include(include).exclude("*.class").serialize(object);
	}

	@SuppressWarnings("unchecked")
	private void instanciarClasse(){
		this.classe = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractController.class);
	}	
	
	
	protected String[] includeParam(String... param) {
		return param;
	}

	protected String[] includeParam() {
		return new String[]{};
	}
	
	protected String[] excludeParam(String... param) {
		return param;
	}
	
	protected String[] excludeParam() {
		return new String[]{};
	}

	public HttpHeaders setHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return  headers;
	}
}