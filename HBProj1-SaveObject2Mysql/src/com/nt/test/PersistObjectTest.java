package com.nt.test;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.nt.entity.Product;
public class PersistObjectTest {
	public static void main(String[] args) {
		Configuration cfg=null;
		SessionFactory factory=null;
		Session ses=null;
		Product prod=null;
		Transaction tx=null;
		boolean flag=false;
		int idval=0;
		//Activate Hibernate f/w (BootStrap hibernate)
		cfg = new Configuration();
		System.out.println(cfg.getProperties());
		System.out.println("...................");
		//supply hibernate cfg file as input file
		cfg.configure("com/nt/cfgs/hibernate.cfg.xml");
		System.out.println(cfg.getProperties());
		//bulild Session Factory
		factory=cfg.buildSessionFactory();
		System.out.println("sessionFactory object class name::"+factory.getClass());
		//open Session
		ses=factory.openSession();
		System.out.println("Session object class name::"+ses.getClass());
		//create Entity object to save with DB s/w
		prod=new Product();
		prod.setPid(4544);
		prod.setPname("chair");
		try {
			tx=ses.beginTransaction(); // //internally calls  con.setAutoCommit(false) to begin the Tx
			System.out.println("tx object class name::"+tx.getClass());
			//save object
			ses.persist(prod);
			flag=true;
		}
		catch(HibernateException he) {
			he.printStackTrace();
			flag=false;
		}
		finally {
			//commit or rollback Tx
			if(flag==true)
				tx.commit();
			else {
				tx.rollback();//internally calls con.rollback()
				System.out.println("Object is not saved ");
			}
			//close session object
			ses.close();
			//close SessionFactory
			factory.close();
		}
	}
}
