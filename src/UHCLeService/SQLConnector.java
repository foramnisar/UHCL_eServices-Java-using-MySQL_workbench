package UHCLeService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLConnector
{

	String url="jdbc:mysql://localhost:3306/uhcl_eservice_system?autoReconnect=true&useSSL=false";
	String userName="root";
	String pass="admin";
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	public boolean login(String id, String pass,String tableName)
	{
		String[] cols = new String[2];
		try {		
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(url, userName, pass);
		String query="select Login_ID, Login_password from"+tableName+";";
		st = con.createStatement();
		rs = st.executeQuery(query);	
		while(!rs.next())
		{
			return false;
		}
		while(rs.next())
		{
			cols[0] = rs.getString(1);
			cols[1] = rs.getString(2);
			if(cols[0].equals(id) && cols[1].equals(pass))
			{
				return true;
			}
		}	
		
		st.close();
		con.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}
	public List<String> displayOpenCourses()
	{
		List<String> openCourseList =new ArrayList<>();
		try {		
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(url, userName, pass);
		String openCourse="select CourseID from course where Class_Status='open'";
		st = con.createStatement();
		rs = st.executeQuery(openCourse);		
		while(rs.next())
		{
			openCourseList.add(rs.getString(1));			
		}				
		st.close();
		con.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return openCourseList;	
	}
	
	public List<String> courseToView(String course)
	{
		List<String> viewCourse =new ArrayList<>();
		try {		
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(url, userName, pass);
		String searchCourse="select * from course where CourseID='"+course+"';";
		st = con.createStatement();
		rs = st.executeQuery(searchCourse);
		int cols = rs.getMetaData().getColumnCount();
		while(rs.next())
		{
			for(int i=1;i<=cols;i++)
				viewCourse.add(rs.getString(i));
		}				
		st.close();
		con.close();
		}
		catch(Exception e)
		{
			System.out.println("Error : "+e.getMessage());
		}
		return viewCourse;
	}
	
	
	public List<String> searchByCourseID(String courseID)
	{
		List<String> searchedCourseID =new ArrayList<>();
		try {		
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(url, userName, pass);
		String searchCourse="select * from course where CourseID='"+courseID+"';";
		st = con.createStatement();
		rs = st.executeQuery(searchCourse);
		int cols = rs.getMetaData().getColumnCount();
		while(rs.next())
		{
			for(int i=1;i<=cols;i++)
				searchedCourseID.add(rs.getString(i));
		}				
		st.close();
		con.close();
		}
		catch(Exception e)
		{
			System.out.println("Error : "+e.getMessage());
		}
		return searchedCourseID;
	}
	
	public void courseClosed(String courseID,String StuID)

	{
		List<String> courseCapacity =new ArrayList<>();
		try {		
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(url, userName, pass);
		String maxStudents ="select * from course where CourseID='"+courseID+"'";
		st = con.createStatement();
		rs = st.executeQuery(maxStudents);
		int cols = rs.getMetaData().getColumnCount();
		while(rs.next())
		{
			for(int i=1;i<=cols;i++)
			{
				courseCapacity.add(rs.getString(i));
			}				
		}		
		int c = 0;
		if(Integer.parseInt(courseCapacity.get(3)) >= Integer.parseInt(courseCapacity.get(4)))
		{
			if(courseCapacity.get(5).equals("open"))
			{
				String enrollStudent ="insert into course (Students_Enrolled, CourseID) values ('"+StuID+"','"+courseID+"');";
				c = Integer.parseInt(courseCapacity.get(4)) + 1;
				String enrollStuCap="update course set Students_Enrolled="+c+" where CourseID='"+courseID+"';";
				st = con.createStatement();
				rs = st.executeQuery(enrollStudent);
				rs = st.executeQuery(enrollStuCap);
				System.out.println("Enrolled successfully!");
			}						
		}

		if(Integer.parseInt(courseCapacity.get(3)) == c)
		{
			String updateStatus ="update course set Class_Staus='close' where CourseID='"+courseID+"';";
			st = con.createStatement();
			rs = st.executeQuery(updateStatus);
			System.out.println("Class is full!");
		}		
		st.close();
		con.close();
		}
		catch(Exception e)
		{
			System.out.println("Error : "+e.getMessage());
		}		
		
	}
 
	public List<String> myClassSchedule(String loginID)
	{
		List<String> mySchedule =new ArrayList<>();
		try {		
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(url, userName, pass);
		String schedule="select CourseID, Title,Instructor_Info,Stu_LoginID from course natural join enrolled_students;";
		st = con.createStatement();
		rs = st.executeQuery(schedule);
		int cols = rs.getMetaData().getColumnCount();
		while(rs.next())
		{
			for(int i=1;i<=cols;i++)
				mySchedule.add(rs.getString(i));
		}				
		st.close();
		con.close();
		}
		catch(Exception e)
		{
			System.out.println("Error : "+e.getMessage());
		}
		return mySchedule;	
	}
	public void dropMyClass(String studentLogin, String courseID)
	{
		try {		
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, userName, pass);
			String schedule="delete from enrolled_students where Stu_LoginID='"+studentLogin+"' and CourseID='"+courseID+"';";
			st = con.createStatement();
			rs = st.executeQuery(schedule);
			
			st.close();
			con.close();
			}
			catch(Exception e)
			{
				System.out.println("Error : "+e.getMessage());
			}
			System.out.println(courseID+" dropped successfully!");
		
	}
	public List<String> viewBillInfo(String studentLogin)
	{	List<String> myClasses =new ArrayList<>();
		try {		
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, userName, pass);
			String countCourses="select count (Stu_LoginID) as Total_Courses_Enrolled from enrolled_students;";
			st = con.createStatement();
			rs = st.executeQuery(countCourses);
			while(rs.next())
			{
				myClasses.add(rs.getString(1));			
			}				
			
			st.close();
			con.close();
			}
			catch(Exception e)
			{
				System.out.println("Error : "+e.getMessage());
			}
			return myClasses;
    }
}
