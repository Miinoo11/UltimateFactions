package de.miinoo.factions.core.ui.callback;

/**
 * @author DotClass
 *
 */
import java.io.Serializable;

@FunctionalInterface
public interface Callback extends Serializable {

	void call();
	
}
