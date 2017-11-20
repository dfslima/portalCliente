package br.com.portalCliente.util.dateUtilities;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import flexjson.JSONException;
import flexjson.ObjectBinder;
import flexjson.ObjectFactory;
import flexjson.transformer.AbstractTransformer;

public class PortalClienteDateTransformer extends AbstractTransformer implements ObjectFactory {

	SimpleDateFormat simpleDateFormatter;
	SimpleDateFormat [] simpleDateFormatterArray;
	
	public PortalClienteDateTransformer(String dateFormat) {
		simpleDateFormatter = getSimpleDateFormat(dateFormat);	}

	public PortalClienteDateTransformer(String... dateFormat) {
		simpleDateFormatterArray = new SimpleDateFormat[dateFormat.length];

		for(int i = 0; i < dateFormat.length; i++){
			simpleDateFormatterArray[i] = new SimpleDateFormat(dateFormat[i]); 
		}
	}


	public void transform(Object value) {
		getContext().writeQuoted(simpleDateFormatter.format(value));
	}

	public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
		try {
			if(simpleDateFormatterArray != null && simpleDateFormatterArray.length > 0){
				return dateToString(value);
			}
			java.util.Date date = new java.util.Date(); 
			date = simpleDateFormatter.parse(value.toString());
			return date;
		} catch (ParseException e) {
			throw new JSONException(String.format( "Failed to parse %s with %s pattern.", value, simpleDateFormatter.toPattern() ), e );
		}
	}

	private Object dateToString(Object value){


		try {
			if(value.getClass() == Long.class){
				Long a = (Long) value;
				Date date = new Date(a);
			}
			
			PortalClienteDateFormat portalClienteDateFormat = new PortalClienteDateFormat(simpleDateFormatterArray);
			return portalClienteDateFormat.parse(value.toString());
		} catch (ParseException e) {
			throw new JSONException(String.format( "Failed to parse %s with %s pattern.", value, simpleDateFormatter.toPattern() ), e );
		}
	}
	
	private SimpleDateFormat getSimpleDateFormat(String parse){
		SimpleDateFormat format = new SimpleDateFormat(parse);
		TimeZone timezone = TimeZone.getTimeZone("GMT-03");
		TimeZone.setDefault(timezone);
		format.setTimeZone(timezone);
		format.setLenient(false);
		return format;
	}
	
	
	
}


