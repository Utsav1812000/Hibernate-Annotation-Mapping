package com.onetoone;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class OneToOne {
	SessionFactory sf = null;
	Scanner sc = new Scanner(System.in);

	public OneToOne() {
		sf = new Configuration().configure().buildSessionFactory();
	}

	public static void main(String[] args) {
		OneToOne oneToOne = new OneToOne();
		Scanner sc = new Scanner(System.in);

		int choice = -1;
		while (true) {
			System.out.println(
					"1 For Insert\n 2 For Update\n 3 For Delete\n 4 For List Of Que-Ans\n 5 For Que By Id\n 0 For Exit");
			System.out.println("Enter Your Choice: ");
			choice = sc.nextInt();

			switch (choice) {
			case 1:
				oneToOne.addQuestion();
				break;
			case 2:
				oneToOne.updateQuestion();
				break;
			case 3:
				oneToOne.deleteQuestion();
				break;
			case 4:
				oneToOne.listQuestion();
				break;
			case 5:
				oneToOne.getQuestionById();
				break;
			case 0:
				System.exit(0);
				break;

			default:
				break;
			}
		}

	}

	public void addQuestion() {
		Session session = sf.openSession();
		Question q1 = new Question();
		System.out.println("Enter Question: ");
		String que = sc.nextLine();
		q1.setQuestion(que);

//		Question q2 = new Question();
//		q2.setQuestion("What is C++ ?");
//
//		Question q3 = new Question();
//		q3.setQuestion("What is HTML ?");

		Answer a1 = new Answer();
		System.out.println("Enter Answer: ");
		String ans = sc.nextLine();
		a1.setAnswer(ans);
		a1.setQuestion(q1);

//		Answer a2 = new Answer();
//		a2.setAnswer("C++ is Programming language.");
//		a2.setQuestion(q2);
//
//		Answer a3 = new Answer();
//		a3.setAnswer("HTML is a markup language.");
//		a3.setQuestion(q3);

		q1.setAnswer(a1);
//		q2.setAnswer(a2);
//		q3.setAnswer(a3);

		Transaction tx = session.beginTransaction();
		session.save(q1);
//		session.save(q2);
//		session.save(q3);
		tx.commit();

		session.close();
	}

	public void listQuestion() {
		Session session = sf.openSession();
		List<Question> questions = session.createQuery("from Question", Question.class).getResultList();
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("Id\t|\tQuestion\t\t|\t Answer");
		System.out.println("-----------------------------------------------------------------------------");
		for (Question question : questions) {
			System.out.println(question.getQid() + "\t|\t" + question.getQuestion() + "\t\t|\t"
					+ question.getAnswer().getAnswer());
			System.out.println("-----------------------------------------------------------------------------");
		}

	}

	public void getQuestionById() {
		Session session = sf.openSession();
		System.out.println("Enter Question Id: ");
		int queid = sc.nextInt();
		Question question = session.get(Question.class, queid);
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("Id\t|\tQuestion\t\t|\t Answer");
		System.out.println("-----------------------------------------------------------------------------");
		if(question == null) {
			System.out.println("Question Not Exist!");
		}else {
			System.out.println(question.getQid() + "\t|\t" + question.getQuestion() + "\t\t|\t"
					+ question.getAnswer().getAnswer());
			System.out.println("-----------------------------------------------------------------------------");
		}

	}

	public void deleteQuestion() {
		Session session = sf.openSession();
		System.out.println("Enter the question id: ");
		int queid = sc.nextInt();
		Question question = session.get(Question.class, queid);
		if (question == null) {
			System.out.println("Question Not Exist!");
		} else {
			Transaction tx = session.beginTransaction();
			session.delete(question);
			System.out.println("Succesfully Deleted !");
			tx.commit();
		}
		session.close();

	}

	public void updateQuestion() {
		Session session = sf.openSession();
		System.out.println("Enter the Question Id that you want to update: ");
		int queid = sc.nextInt();
		Question question = session.get(Question.class, queid);
		
		System.out.println("What you want to update ?\n 1. For Question\n 2. For Answer");
		int ch = sc.nextInt();
		sc.nextLine();
		if(ch == 1) {
			System.out.println("Enter new Question: ");
			question.setQuestion(sc.nextLine());
		}else if(ch == 2) {
			System.out.println("Enter new Answer: ");
			Answer answer = session.get(Answer.class, queid);
			answer.setAnswer(sc.nextLine());
			question.setAnswer(answer);
		}
		Transaction tx = session.beginTransaction();
		session.update(question);
		tx.commit();
		session.close();

	}

}
