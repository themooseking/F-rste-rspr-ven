package presentation;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.DB_Controller;
import logic.Salesman;
import styles.ButtonWithStyle;
import styles.ComboBoxWithStyle;
import styles.GridPaneCenter;
import styles.StyleClass;
import styles.VBoxWithStyle;

public class LoginScreen {

	private StyleClass style = new StyleClass();
	private ComboBoxWithStyle selectedUser;

	public void loginUI() {		
		VBoxWithStyle vbox = new VBoxWithStyle(company(), title(), selectUser(), loginSetup());
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	private GridPane selectUser() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);
		grid.setPadding(new Insets(30));

		ArrayList<Salesman> userList = new DB_Controller().getSalesmanList();

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
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);

		ButtonWithStyle button = new ButtonWithStyle("Login", grid, 0, 1);
		button.setOnAction(e -> {
			LoggedInST.setUser((Salesman) selectedUser.getValue());
			new CPRScreen().cprUI();
		});

		return grid;
	}

	//////////////////////////////
	// Label Title
	//////////////////////////////

	private Label title() {
		Label label = new Label("The Red Car Låne System");
		label.setFont(Font.loadFont("file:resources/fonts/FerroRosso.ttf", 120));
		label.setTextFill(Color.web(new StyleClass().defaultTextColor()));
		return label;
	}
	
	private Label company() {
		Label label = new Label("Den Regionale Ferrari Forhandler");
		label.setFont(Font.loadFont("file:resources/fonts/FerroRosso.ttf", 40));
		label.setTextFill(Color.web(new StyleClass().defaultTextColor()));
		return label;
	}

	//////////////////////////////
	// Scene stuff
	//////////////////////////////

	private void sceneSetup(Scene scene) {
		PrimaryStageST.getStage().setTitle("The Red Car");
		PrimaryStageST.getStage().setScene(scene);
		PrimaryStageST.getStage().show();
	}

}
