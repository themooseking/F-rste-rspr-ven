package styles;

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

}
