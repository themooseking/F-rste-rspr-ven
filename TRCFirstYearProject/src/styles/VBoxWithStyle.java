package styles;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class VBoxWithStyle extends VBox {

	public VBoxWithStyle() {
		super();
		background();
	}

	public VBoxWithStyle(double arg0, Node... arg1) {
		super(arg0, arg1);
		background();
	}

	public VBoxWithStyle(double arg0) {
		super(arg0);
		background();
	}

	public VBoxWithStyle(Node... arg0) {
		super(arg0);
		background();
	}

	private void background() {
		super.setStyle(
				"-fx-background-color: \"" + new StyleClass().backgroundColor() + "\";"
			  + "-fx-background-image: url(\"file:resources/background/Background1.jpg\"); "
			  + "-fx-background-repeat: no-repeat;");
	}
}
