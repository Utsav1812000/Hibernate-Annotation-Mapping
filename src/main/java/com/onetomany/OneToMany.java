package com.onetomany;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class OneToMany {
	SessionFactory sf = null;
	Scanner sc = new Scanner(System.in);

	public OneToMany() {
		sf = new Configuration().configure().buildSessionFactory();
	}

	public static void main(String[] args) {
		OneToMany oneToMany = new OneToMany();
		Scanner sc = new Scanner(System.in);

		int choice = -1;
		while (true) {
			System.out.println(
					"1 For Insert\n 2 For Update\n 3 For Delete\n 4 For List Of Que-Ans\n 5 For Que By Id\n 0 For Exit");
			System.out.println("Enter Your Choice: ");
			choice = sc.nextInt();

			switch (choice) {
			case 1:
				oneToMany.addQuestion();
				break;
			case 2:
				oneToMany.updateQuestion();
				break;
			case 3:
				oneToMany.deleteQuestion();
				break;
			case 4:
				oneToMany.listQuestion();
				break;
			case 5:
				oneToMany.getQuestionById();
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
		Question question = new Question();
		System.out.println("Enter the Question: ");
		String que = sc.nextLine();
		question.setQuestion(que);

		List<Answer> answers = new ArrayList<Answer>();
		Answer answer = null;
//		System.out.println("Enter the Answer: ");
//		answer.setAnswer(sc.nextLine());
//		answer.setQuestion(question);
//		answers.add(answer);

		while (true) {
			System.out.println("Do you want to add more Answer ? press 1.");
			int ch = sc.nextInt();
			sc.nextLine();
			if (ch == 1) {
				answer = new Answer();
				System.out.println("Enter the Answer: ");
				answer.setAnswer(sc.nextLine());
				answers.add(answer);
				answer.setQuestion(question);
			} else {
				break;

			}

		}
		question.setAnswers(answers);
		Transaction tx = session.beginTransaction();
		try {
			session.save(question);
			tx.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			tx.rollback();
		}

	}

	public void getQuestionById() {
		System.out.println("Enter the Question Id: ");
		int queid = sc.nextInt();
		Session session = sf.openSession();
		Question question = session.get(Question.class, queid);
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Id\t\t Question\t\t Answer");
		System.out.println("----------------------------------------------------------------------");
		for (int i = 0; i < question.getAnswers().size(); i++) {
			System.out.println(question.getQid() + "\t\t" + question.getQuestion() + "\t\t"
					+ question.getAnswers().get(i).getAnswer());
			System.out.println("----------------------------------------------------------------------");
		}
		session.close();
	}

	public void listQuestion() {
		Session session = sf.openSession();
		List<Question> questions = session.createQuery("from Question", Question.class).getResultList();
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Id\t\t Question\t\t Answer");
		System.out.println("----------------------------------------------------------------------");
		for (Question question : questions) {
			for (int i = 0; i < question.getAnswers().size(); i++) {
				System.out.println(question.getQid() + "\t\t" + question.getQuestion() + "\t\t"
						+ question.getAnswers().get(i).getAnswer());
				System.out.println("----------------------------------------------------------------------");
			}
		}
		System.out.println();
		session.close();
	}

	public void deleteQuestion() {
		System.out.println("Enter the Question Id:");
		int queid = sc.nextInt();
		Session session = sf.openSession();
		Question question = session.get(Question.class, queid);
		Transaction tx = session.beginTransaction();
		try {
			session.delete(question);
			System.out.println("Successfully Deleted !");
			tx.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			tx.rollback();
		}

	}

	public void updateQuestion() {
		System.out.println("Enter the Question Id which you want to update: ");
		int queid = sc.nextInt();
		Session session = sf.openSession();
		Question question = session.get(Question.class, queid);
		Answer answer = null;
		List<Answer> answers = new ArrayList<Answer>();
		System.out.println("What You Want to Update? 1 For Question and 2 For Answer. ");
		int ch = sc.nextInt();
		sc.nextLine();
		if (ch == 1) {
			// update que
			System.out.println("Enter New Question: ");
			question.setQuestion(sc.nextLine());
		} else if (ch == 2) {
			// update ans
			for (int i = 0; i < question.getAnswers().size(); i++) {
				System.out.println("Your Old Answers are: "+question.getAnswers().get(i).getAnswer()+"\n");
				answers.add(answer);
			}
			
			while (true) {
				System.out.println("Do you want to update a Answer? press 1.");
				int ch1 = sc.nextInt();
				if (ch1 == 1) {
					answer = new Answer();
					System.out.println("Enter New Answer: ");
					answer.setAnswer(sc.nextLine());
					answers.add(answer);
					answer.setQuestion(question);
				}
				else {
					break;
				}	
			}
			question.setAnswers(answers);
		}
		Transaction tx = session.beginTransaction();
		try {
			session.save(question);
			tx.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			tx.rollback();
		}

	}

}
