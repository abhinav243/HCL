package com.hcl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkerThread implements Runnable{

	String localFile;
	Question2Repository repo;
	SessionFactory sessionFactory;
	EntityManagerFactory enitityManagerFactory;
	@Autowired
	public WorkerThread(Question2Repository repo) {
		this.repo=repo;
	}
	public WorkerThread(String localFile,SessionFactory sessionFactory) {
		this.localFile=localFile;
		this.sessionFactory=sessionFactory;
	}
	@Override
	public void run() {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(localFile));
			String line;
			line = br.readLine();
			while ((line = br.readLine()) != null)
			{  
				Question2Model dbValue=new Question2Model();
				String[] employee = line.split(",");
				dbValue.setCallAttempts(Long.parseLong(employee[4].trim()));
				dbValue.setCellId(Long.parseLong(employee[3].trim()));
				dbValue.setGranularityPeriod(Long.parseLong(employee[1].trim()));
				dbValue.setObjectName(employee[2].trim());
				dbValue.setResultTime(LocalDateTime.parse(employee[0].trim(),formatter));
				session.persist(dbValue);
				//to save values one by one
				//repo.save(dbValue);
			} 
			//to save multiple values in db together
			tx.commit();
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}finally{
			session.close();
		}
	}

}
