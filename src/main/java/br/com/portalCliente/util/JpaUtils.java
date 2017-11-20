package br.com.portalCliente.util;

import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

public abstract class JpaUtils extends MessageUtils {

	protected static Map<String, Object> params;
	
	@SuppressWarnings("rawtypes")
	protected static void getParams (TypedQuery query) {

		for (Map.Entry<String, Object> p : params.entrySet()) {
			if(p.getValue() != null){ 
				query.setParameter(p.getKey(), p.getValue());
			}
		}	
	}

	protected static void getParams (Query query) {

		for (Map.Entry<String, Object> p : params.entrySet()) {
			if(p.getValue() != null){ 
				query.setParameter(p.getKey(), p.getValue());
			}
		}	
	}
	
	@SuppressWarnings("rawtypes")
	protected static void setParams (Map<String, Object> params, TypedQuery query) {

		for (Map.Entry<String, Object> p : params.entrySet()) {
			if(p.getValue() != null){ 
				query.setParameter(p.getKey(), p.getValue());
			}
		}	
	}

	protected static void setParams (Map<String, Object> params, Query query) {

		for (Map.Entry<String, Object> p : params.entrySet()) {
			if(p.getValue() != null){ 
				query.setParameter(p.getKey(), p.getValue());
			}
		}	
	}
	
	protected static void validateSqlJoin(StringBuilder sql, String newSqlJoin){
		if(!sql.toString().contains(newSqlJoin)){
			sql.append(" " + newSqlJoin);
		}		
	}

	protected static void validateSql(StringBuilder sql, String newSql){
		if(sql.toString().contains("WHERE")){
			sql.append(" AND " + newSql);
		}
		else {
			sql.append(" WHERE " + newSql);
		}
	}
	
	protected static void validateWhere(StringBuilder sql, String newSql){
		if(sql.toString().contains("WHERE")){
			sql.append(" AND " + newSql);
		}
		else {
			sql.append(" WHERE " + newSql);
		}
	}
	
	protected static int calculateFirstResultQuery(int firstResult, int maxResults){
		int value = firstResult - 1;
		if(value <= 0){
			return 0;
		}
		value = value * maxResults;
		return value;
	}
}
