package br.com.unamaproject.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.unamaproject.server.service.exceptions.InvalidFormatException;

public class DateUtils {

	public static Date convertStringtoDate(String data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		try {
			return sdf.parse(data);
		} catch (ParseException e) {
			throw new InvalidFormatException("Error! expected format 'dd-MM-yyyy HH:mm:ss'");
		}
	}	
}
