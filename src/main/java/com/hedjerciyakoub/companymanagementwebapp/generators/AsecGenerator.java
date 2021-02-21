package com.hedjerciyakoub.companymanagementwebapp.generators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

public class AsecGenerator extends SequenceStyleGenerator {
	
	public static final String TABLE_NAME="tableName";
	public static final String ID_NAME="idName";
	String tableName;
	String idName; 
	
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session , Object object) throws HibernateException{ // when hibernate needs primery key then it will call this method to presist a new entity
		
		
		Connection connection = session.connection();
		Statement statement;
		int id = 1;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT count("+idName+") FROM "+tableName);
			
			if(rs.next()) {

				id=rs.getInt(1)+1;

			}


			if(id!=1) {
				rs = statement.executeQuery("SELECT MAX("+idName+") FROM "+tableName);
			}
			
			if(rs.next()) {
					id=rs.getInt(1)+1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}



		return id;
	}
	
	
	@Override
	public void configure(Type type , Properties params , ServiceRegistry serviceRegistry) {
		super.configure(LongType.INSTANCE, params, serviceRegistry);
		tableName = ConfigurationHelper.getString(TABLE_NAME, params);
		idName = ConfigurationHelper.getString(ID_NAME, params);		
	}

}
