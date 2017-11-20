package br.com.portalCliente.util.dateUtilities;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateInterceptorUtils {

	private static TimeZone tz = TimeZone.getTimeZone("America/Recife");

	public static int FIRST_HOUR_DAY [] = {0,0,0};
	public static int LAST_HOUR_DAY [] = {23,59,59};
	public static boolean START_YEAR = true;
	public static boolean END_YEAR = false;
	
	
	public static Date startOrEndYear(Date date, boolean option){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		if(option){
			calendar.set(Calendar.MONTH, 1 - 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			
		}
		else{
			calendar.set(Calendar.MONTH, 12 - 1);
			calendar.set(Calendar.DAY_OF_MONTH, 31);
		}
		

		return calendar.getTime();
	}

	
	/**
	 * Esse método ira alterar o dia da data para o primeiro dia do mês 
	 * <br /> 
	 * Se não for setado o parâmetro, o <b>Date</b> retornado será <b>Null</b>
	 * @param date
	 * @return Date 
	 */
	
	public static Date firstDayOfMonth(Date date){
		return genericDayOfMonth(date, null, true);
	}
		
	/**
	 * 	Esse método ira alterar o dia da data para o primeiro dia do mês 
	 *  <br />
	 *  O parâmetro a ser passado no value é: SaleDateFactory.FIRST_HOUR_DAY ou SaleDateFactory.LAST_HOUR_DAY
	 *  <br /> 
	 *  Se não for setado nenhum dos parametros citados, o <b>Date</b> retornado será <b>Null</b>
	 * @param date, value
	 * @return <b>Date</b>
	 */
	
	public static Date firstDayOfMonth(Date date, int[] value){
		return genericDayOfMonth(date, value, true);
	}
	
	// ------------------------------- iniciao de metodos para alterar o ultimo dia do mês -------------------
	

	/**
	 * Esse método ira alterar o dia da data para o último dia do mês 
	 * <br /> 
	 * Se não for setado o parâmetro, o <b>Date</b> retornado será <b>Null</b>
	 * @param date
	 * @return Date 
	 */
	
	public static Date lastDayOfMonth(Date date){
		return genericDayOfMonth(date, null, false);
	}
	
	/**
	 * 	Esse método ira alterar o dia da data para o último dia do mês 
	 *  <br />
	 *  O parâmetro a ser passado no value é: SaleDateFactory.FIRST_HOUR_DAY ou SaleDateFactory.LAST_HOUR_DAY
	 *  <br /> 
	 *  Se não for setado nenhum dos parametros citados, o <b>Date</b> retornado será <b>Null</b>
	 * @param date, value
	 * @return <b>Date</b>
	 */
	
	public static Date lastDayOfMonth(Date date, int [] value){
		return genericDayOfMonth(date, value, false);
	}
	
	// ---------------------------- Inicio dos metodos para alterar hora do dia ------------------------------
	
	
	/**
	 *  O parâmetro a ser passado no value é: SaleDateFactory.FIRST_HOUR_DAY ou SaleDateFactory.LAST_HOUR_DAY
	 *  <br /> 
	 *  Se não for setado nenhum dos parametros citados, o <b>Date</b> retornado será <b>Null</b>
	 * @param value
	 * @return <b>Date</b>
	 */
	
	public static Date firstOrLastHourOfDay(int [] value){
		return firstOrLastHourOfDay(null, value);
	}
	
	/**
	 *   O parâmetro a ser passado no value é: SaleDateFactory.FIRST_HOUR_DAY ou SaleDateFactory.LAST_HOUR_DAY
	 *  <br /> 
	 *  Se não for setado nenhum dos parametros citados, o <b>Date</b> retornado será <b>Null</b>
	 * @param value
	 * @return <b>Date</b>
	 */
	
	public static Date firstOrLastHourOfDay(Date date, int [] value){
		Calendar calendar = Calendar.getInstance(tz);
		Date resultDate = null;
		
		if(date != null){
			calendar.setTime(date);
		}
		if(value != null && value.length == 3){
			calendar.set(Calendar.HOUR_OF_DAY, value[0]);
			calendar.set(Calendar.MINUTE, value[1]);
			calendar.set(Calendar.SECOND, value[2]);
			resultDate = calendar.getTime();
		}
		
		return resultDate;
	}

	
	
	// ---------------------- métodos generico para primeira e última data do mês --------------------------
	
	private static Date genericDayOfMonth(Date date, int [] value, boolean firstDay){
		Calendar calendar = Calendar.getInstance(tz);

		if(date == null){
			return null;
		}

		Date dateResult = firstOrLastHourOfDay(date, value);
		if(dateResult == null){
			return null;
		}
		
		calendar.setTime(dateResult);
		
		int day = 1;
		if(!firstDay){
			day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		
		calendar.set(Calendar.DAY_OF_MONTH, day);

		return calendar.getTime();
	}

	public static Date setMinuteInDate(Date date, int hour, int minute, int second) {
		try {

			Calendar dateCalendar = Calendar.getInstance();
			dateCalendar.setTime(date);
			dateCalendar.add(Calendar.HOUR_OF_DAY, hour);
			dateCalendar.add(Calendar.MINUTE, minute);
			dateCalendar.add(Calendar.SECOND, second);

			return dateCalendar.getTime();

		} catch (Exception e) {
			return null;
		}
	}
	
	public static Calendar getCalendarNotTime() {
		return getCalendarNotTime(null);
}
	
	public static Calendar getCalendarNotTime(Date date) {
			Calendar dateCalendar = Calendar.getInstance(tz);
			if(date != null){
				dateCalendar.setTime(date);
			}
			dateCalendar.set(Calendar.HOUR_OF_DAY, 0);
			dateCalendar.set(Calendar.MINUTE, 0);
			dateCalendar.set(Calendar.SECOND, 0);
			dateCalendar.set(Calendar.MILLISECOND, 0);
			return dateCalendar;
	}
	
	
}