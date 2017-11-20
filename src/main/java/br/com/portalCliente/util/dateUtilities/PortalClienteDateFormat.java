package br.com.portalCliente.util.dateUtilities;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PortalClienteDateFormat extends DateFormat {
	private static final long serialVersionUID = -3629730630003302967L;

	private static List<? extends DateFormat> DATE_FORMATS = null;

	public PortalClienteDateFormat(SimpleDateFormat... parser){
		DATE_FORMATS = Arrays.asList(parser) ;
	}

	@Override
	public StringBuffer format(final Date date, final StringBuffer toAppendTo, final FieldPosition fieldPosition) {
		throw new UnsupportedOperationException("This custom date formatter can only be used to *parse* Dates.");
	}

	@Override
	public Date parse(final String source, final ParsePosition pos) {
		Date res = null;
		for (final DateFormat dateFormat : DATE_FORMATS) {
			if ((res = dateFormat.parse(source, pos)) != null) {
				return res;
			}
		}
		return null;
	}
}