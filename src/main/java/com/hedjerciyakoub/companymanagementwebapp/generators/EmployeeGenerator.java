package com.hedjerciyakoub.companymanagementwebapp.generators;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class EmployeeGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		String y = String.valueOf(LocalDate.now().getYear()).substring(2, 4);
		String m = String.valueOf(LocalDate.now().getMonthValue());
		String d = String.valueOf(LocalDate.now().getDayOfMonth());
		String mi = String.valueOf(LocalDateTime.now().getMinute());
		String s = String.valueOf(LocalDateTime.now().getSecond() / 6);

		int generatedId = Integer.parseInt(y + m + d + mi + s);
		return generatedId;
		
	}

}
