package com.hedjerciyakoub.companymanagementwebapp.generators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class DepartmentGenerator implements IdentifierGenerator{

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Connection connection = session.connection();
		
		try {
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT count(dept_id) as id FROM company_management.department");
			 
			int id = 1; 
			
			if(rs.next()) {
				id = rs.getInt(1)+1;
			}
			return id;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}

}
