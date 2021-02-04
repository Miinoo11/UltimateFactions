package de.miinoo.factions.core.gui;

/**
 * @author DotClass
 *
 */

import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.GUIList;
import de.miinoo.factions.core.ui.ui.UIItem;
import de.miinoo.factions.core.ui.ui.UIList;
import de.miinoo.factions.core.ui.gui.GUI;
import de.miinoo.factions.core.ui.gui.GUIItem;
import de.miinoo.factions.core.ui.gui.GUIScrollBar;
import de.miinoo.factions.configuration.messages.GUITags;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.Function;

public class ListGUI<E> extends GUI {

	private final UIList<E> list;

	public ListGUI(Player player, String title, Collection<E> elements, Function<E, UIItem> converter,
                   UIList.OnClickListener<E> action) {
		super(player, title,
				elements.size() > 54 ? 54
						: (elements.size() > 0 && elements.size() % 9 == 0 ? elements.size()
								: ((elements.size() / 9) + 1) * 9));
		list = new GUIList<>(9, elements.size() > 54 ? 5 : size / 9, elements, converter).setOnClickListener(action);
		addElement(0, list);
		if (elements.size() > 54) {
			addElement(46, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 7, list,
					new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
					new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
		}
	}

	public ListGUI<E> setPage(int page) {
		list.setPage(page);
		return this;
	}

	public int getPage() {
		return list.getPage();
	}
	
}