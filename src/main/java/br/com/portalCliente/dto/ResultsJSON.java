package br.com.portalCliente.dto;

import java.util.List;

import flexjson.JSONSerializer;

public class ResultsJSON {
	
	private long count;
	private List<?> results;
	
	public ResultsJSON(){}
	
	public ResultsJSON(long count, List<?> results){
		this.count = count;
		this.results = results;
	}
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public List<?> getResults() {
		return results;
	}
	public void setResults(List<?> results) {
		this.results = results;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").include("results").serialize(this);
	}
	
}
