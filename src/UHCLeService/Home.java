package UHCLeService;

import java.util.Scanner;

public class Home {

	public static SQLConnector con = new SQLConnector();

	static Scanner input=new Scanner(System.in);
	static String accountType="";
	public static void main(String[] args) 
	{	
		do {
			System.out.println("Student login or Faculty login? student/faculty ");
			accountType=input.nextLine();
			if(accountType.equals("student"))
			{
				loginIdPass();								
			}
			else if(accountType.equals("faculty"))
			{
				loginIdPass();			
			}		
			else 
			{
				System.out.println("Enter account type.");
				break;				
			}
		}while (true);

	}
	public static void loginIdPass()
	{
		do {
			System.out.println("Enter your login ID: ");
			String id=input.nextLine();
			System.out.println("Enter password: ");
			String pass=input.nextLine();
			if (con.login(id, pass,accountType))
			{
				System.out.println("Login Successful!");
				student(id);
				break;
			}
			else
			{
				System.out.println("Incorrect Login or password");
			}					
		}while (true);
	}
	
	
	public static void faculty()
	{
		
		
	}
	
	public static void student(String ID)
	{
		AfterLogin afterlogin=new AfterLogin();
		afterlogin.ID= ID;
		System.out.println("Do you want to: Press 1 for Register a course, Press 2 for Show class, Press 3 for Drop a class, Press 4 for View the bill.");	
		String loginOpion=input.nextLine();
		if(loginOpion.equals("1"))
		{
			afterlogin.registerCourse();					
		}
		else if(loginOpion.equals("2"))
		{
			afterlogin.showClass();
		}
		else if(loginOpion.equals("3"))
		{
			afterlogin.dropCourse();
		}
		else if(loginOpion.equals("4"))
		{
			afterlogin.viewBill();
		}
		else
		{
			System.out.println("Invalid Input!");
		}
	}

}
