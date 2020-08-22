package de.miinoo.factions.api.ui.ui;

/**
 * @author DotClass
 *
 */
public interface UIPageable extends UIElement {
	
	int getPage();
	
	boolean setPage(int page);
	
	boolean nextPage();
	
	boolean lastPage();
	
}
