package styles;

import javafx.scene.control.TabPane;

public class TabPaneWithStyle extends TabPane {
	
	StyleClass style = new StyleClass();

	public TabPaneWithStyle() {
		super.getStylesheets().add("/styles/TabPane.css");
		super.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	} 

}
