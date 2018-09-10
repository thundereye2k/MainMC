package MainMC.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import MainMC.Nothing00.MainPermissions;
import MainMC.Nothing00.functions.Kit;
import MainMC.Nothing00.functions.KitGui;
import MainMC.Nothing00.functions.User;
import MainMC.folders.Conf;
import MainMC.folders.Messages;

public class InventoryInteractEvent implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onClick(InventoryClickEvent e) {

		if (e.getCurrentItem() == null)
			return;

		Messages msg = new Messages();
		Conf config = new Conf();

		Player p = (Player) e.getWhoClicked();
		User user = new User(p.getName());
		MainPermissions s = new MainPermissions(p);

		// LOCKED
		if (user.isLocked()) {
			e.setCancelled(true);
			p.sendMessage(config.getUnLockMessage());
			return;
		}

		// IN JAIL
		if (user.isJailed()) {
			e.setCancelled(true);
			p.sendMessage(msg.getMessage("jailed"));
			return;
		}

		InventoryType GUI = e.getInventory().getType();

		// vedo se è un invenatario
		if (GUI == InventoryType.PLAYER) {
			if (!e.getView().getTopInventory().equals(p.getInventory())) {
				if (!s.hasPermission("main.invsee.interact")) {
					p.sendMessage(msg.getMessage("InvSee"));
					e.setCancelled(true);
					return;
				}
			}
		}

		// vedo se è un enderchest
		if (GUI == InventoryType.ENDER_CHEST) {
			if (!p.getOpenInventory().getTopInventory().equals(p.getEnderChest())) {
				if (!s.hasPermission("main.enderchest.other.interact")) {
					p.sendMessage(msg.getMessage("EnderSee"));
					e.setCancelled(true);
					return;
				}
			}
		}

		// KIT GUI

		ItemStack item;
		if (e.getAction().name().contains("HOTBAR")) {
			item = e.getView().getBottomInventory().getItem(e.getHotbarButton());
		} else {
			item = e.getCurrentItem();
		}

		if (e.getClickedInventory().getTitle().equals("Kits")) {
			e.setCancelled(true);
			if (item == null)
				return;
			e.setCancelled(true);
			if (item.getData().getData() == 13) {
				if (e.getClick().equals(ClickType.RIGHT)) {
					KitGui gui = new KitGui(p);
					gui.showPreview(gui.getCurrentKit(item));
				} else if (e.getClick().equals(ClickType.LEFT)) {
					KitGui gui = new KitGui(p);
					if (gui.isUnlocked(item)) {
						Kit kit = new Kit(gui.getCurrentKit(item), p);
						p.closeInventory();
						kit.giveKit();
						if (!p.hasPermission("main.kits.bypassdelay"))
							kit.setDelay();
					} else {
						gui.showPreview(gui.getCurrentKit(item));
					}
				}
			}
		}
		if (e.getClickedInventory().getTitle().contains("Preview: ")) {
			e.setCancelled(true);
			if (item == null)
				return;
			if (!item.getType().equals(Material.BARRIER))
				return;
			KitGui gui = new KitGui(p);
			gui.openMainGui();
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Messages msg = new Messages();

		if (!e.getInventory().getTitle().contains("Kit:"))
			return;

		Kit kit = new Kit(e.getInventory().getTitle().split(" ")[1], (Player) e.getPlayer());

		kit.createKit(e.getInventory());
		e.getPlayer().sendMessage(msg.getMessage("KitCreate").replaceAll("%kit%", kit.getName()));

	}

}
