package UHCLeService;
import java.text.NumberFormat;
import java.util.List;
import java.util.Scanner;

public class AfterLogin {
	Scanner input=new Scanner(System.in);
	SQLConnector sql=new SQLConnector();
	
	public String ID="";
	public void registerCourse()

	{		
		System.out.println("1. All Open Courses or 2. Search for a course by course ID? ");
		String searchCourse=input.nextLine();
		String openCourseView="";
		if(searchCourse.equals("1"))
		{
			List<String> listOfOpenCourse=sql.displayOpenCourses();
			int count=1;
			for(int i=0;i<listOfOpenCourse.size();i++)
			{
				System.out.print(count +"."+ listOfOpenCourse.get(i)+" , ");
				count++;
			}			
			//int indexOfOpenCourse=input.nextInt();
			System.out.print("\n Please select which course you want to view: ");
			openCourseView=listOfOpenCourse.get(input.nextInt()-1);
			input.nextLine();
			
			List<String> course2View=sql.courseToView(openCourseView);
						
			System.out.println("Course ID    |    Title     |     Name      |   capacity |  enrolled  | status  |");
			System.out.println("------------------------------------------------------------------------------");
			
			for(String i:course2View)
			{
				System.out.print(i+"   |   ");
			}
			System.out.println();			
		}	
		else if(searchCourse.equals("2"))
		{
			System.out.println("Enter course ID: ");
			//String enteredCourseID=input.nextLine();
			List<String> listOfCourseID =sql.searchByCourseID(input.nextLine());
			
			System.out.println("Course ID    |    Title     |     Name      |   capacity |  enrolled  | status  |");
			System.out.println("------------------------------------------------------------------------------");
			
			for(String s:listOfCourseID)
			{
				System.out.print(s+"    |     ");
			}
			System.out.println();
		}
		else
		{
			System.out.println("Invalid Input!");
		}
		
		System.out.println("1.Take the course or 2.go back: ");		
		int option = input.nextInt();
		input.nextLine();
		if(option == 1)
		{
			System.out.println("Enter courseID which you want to enroll: ");
			sql.courseClosed(input.nextLine(), ID);
		}
		else if(option == 2)
		{
			Home.student(ID);
		}
	}
	
	public void showClass()
	{
		
		List<String>stuLoginID=sql.myClassSchedule(ID);
		System.out.println("Course ID    |    Title     |     Name      |   capacity |  enrolled  | status  |");
		System.out.println("------------------------------------------------------------------------------");
		
		for(String s:stuLoginID)
		{
			System.out.print(s+"    |     ");
		}
		System.out.println();
		System.out.println("Do you want to view schedule of any other courses? ");
		String viewMore=input.nextLine();
		if(viewMore.equals("y"))
		{
			System.out.println("Enter courseID: ");
			List<String> viewMoreCourses=sql.searchByCourseID(input.nextLine());
			System.out.println("Course ID    |    Title     |     Name      |   capacity |  enrolled  | status  |");
			System.out.println("------------------------------------------------------------------------------");
			
			for(String s:viewMoreCourses)
			{
				System.out.print(s+"    |     ");
			}
			System.out.println();
		}
		else
		{
			Home.student(ID);
		}
	
	}
	public void dropCourse()
	{
		System.out.println("Enter your courseID the one you want to drop:");
		sql.dropMyClass(ID, input.nextLine());		
	}
	public void viewBill()
	{
		List<String>myBill=sql.viewBillInfo(ID);
		int total=0;
		for(String s: myBill)
		{
			int perCourse=1000*(Integer.parseInt(s));
			total+=perCourse;
			
		}
		NumberFormat formatter=NumberFormat.getCurrencyInstance();
		System.out.println("Your total tuitions and fees is "+(formatter.format(total)));
		
	}

	public void teachingSchedule()
	{
		List<String> viewTeachingSchedule=sql.myClassSchedule(ID);
		
	}

}
