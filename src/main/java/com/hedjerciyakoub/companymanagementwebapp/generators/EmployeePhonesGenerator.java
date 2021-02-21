package com.hedjerciyakoub.companymanagementwebapp.generators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class EmployeePhonesGenerator implements IdentifierGenerator{

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		
		
		try {
			Connection connection = session.connection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT count(phone_id) as id FROM company_management.employee_phones;");
			
			int id = 1 ;
			if(rs.next()) {
				 id= rs.getInt(1)+1;
				 
			}
			
			return id ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

}
