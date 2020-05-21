package presentation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.DB_Controller;
import logic.Salesman;
import styles.ButtonWithStyle;
import styles.GridPaneCenter;
import styles.PasswordFieldWithStyle;
import styles.StyleClass;
import styles.TextFieldWithStyle;
import styles.VBoxWithStyle;

public class LoginScreen {

	private StyleClass style = new StyleClass();
	private PasswordFieldWithStyle password;
	private TextFieldWithStyle userLogin;
	private Label wrong;

	public void show() {
		VBoxWithStyle vbox = new VBoxWithStyle(company(), /*title(),*/ selectUser(), userPassword(), wrongPassword(), buttons());
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: \"" + "#ff1300" + "\";"
				+ "-fx-background-image: url(\"file:resources/background/BackgroundLogin.jpg\"); "
				+ "-fx-background-repeat: no-repeat;");

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	private GridPane selectUser() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);
		grid.setPadding(new Insets(250, 0, 0, 0));

		userLogin = new TextFieldWithStyle("Bruger ID", grid, 0, 0);
		durationEvent(userLogin);
		
		return grid;
	}
	
	//////////////////////////////	
	// PasswordField
	//////////////////////////////

	private GridPane userPassword() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);
		password = new PasswordFieldWithStyle("Adgangskode", grid, 0, 0);
		
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
			Salesman salesman = new DB_Controller().getSalesman(Integer.parseInt(userLogin.getText()), password.getText());
			if (salesman != null) {
				LoggedInST.setUser(salesman);
				new CPRScreen().show();
			}
			else {
				wrong.setText("Forkert ID eller adgangskode");
			}
		});

		return grid;
	}
	
	//////////////////////////////
	// TEXTFIELD EVENTS
	//////////////////////////////
	
	private void durationEvent(TextFieldWithStyle tf) {
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue,
					final String newValue) {
				if (!newValue.matches("\\d*")) {
					tf.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}
	
	//////////////////////////////
	// Label Title
	//////////////////////////////

	private Label company() {
		Label label = new Label("Lånesystem");
		label.setPadding(new Insets(0, 0, 0, 0));
		label.setFont(Font.loadFont(style.titleFont(), 120));
		label.setTextFill(Color.web(style.black()));
		
		return label;
	} 

	private Label wrongPassword() {
		wrong = new Label("");
		wrong.setPadding(new Insets(0, 0, 0, 0));
		wrong.setFont(Font.font(style.textFont(), FontWeight.BOLD, 22));
		wrong.setStyle("-fx-effect: dropShadow(gaussian, white, 2, 1, 0, 0);");
		wrong.setTextFill(Color.web(style.red()));
		
		return wrong;
	}
	
	//////////////////////////////
	// Scene stuff
	//////////////////////////////

	private void sceneSetup(Scene scene) {
		PrimaryStageST.getStage().setTitle("Ferrari LÃ¥nesystem");
		PrimaryStageST.getStage().setScene(scene);
		PrimaryStageST.getStage().show();
	}

}
