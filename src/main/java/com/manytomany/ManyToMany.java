package com.manytomany;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ManyToMany {
	public static void main(String[] args) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		
		Employee e1 = new Employee();
		e1.setEmpname("utsav");
		
		Employee e2 = new Employee();
		e2.setEmpname("kaushal");
		
		Employee e3 = new Employee();
		e3.setEmpname("satish");
		
		Projects utsavProjectsJava = new Projects();
		utsavProjectsJava.setProject("Java");
		
		Projects utsavProjectsPython = new Projects();
		utsavProjectsPython.setProject("Python");
		
		Projects utsavProjectsCpp = new Projects();
		utsavProjectsCpp.setProject("Cpp");
		
		List<Projects> utsavProjects = new ArrayList<Projects>();
		utsavProjects.add(utsavProjectsJava);
		utsavProjects.add(utsavProjectsPython);
		utsavProjects.add(utsavProjectsCpp);
		
		e1.setProjects(utsavProjects);
		
		Projects kaushalProjectsHtml = new Projects();
		kaushalProjectsHtml.setProject("HTML");
		
		Projects kaushalProjectsJs = new Projects();
		kaushalProjectsJs.setProject("JS");

		List<Projects> kaushalProjects = new ArrayList<Projects>();
		kaushalProjects.add(kaushalProjectsHtml);
		kaushalProjects.add(kaushalProjectsJs);
		
		e2.setProjects(kaushalProjects);
		
		Projects satishProjectsAjax = new Projects();
		satishProjectsAjax.setProject("Ajax");
		
		List<Projects> satishProjects = new ArrayList<Projects>();
		satishProjects.add(satishProjectsAjax);
		satishProjects.add(utsavProjectsPython);
		
		e3.setProjects(satishProjects);
		
		Transaction tx = session.beginTransaction();
		session.save(e1);
		session.save(e2);
		session.save(e3);
		
		tx.commit();
		session.close();
	}
}
