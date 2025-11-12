package xpathoy6icj;


public class Student {
	public String id;
	public String knev;
	public String vnev;
	public String bnev;
	public String kor;
	
	public Student(String id, String knev, String vnev, String bnev, String kor) {
		this.id = id;
		this.knev = knev;
		this.vnev = vnev;
		this.bnev = bnev;
		this.kor = kor;
	}
	
	public static void Print(Student student) {
		System.out.println("\nAktuális elem: student");
		
		System.out.println("Hallgató ID: " + student.id);
		
		System.out.println("Keresztnév: " + student.knev);
		
		System.out.println("Vezetéknév: " + student.vnev);
		
		System.out.println("Becenév: " + student.bnev);
		
		System.out.println("Kor: " + student.kor);
	}
	
	public static void Print(Student students[]) {
		for(Student student : students) {
			Student.Print(student);
		}
	}
}
