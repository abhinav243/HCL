package com.hcl;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

@RequestMapping("/sftp")
@RestController
public class Question2 {
	@Autowired
	Question2Repository myRepo;
	@Autowired
	EntityManagerFactory enitityManagerFactory;
	private static LocalDateTime startDateTime; //to store the the timestamp till which the files are loaded in db
	
	@PostConstruct
	public void init() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");//multiple formats can be added later
		startDateTime=LocalDateTime.parse("2000-01-01 01:01",formatter); //initial date -> files after this timestamp will be populated in the DB
	}
	//had some issues with sftp, implemented it for ftp
	@PostMapping("/download") //api to be added in CRON jobs-> should run after the previous one is finished
	public void main() throws FileSystemException {

		SessionFactory sessionFactory = enitityManagerFactory.unwrap(SessionFactory.class);
		String username="user";
		String password="12345";
		String server="127.0.0.1";
		int port=1026;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime latestDate=startDateTime;
		/*SFTP code
		 * StandardFileSystemManager manager = new StandardFileSystemManager();
		manager.init();
		FileObject local=null;
		FileObject remote=null*/;
		try {
			/*SFTP Code
			 * FileObject localFileObject=manager.resolveFile("sftp://"+username+":"+password+"@"+remoteHost+"/");
			FileObject[] children = localFileObject.getChildren();*/
		    ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		    FTPClient ftpClient = new FTPClient();
		    ftpClient.connect(server,port);
		    ftpClient.login(username, password);
		     
		    // lists files and directories in the current working directory
		    FTPFile[] files = ftpClient.listFiles();
			for ( int i = 0; i < files.length; i++ ){
				String name=files[ i ].getName();
			    String dateStr=name.replace(".csv", "");
			    dateStr=dateStr.substring(dateStr.length()-16);//regex can be used to get dateStr more efficiently
			    LocalDateTime date=LocalDateTime.parse(dateStr,formatter);
			    if(date.isAfter(latestDate)) {
			    	latestDate=date;
			    }
			    if(date.isAfter(startDateTime)) {
			    	/*SFTP Code
			    	 * local = manager.resolveFile("/home/vin/Documents/" + name);
					remote = manager.resolveFile("sftp://" + username + ":" + password + "@" + remoteHost + "/" + name);
					local.copyFrom(remote, Selectors.SELECT_SELF);*/
			    	OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream("/home/vin/Documents/"+name));
		            boolean success = ftpClient.retrieveFile(name, outputStream1);
		            outputStream1.close();
				    executor.execute(new WorkerThread("/home/vin/Documents/"+name,sessionFactory));
			    }
			}
			startDateTime=latestDate;
			executor.shutdown();
			while (!executor.isTerminated()) {
			}
		}catch(Exception e){
			System.out.println(e.getLocalizedMessage());
//			e.printStackTrace();
		}finally {
			/*SFTP Code
			 * manager.close();
			local.close();
			remote.close();*/
		}
	}
}
