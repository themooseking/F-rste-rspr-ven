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
			  + "-fx-background-image: url(\"https://png2.cleanpng.com/sh/bd9a65de8a2b04505409571d2c2f04bb/L0KzQYm3V8E2N5Nsi5H0aYP2gLBuTgNkfZVqittqLXbogsPokvkua5J3RdNAdHAwccfwj71kd6R5iudFaXBxeX6BUcUubppmRd54Z3Awdrb5kvFzcV46eqZrYna6dYW6hsg0Pl87SqsBNkC3Q4K8U8E3QWE9T6o7N0i3PsH1h5==/kisspng-scuderia-ferrari-car-auto-avio-costruzioni-815-fia-logo-ferrari-5b4bbf7e43f836.6296604315316908782784.png\"); "
			  + "-fx-background-repeat: no-repeat;"
			  + "-fx-background-position: top;"
			  + "-fx-background-size: 250px;");
	}
}
