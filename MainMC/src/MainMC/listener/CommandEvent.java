package MainMC.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import MainMC.Nothing00.MainPermissions;
import MainMC.Nothing00.Utils.AfkManager;
import MainMC.Nothing00.Utils.Combat;
import MainMC.Nothing00.functions.User;
import MainMC.folders.Conf;
import MainMC.folders.Messages;

public class CommandEvent implements Listener {

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onChat(PlayerCommandPreprocessEvent e) {

		Messages msg = new Messages();
		Conf config = new Conf();
		User user = new User(e.getPlayer().getName());
		MainPermissions s = new MainPermissions(e.getPlayer());

		// COMBAT
		if (config.useCombat()) {
			Combat combat = new Combat(e.getPlayer(), e.getPlayer());
			if (combat.isCombat()) {
				e.getPlayer().sendMessage(config.getEnterSenderMessage());
				e.setCancelled(true);
				return;
			}
		}

		// BLACKLIST
		if (config.getCommandBlackList().contains(e.getMessage().split(" ")[0].toLowerCase())
				&& !s.hasPermission("main.bypasscommands")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(config.getBlackListCommandMessage());
			return;
		}

		// LOCKED
		if (user.isLocked()) {
			if (config.getUnLockWhitelist().contains(e.getMessage().split(" ")[0].toLowerCase()))
				return;
			e.setCancelled(true);
			e.getPlayer().sendMessage(config.getUnLockMessage());
			return;
		}

		// IN JAIL
		if (user.isJailed()) {
			if (config.getJailWhitelist().contains(e.getMessage().split(" ")[0].toLowerCase()))
				return;
			if (e.getMessage().split(" ")[0].equalsIgnoreCase("unlockaccount")
					|| e.getMessage().split(" ")[0].toLowerCase().equalsIgnoreCase("keyword"))
				return;
			e.setCancelled(true);
			e.getPlayer().sendMessage(msg.getMessage("jailed"));
			return;
		}

		// CHECK AFK
		AfkManager afk = new AfkManager(e.getPlayer());
		if (config.cancelAfkonMove()) {
			if (afk.isAfk()) {
				afk.removeAfk();
				User.sendAllMessage(msg.getMessage("offAFK").replaceAll("%player%", e.getPlayer().getName()));
			}
		}
		if (config.freezeAfk()) {
			if (afk.isAfk()) {
				if (e.getMessage().split(" ")[0].equalsIgnoreCase("unlockaccount")
						|| e.getMessage().split(" ")[0].toLowerCase().equalsIgnoreCase("keyword"))
					return;
				e.setCancelled(true);
				return;
			}
		}

	}

}
