package presentation;

import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import logic.DB_Controller;
import styles.ButtonWithStyle;
import styles.GridPaneCenter;
import styles.StyleClass;
import styles.TextFieldWithStyle;
import styles.VBoxWithStyle;

public class CPRScreen {

	private StyleClass style = new StyleClass();
	private DB_Controller controller = new DB_Controller(); 
	private TextFieldWithStyle textfield;
	private Button continueButton;

	public void cprUI() {
		VBoxWithStyle vbox = new VBoxWithStyle(title(), cprTextfield(), buttons());
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(0, 0, 0, 0));
		vbox.setStyle(
				"-fx-background-color: \"" + new StyleClass().backgroundColor() + "\";"
			  + "-fx-background-image: url(\"file:resources/background/BackgroundCPR.jpg\"); "
			  + "-fx-background-repeat: no-repeat;");

		Scene scene = new Scene(vbox, style.sceneX(), style.sceneY());
		sceneSetup(scene);
	}

	private GridPane cprTextfield() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);
		grid.setPadding(new Insets(280,0,322,0));

		textfield = new TextFieldWithStyle("CPR-Number", grid, 0, 0);
		textfield.setText("310396-159");

		textfield.setOnKeyReleased(new EventHandler<KeyEvent>() {
			int tfl = 1;

			@Override
			public void handle(KeyEvent keyEvent) {

				tfl = textfield.getLength();
				if (tfl == 11) {
					continueButton.setDisable(false);
				} else {
					continueButton.setDisable(true);
				}

				textfield.end();

				if (keyEvent.getCode() == KeyCode.ENTER) {
					System.out.println("Hej");
				}

				if (keyEvent.getCode().isDigitKey()) {
					if (tfl == 6) {
						textfield.setText(textfield.getText() + "-");
						textfield.end();
					}
				}

				if (keyEvent.getCode() == KeyCode.BACK_SPACE && tfl == 7) {
					textfield.setText(textfield.getText().substring(0, 6));
					textfield.end();
				}

				textfield.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (!newValue.matches("\\d*")) {
							if (tfl < 6) {
								textfield.setText(newValue.replaceAll("[^\\d]", ""));
							}
						}
					}
				});
			}
		});

		return grid;
	}

	//////////////////////////////
	// Buttons
	//////////////////////////////

	private HBox buttons() {
		HBox hbox = new HBox(logoutButton(), showProposalsButton(), verifyProposalsButton(), continueButton());
		hbox.setAlignment(Pos.BASELINE_RIGHT);
		hbox.setPadding(new Insets(0, 50, 0, 0));

		return hbox;
	}

	private GridPane continueButton() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);

		continueButton = new ButtonWithStyle("Vælg", grid, 0, 3);
		continueButton.setDisable(true);
		continueButton.setOnAction(e -> {
			try {
				new ProposalOverview().customerUI(textfield.getText());
			} catch (NullPointerException e2) {
				Alert wrongCpr = new Alert(AlertType.NONE, ("Ingen kunder fundet med cpr-nummeret: " + textfield.getText() + "."), ButtonType.OK);
				Optional<ButtonType> result = wrongCpr.showAndWait();
			}
		});

		return grid;
	}

	private GridPane verifyProposalsButton() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);

		if ((LoggedInST.getUser().getTitle()).equals("Salgschef")) {
			ButtonWithStyle button = new ButtonWithStyle("Godkend", grid, 0, 2);
			button.setContentDisplay(ContentDisplay.CENTER);
			int numAwaiting = controller.getNumAwaiting();
			if(numAwaiting > 0) {
				button.setGraphic(noti(Integer.toString(numAwaiting)));
			}
			button.setOnAction(e -> {
				new ProposalOverview().cosUI();
			});
		}

		return grid;
	}

	private GridPane showProposalsButton() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);

		ButtonWithStyle button = new ButtonWithStyle("L�netilbud", grid, 0, 1);
		button.setContentDisplay(ContentDisplay.CENTER);
		int numOngoing = controller.getNumOngoing(LoggedInST.getUser());
		if(numOngoing > 0) {
			button.setGraphic(noti(Integer.toString(numOngoing)));
		}
		button.setOnAction(e -> {
			new ProposalOverview().salesmanUI();
			
		});

		return grid;
	}

	private GridPane logoutButton() {
		GridPaneCenter grid = new GridPaneCenter(Pos.CENTER);

		ButtonWithStyle button = new ButtonWithStyle("Log ud", grid, 0, 1);
		button.setOnAction(e -> {
			new LoginScreen().loginUI();
		});

		return grid;
	}
	
	//////////////////////////////
	// Circle
	//////////////////////////////

	private Node noti(String number) {
		GridPane p = new GridPane();
		p.setTranslateY(-40);
		p.setTranslateX(145);
		
		Label lab = new Label(number);
		lab.setStyle("-fx-text-fill:white");
		lab.setFont(Font.loadFont(style.titleFont(), 28));
		lab.setTextFill(Color.web(style.black()));
		lab.setPadding(new Insets (0, 0, 0, 12));
		
		Circle circle = new Circle(20, Color.web("#060606"));
		circle.setStrokeWidth(2.0);
		circle.setStyle("-fx-background-insets: 0 0 -1 0, 0, 0, 0;");
		circle.setSmooth(true);
		p.getChildren().addAll(circle, lab);
		return p;
	}

	//////////////////////////////
	// Label Title
	//////////////////////////////

	private Label title() {
		Label label = new Label("Kundens CPR-nummer");
		label.setPadding(new Insets(0, 0, 0, 0));
		label.setFont(Font.loadFont(style.titleFont(), 120));
		label.setTextFill(Color.web(style.black()));
		return label;
	}

	//////////////////////////////
	// Scene stuff
	//////////////////////////////

	private void sceneSetup(Scene scene) {
		PrimaryStageST.getStage().setTitle("Ferrari Lånesystem");
		PrimaryStageST.getStage().setScene(scene);
		PrimaryStageST.getStage().show();
	}

}
