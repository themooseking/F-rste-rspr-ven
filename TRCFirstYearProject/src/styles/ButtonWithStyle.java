package styles;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ButtonWithStyle extends Button {
	
	StyleClass style = new StyleClass();

	public ButtonWithStyle(String buttonText, GridPane grid, int col, int row) {
		super.setText(buttonText);
		super.setFont(Font.font(style.textFont(), FontWeight.BOLD, 30));
		super.setMinSize(300, 90);

		defaultEffect(this);

		super.onMouseEnteredProperty().set(e -> enterEffect(this));
		super.onMouseExitedProperty().set(e -> defaultEffect(this));
		
		GridPane.setConstraints(this, col, row);
		grid.getChildren().add(this);
	}
	
	private void enterEffect(Button obj) {
		BackgroundFill background_fill = new BackgroundFill(Color.web(style.grey()), new CornerRadii(4), Insets.EMPTY);
		Background background = new Background(background_fill);
		
		setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.BLACK, 10, 0, 0, 0));

		obj.setBackground(background);
		obj.setCursor(Cursor.HAND);
		obj.setTextFill(Color.web(style.enterTextColor()));
	}

	private void defaultEffect(Button obj) {
		BackgroundFill background_fill = new BackgroundFill(Color.web(style.grey()), new CornerRadii(4), Insets.EMPTY);
		Background background = new Background(background_fill);
		
		setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.BLACK, 0, 0, 0, 0));

		obj.setBackground(background);
		obj.setCursor(Cursor.DEFAULT);
		obj.setTextFill(Color.web(style.white()));
	}

}
