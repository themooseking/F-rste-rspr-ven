package styles;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class StyleClass {

	private int sceney = 1000;
	private int scenex = 1800;

	private String grey = "#707070";
	private String red = "#e33635";
	private String white = "#ffffff";
	private String entertextcolor = "#e33635";
	private String backgroundcolor = "#242224";

	private String textfont = "Lucida Fax";
	private int textsize = 20;

	private int textunitwidth = 110;
	private int textreaderinsets = 25;
	private int textreadertextwidth = 300;

	private Border underline = new Border(
			new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(0, 0, 3, 0)));
	private Border dottedunderline = new Border(
			new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, new CornerRadii(0), new BorderWidths(0, 0, 2, 0)));

	private Border elementborder = new Border(
			new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3)));

	public int sceneX() {
		return scenex;
	}

	public int sceneY() {
		return sceney;
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

	public String enterTextColor() {
		return entertextcolor;
	}

	public String backgroundColor() {
		return backgroundcolor;
	}

	public String textFont() {
		return textfont;
	}

	public int textSize() {
		return textsize;
	}

	public int textUnitWidth() {
		return textunitwidth;
	}

	public int textReaderInsets() {
		return textreaderinsets;
	}
	
	public int textReaderTextWidth() {
		return textreadertextwidth;
	}

	public Border underLine() {
		return underline;
	}

	public Border dottedUnderLine() {
		return dottedunderline;
	}
	
	public Border elementBorder() {
		return elementborder;
	}
}
