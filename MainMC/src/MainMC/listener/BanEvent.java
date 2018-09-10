package MainMC.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import MainMC.Nothing00.Utils.Time;
import MainMC.Nothing00.functions.Ip;
import MainMC.Nothing00.functions.User;
import MainMC.folders.Messages;

public class BanEvent implements Listener {

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void playerBanLoginEvent(PlayerLoginEvent e) {
		Messages msg = new Messages();
		User user = new User(e.getPlayer().getName());
		User.userhash.put(e.getPlayer().getName(), user);

		if (!user.exists())
			return;

		Ip ip = new Ip(user.getCurrentIp());
		if (ip.isBanned()) {
			e.setResult(Result.KICK_BANNED);
			e.setKickMessage(msg.getMessage("Banned").replaceAll("%motivation%",
					user.getLastBanMotivation().replaceAll(" [IP]", "")));
			return;
		}

		if (user.isBanned()) {
			if (user.getBanExpire() != null) {
				Time time = new Time(user.getBanExpire());
				if (time.isPastOrPresent()) {
					user.setBanned(false);
					e.setResult(Result.ALLOWED);
					return;
				} else {
					e.setResult(Result.KICK_BANNED);
					e.setKickMessage(
							msg.getMessage("tempBanned").replaceAll("%motivation%", user.getLastBanMotivation())
									.replaceAll("%time%", user.getBanExpire()));
					return;
				}
			} else {
				e.setResult(Result.KICK_BANNED);
				e.setKickMessage(msg.getMessage("Banned").replaceAll("%motivation%", user.getLastBanMotivation()));
			}
		}		
	}

	

}
