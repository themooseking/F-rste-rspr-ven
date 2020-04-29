package presentation;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Salesman;
import styles.ButtonWithStyle;
import styles.StyleClass;
import styles.ComboBoxWithStyle;
import styles.GridPaneCenter;
import styles.VBoxWithStyle;

public class LoginScreen {

	private ComboBoxWithStyle selectedUser;

	public void loginUI() {
		
		VBoxWithStyle vbox = new VBoxWithStyle(company(), title(), selectUser(), loginSetup());
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, 1800, 980);
		sceneSetup(scene);
	}

	private GridPane selectUser() {
		GridPaneCenter grid = new GridPaneCenter();
		grid.setPadding(new Insets(30));

//		ArrayList<Salesman> userList = new DB_Controller().getSalesmanList();

		Salesman sm1 = new Salesman(12345678, "Johnny Sins", "sins@brazzers.com",
				"SENIOR VICE LEADING EXECUTIVE SUPREME SALESMAN", 1000000000);
		Salesman sm2 = new Salesman(98765432, "Riley Reid", "reid@mylips.com", "SLAVE", 5);
		Salesman sm3 = new Salesman(21586895, "Dean", "dean@eamv.dk", "CEO", 100);
		ArrayList<Salesman> userList = new ArrayList<Salesman>(Arrays.asList(sm1, sm2, sm3));

		selectedUser = new ComboBoxWithStyle(FXCollections.observableArrayList(userList), grid, 0, 0);
		if (LoggedInST.getUser() == null) {
			selectedUser.setValue(userList.get(0));
		} else {
			selectedUser.setValue(LoggedInST.getUser());
		}

		selectedUser.setMinSize(150, 50);

		return grid;
	}

	private GridPane loginSetup() {
		GridPaneCenter grid = new GridPaneCenter();

		ButtonWithStyle btnLogin = new ButtonWithStyle("Login", grid, 0, 1);
		btnLogin.setOnAction(e -> {

		});

		return grid;
	}

	//////////////////////////////
	// Label Title
	//////////////////////////////

	private Label title() {
		Label label = new Label("The Red Car Loan System");
		label.setFont(Font.loadFont("file:resources/fonts/FerroRosso.ttf", 120));
		label.setTextFill(Color.web(new StyleClass().defaultTextColor()));
		return label;
	}
	
	private Label company() {
		Label label = new Label("The Regional Ferrari Dealer");
		label.setFont(Font.loadFont("file:resources/fonts/FerroRosso.ttf", 40));
		label.setTextFill(Color.web(new StyleClass().defaultTextColor()));
		return label;
	}

	//////////////////////////////
	// Scene stuff
	//////////////////////////////

	private void sceneSetup(Scene scene) {
		PrimaryStageST.getStage().setTitle("ALS");
		PrimaryStageST.getStage().setScene(scene);
		PrimaryStageST.getStage().show();
	}

}
