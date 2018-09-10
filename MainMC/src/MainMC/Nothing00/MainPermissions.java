package MainMC.Nothing00;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import MainMC.folders.Conf;

public class MainPermissions {

	private CommandSender sender;

	public MainPermissions(CommandSender sender) {
		this.sender = sender;
	}

	public MainPermissions(Player sender) {
		this.sender = sender;
	}

	public boolean hasPermission(String perm) {
		if (this.sender.isOp())
			return true;
		Conf config = new Conf();
		if (!config.usePermissionsPlugin() && !PermissionsPlus.active())
			return config.getPermissions().contains(perm.replaceAll("main.", ""));
		return this.sender.hasPermission(perm);
	}

}
