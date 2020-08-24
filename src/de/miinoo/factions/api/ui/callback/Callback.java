package de.miinoo.factions.api.ui.callback;

/**
 * @author DotClass
 *
 */
import java.io.Serializable;

@FunctionalInterface
public interface Callback extends Serializable {

	void call();
	
}
