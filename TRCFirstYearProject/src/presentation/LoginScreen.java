package presentation;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
		VBoxWithStyle vbox = new VBoxWithStyle(company(), title(), selectUser(), buttons());
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle(
				"-fx-background-color: \"" + new StyleClass().backgroundColor() + "\";"
			  + "-fx-background-image: url(\"file:resources/background/BackgroundLogin.jpg\"); "
			  + "-fx-background-repeat: no-repeat;");

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

	//////////////////////////////
	// Buttons
	//////////////////////////////

	private HBox buttons() {
		HBox hbox = new HBox(loginButton());
		hbox.setAlignment(Pos.BASELINE_RIGHT);
		hbox.setPadding(new Insets(270, 50, 0, 0));

		return hbox;
	}

	private GridPane loginButton() {
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
		Label label = new Label("The Red Car L�ne System");
		label.setFont(Font.loadFont("file:resources/fonts/FerroRosso.ttf", 120));
		label.setTextFill(Color.web(new StyleClass().grey()));
		return label;
	}

	private Label company() {
		Label label = new Label("Den Regionale Ferrari Forhandler");
		label.setPadding(new Insets(240, 0, 0, 0));
		label.setFont(Font.loadFont("file:resources/fonts/FerroRosso.ttf", 40));
		label.setTextFill(Color.web(new StyleClass().grey()));
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
