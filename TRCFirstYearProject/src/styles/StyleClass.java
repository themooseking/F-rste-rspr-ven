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

	private String enterhovercolor = "#e33635";
	private String defaulthovercolor = "#3D3D3D";
	private String defaulttextcolor = "#e33635";
	private String entertextcolor = "#ffffff";
	private String backgroundcolor = "#242224";

	private String textfont = "Lucida Fax";
	private int textsize = 30;

	private int textunitwidth = 110;
	private int textreaderinsets = 25;

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

	public String enterHoverColor() {
		return enterhovercolor;
	}

	public String defaultHoverColor() {
		return defaulthovercolor;
	}

	public String defaultTextColor() {
		return defaulttextcolor;
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
