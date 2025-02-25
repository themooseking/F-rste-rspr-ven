package presentation;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.DB_Controller;
import styles.ButtonWithStyle;
import styles.GridPaneWithStyle;
import styles.StyleClass;
import styles.TextFieldWithStyle;
import styles.VBoxWithStyle;

public class CPRScreen {

	private StyleClass style = new StyleClass();
	private DB_Controller controller = new DB_Controller();
	private TextFieldWithStyle textfield;
	private Button continueButton;
	private Label wrong;

	public void show() {
		VBoxWithStyle vbox = new VBoxWithStyle(title(), cprTextfield(), wrongPassword(), buttons());
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: \"" + new StyleClass().backgroundColor() + "\";"
				+ "-fx-background-image: url(\"file:resources/background/BackgroundCPR.jpg\"); "
				+ "-fx-background-repeat: no-repeat;");

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	//////////////////////////////
	// TEXTFIELD
	//////////////////////////////

	private GridPane cprTextfield() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER);
		grid.setPadding(new Insets(280, 0, 0, 0));

		textfield = new TextFieldWithStyle("CPR-Number", grid, 0, 0);
		textFieldEvent();

		return grid;
	}

	private void textFieldEvent() {
		textfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				tfKeyBindings(keyEvent);
			}
		});

		textfield.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue,
					final String newValue) {

				if (!newValue.matches("\\d*") && textfield.getLength() < 5) {
					textfield.setText(newValue.replaceAll("[^\\d]", ""));
				} else {
					tfDashCheck(oldValue, newValue);
				}

				tfLengthCheck(oldValue, newValue);
			}
		});
	}

	//////////////////////////////
	// BINDINGS AND CHECKS
	//////////////////////////////

	private void tfKeyBindings(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ENTER) {
			continueEvent();
		}
	}

	private void tfDashCheck(String oldValue, String newValue) {
		if (oldValue.length() == 6 && newValue.length() > oldValue.length()) {
			textfield.setText(oldValue + "-" + newValue.substring(6, 7));
		}

		if (oldValue.length() == 8 && newValue.length() < oldValue.length()) {
			Platform.runLater(() -> {
				textfield.setText(newValue.substring(0, 6));
				textfield.end();
			});
		}
	}

	private void tfLengthCheck(String oldValue, String newValue) {
		if (newValue.length() == 12) {
			textfield.setText(oldValue);
			continueButton.setDisable(false);
		} else if (newValue.length() == 11) {
			continueButton.setDisable(false);
		} else {
			continueButton.setDisable(true);
		}
	}

	//////////////////////////////
	// BUTTONS
	//////////////////////////////

	private HBox buttons() {
		HBox hbox = new HBox(logoutButton(), showProposalsButton(), verifyProposalsButton(), continueButton());
		hbox.setAlignment(Pos.BASELINE_RIGHT);
		hbox.setPadding(new Insets(0, 50, 0, 0));

		return hbox;
	}

	private GridPane continueButton() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER);

		continueButton = new ButtonWithStyle("V�lg", grid, 0, 3);
		continueButton.setDisable(true);
		continueButton.setOnAction(e -> {
			continueEvent();
		});

		return grid;
	}

	private void continueEvent() {
		try {
			new ProposalOverview().defaultUI(textfield.getText());
		} catch (NullPointerException e2) {
			wrong.setText("Ugyldigt CPR-nummer.");
		}
	}

	private GridPane verifyProposalsButton() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER);

		if ((LoggedInST.getUser().getTitle()).equals("Salgschef")) {
			ButtonWithStyle button = new ButtonWithStyle("Godkend", grid, 0, 2);
			button.setContentDisplay(ContentDisplay.CENTER);
			int numAwaiting = controller.getNumAwaiting();
			if (numAwaiting > 0) {
				button.setGraphic(noti(Integer.toString(numAwaiting)));
			}
			button.setOnAction(e -> {
				new ProposalOverview().cosUI();
			});
		}

		return grid;
	}

	private GridPane showProposalsButton() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER);

		ButtonWithStyle button = new ButtonWithStyle("L�netilbud", grid, 0, 1);
		button.setContentDisplay(ContentDisplay.CENTER);
		int numOngoing = controller.getNumOngoing(LoggedInST.getUser());
		if (numOngoing > 0) {
			button.setGraphic(noti(Integer.toString(numOngoing)));
		}
		button.setOnAction(e -> {
			new ProposalOverview().salesmanUI();

		});

		return grid;
	}

	private GridPane logoutButton() {
		GridPaneWithStyle grid = new GridPaneWithStyle(Pos.CENTER);

		ButtonWithStyle button = new ButtonWithStyle("Log ud", grid, 0, 1);
		button.setOnAction(e -> {
			new LoginScreen().show();
		});

		return grid;
	}

	//////////////////////////////
	// CIRCLE
	//////////////////////////////

	private Node noti(String number) {
		GridPane p = new GridPane();
		p.setTranslateY(-40);
		p.setTranslateX(145);

		Label label = new Label(number);
		label.setStyle("-fx-text-fill:white");
		label.setFont(Font.loadFont(style.titleFont(), 28));
		label.setTextFill(Color.web(style.black()));

		if (Integer.parseInt(number) < 10) {
			label.setPadding(new Insets(0, 0, 0, 12));
		} else {
			label.setPadding(new Insets(0, 0, 0, 4));
		}

		Circle circle = new Circle(20, Color.web("#060606"));
		circle.setStrokeWidth(2.0);
		circle.setStyle("-fx-background-insets: 0 0 -1 0, 0, 0, 0;");
		circle.setSmooth(true);
		p.getChildren().addAll(circle, label);

		return p;
	}

	//////////////////////////////
	// LABEL TITLE / ERROR MESSAGE
	//////////////////////////////

	private Label title() {
		Label label = new Label("Kundens CPR-nummer");
		label.setPadding(new Insets(0, 0, 0, 0));
		label.setFont(Font.loadFont(style.titleFont(), 120));
		label.setTextFill(Color.web(style.black()));
		
		return label;
	}

	private Label wrongPassword() {
		wrong = new Label("");
		wrong.setPadding(new Insets(10, 0, 271, 0));
		wrong.setFont(Font.font(style.textFont(), FontWeight.BOLD, 22));
		wrong.setStyle("-fx-effect: dropShadow(gaussian, white, 2, 1, 0, 0);");
		wrong.setTextFill(Color.web(style.red()));
		
		return wrong;
	}

	//////////////////////////////
	// SCENE STUFF
	//////////////////////////////

	private void sceneSetup(Scene scene) {
		PrimaryStageST.getStage().setTitle("Ferrari L�nesystem");
		PrimaryStageST.getStage().setScene(scene);
		PrimaryStageST.getStage().show();
	}
}
