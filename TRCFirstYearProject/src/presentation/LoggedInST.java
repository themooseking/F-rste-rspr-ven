package presentation;

import logic.Salesman;

public class LoggedInST {
	
	private static LoggedInST inst = null;
	private static Salesman user;
	
	private LoggedInST() {		
	}
	
	public static LoggedInST instance() {
		
		if (inst == null) {
			inst = new LoggedInST();
		}				
		return inst;		
	}

	public static Salesman getUser() {
		return user;
	}

	public static void setUser(Salesman user) {
		LoggedInST.user = user;
	}	
}
