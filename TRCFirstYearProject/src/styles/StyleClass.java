package styles;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class StyleClass {

	private int sceneY = 1000;
	private int sceneX = 1800;

	private String grey = "#303030";
	private String red = "#e61900";
	private String white = "#ffffff";
	private String black = "#000000";
	
	private String enterTextColor = "#e33635"; 
	private String backgroundColor = "#242224";

	private String titleFont = "file:resources/fonts/Caladea-Regular.ttf";
	private String textFont = "Lucida Fax";
	private int textSize = 20;

	private int textUnitWidth = 70;
	private int textReaderInsets = 25;
	private int textReaderTextWidth = 300;

	private Border underline = new Border(
			new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(0, 0, 1, 0)));
	private Border sumline = new Border(
			new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(0, 0, 3, 0)));
	private Border dottedUnderline = new Border(
			new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, new CornerRadii(0), new BorderWidths(0, 0, 1, 0)));
 
	private Border elementBorder = new Border(
			new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3)));
	
	private String bold = "-fx-font-weight: BOLD; -fx-text-fill: #4A4A4A;";

	public int sceneX() {
		return sceneX;
	}

	public int sceneY() {
		return sceneY;
	}

	public String grey() {
		return grey;
	}

	public String red() {
		return red;
	}

	public String white() {
		return white;
	}
	
	public String black() {
		return black;
	}

	public String enterTextColor() {
		return enterTextColor;
	}

	public String backgroundColor() {
		return backgroundColor;
	}

	public String textFont() {
		return textFont;
	}
	
	public String titleFont() {
		return titleFont;
	}

	public int textSize() {
		return textSize;
	}

	public int textUnitWidth() {
		return textUnitWidth;
	}

	public int textReaderInsets() {
		return textReaderInsets;
	}
	
	public int textReaderTextWidth() {
		return textReaderTextWidth;
	}

	public Border underLine() {
		return underline;
	}	
	
	public Border sumLine() {
		return sumline;
	}

	public Border dottedUnderLine() {
		return dottedUnderline;
	}
	
	public Border elementBorder() {
		return elementBorder;
	}
	
	public String bold() {
		return bold;
	}
}
