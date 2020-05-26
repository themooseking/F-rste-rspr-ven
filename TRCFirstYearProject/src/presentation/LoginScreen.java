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
import styles.GridPaneWithStyle;
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
		VBoxWithStyle vbox = new VBoxWithStyle(company(), selectUser(), userPassword(), wrongPassword(), button());
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: \"" + "#ff1300" + "\";"
				+ "-fx-background-image: url(\"file:resources/background/BackgroundLogin.jpg\"); "
				+ "-fx-background-repeat: no-repeat;");

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	//////////////////////////////
	// TEXTFIELDS
	//////////////////////////////

	private GridPane selectUser() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER);
		grid.setPadding(new Insets(200, 0, 0, 0));

		userLogin = new TextFieldWithStyle("Bruger ID", grid, 0, 0);
		idDigitsCheck(userLogin);

		return grid;
	}

	private GridPane userPassword() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER);
		password = new PasswordFieldWithStyle("Adgangskode", grid, 0, 0);

		return grid;
	}

	//////////////////////////////
	// TEXTFIELD CHECK
	//////////////////////////////

	private void idDigitsCheck(TextFieldWithStyle tf) {
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
	// BUTTON
	//////////////////////////////

	private HBox button() {
		HBox hbox = new HBox(loginButton());
		hbox.setAlignment(Pos.BASELINE_RIGHT);
		hbox.setPadding(new Insets(270, 50, 10, 0));

		return hbox;
	}

	private GridPane loginButton() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER);

		ButtonWithStyle button = new ButtonWithStyle("Login", grid, 0, 1);
		loginButtonEvent(button);

		return grid;
	}

	//////////////////////////////
	// BUTTON EVENT
	//////////////////////////////

	private void loginButtonEvent(ButtonWithStyle button) {
		button.setOnAction(e -> {
			if (!userLogin.getText().isEmpty()) {
				Salesman salesman = new DB_Controller().getSalesman(Integer.parseInt(userLogin.getText()),
						password.getText());
				if (salesman != null) {
					LoggedInST.setUser(salesman);
					new CPRScreen().show();

				} else {
					wrong.setText("Forkert ID eller adgangskode");
				}
			} else {
				wrong.setText("Udfyld tekstfelterne");
			}
		});

	}

	//////////////////////////////
	// LABEL TITLE / ERROR MESSAGE
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
	// SCENE STUFF
	//////////////////////////////

	private void sceneSetup(Scene scene) {
		PrimaryStageST.getStage().setTitle("Ferrari Lånesystem");
		PrimaryStageST.getStage().setScene(scene);
		PrimaryStageST.getStage().show();
	}

}
