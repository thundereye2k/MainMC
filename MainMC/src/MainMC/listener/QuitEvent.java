package MainMC.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import MainMC.Nothing00.MainPermissions;
import MainMC.Nothing00.Utils.Combat;
import MainMC.Nothing00.functions.User;
import MainMC.folders.Conf;

public class QuitEvent implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onQuit(PlayerQuitEvent e) {

		Conf config = new Conf();
		User user = new User(e.getPlayer().getName());
		if (!user.exists())
			return;
		user.saveQuitLocation();
		if (User.getUser(e.getPlayer().getName()) == null)
			return;
		User.getUser(e.getPlayer().getName()).saveTime();
		MainPermissions s = new MainPermissions(e.getPlayer());

		// SILENT QUIT
		if (!config.getQuitMessage().equals("none") && config.getQuitMessage() != null
				&& !config.getQuitMessage().isEmpty()) {
			e.setQuitMessage(config.getQuitMessage().replace("{player}", e.getPlayer().getName())
					.replace("{displayname}", e.getPlayer().getDisplayName()));
		}
		if (config.silentQuitJoin()) {
			if (s.hasPermission("main.silentquit")) {
				e.setQuitMessage(null);
			}
		}

		// GOD CANCELLATION
		if (User.getUser(e.getPlayer().getName()).isGod() && config.disbleGodOnQuit()) {
			User.getUser(e.getPlayer().getName()).setGod(false);
		}

		// TPA CANCELLATION
		if (User.getUser(e.getPlayer().getName()).askTpa()) {
			User.getUser(e.getPlayer().getName()).setSendTpa(false);
			User.getUser(User.getUser(e.getPlayer().getName()).getRequest().getName()).setRequest(null);
			User.getUser(User.getUser(e.getPlayer().getName()).getRequest().getName()).setRequestType(2);
		}

		// COMBAT
		Player combat = Combat.onQuit(e.getPlayer());
		if (combat != null)
			combat.sendMessage(config.getExitCombatMessage());

		// DELTE ONLINE USER
		if (User.userhash.containsKey(e.getPlayer().getName())) {
			User.userhash.remove(e.getPlayer().getName());
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onQuit(PlayerKickEvent e) {

		if (!User.userhash.containsKey(e.getPlayer().getName()))
			return;

		Conf config = new Conf();
		User user = new User(e.getPlayer().getName());
		if (!user.exists())
			return;
		user.saveQuitLocation();
		if (User.getUser(e.getPlayer().getName()) == null)
			return;
		User.getUser(e.getPlayer().getName()).saveTime();

		// GOD CANCELLATION
		if (User.getUser(e.getPlayer().getName()).isGod() && config.disbleGodOnQuit()) {
			User.getUser(e.getPlayer().getName()).setGod(false);
		}

		// TPA CANCELLATION
		if (User.getUser(e.getPlayer().getName()).askTpa()) {
			User.getUser(e.getPlayer().getName()).setSendTpa(false);
			User.getUser(User.getUser(e.getPlayer().getName()).getRequest().getName()).setRequest(null);
			User.getUser(User.getUser(e.getPlayer().getName()).getRequest().getName()).setRequestType(2);
		}

		// COMBAT
		Player combat = Combat.onQuit(e.getPlayer());
		if (combat != null)
			combat.sendMessage(config.getExitCombatMessage());

		// DELTE ONLINE USER
		if (User.userhash.containsKey(e.getPlayer().getName())) {
			User.userhash.remove(e.getPlayer().getName());
		}
	}

}
