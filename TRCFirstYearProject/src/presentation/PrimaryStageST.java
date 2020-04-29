package presentation;

import javafx.stage.Stage;

public class PrimaryStageST {
	
	private static PrimaryStageST inst = null;
	private static Stage primaryStage;
	
	private PrimaryStageST() {		
	}
	
	public static PrimaryStageST instance() {
		
		if (inst == null) {
			inst = new PrimaryStageST();
		}				
		return inst;		
	}

	public static Stage getStage() {
		return primaryStage;
	}

	public static void setStage(Stage stage) {
		PrimaryStageST.primaryStage = stage;
	}	
}
