package MainMC.commands;

import org.apache.commons.lang.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import MainMC.Nothing00.MainPermissions;
import MainMC.Nothing00.Utils.Economy;
import MainMC.Nothing00.functions.User;
import MainMC.folders.Conf;
import MainMC.folders.Messages;

@SuppressWarnings("deprecation")
public class EconomyCommand implements CommandExecutor {

	public static String[] getCommands() {

		Conf config = new Conf();

		if (config.economyDisabled())
			return new String[0];
		if (config.economyAddon() && config.useEcoGui()) {
			String[] array = { "pay", "eco" };
			return array;
		} else {
			String[] array = { "baltop", "pay", "eco", "balance" };
			return array;
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

		if (sender instanceof Player || sender instanceof ConsoleCommandSender) {

			Conf config = new Conf();

			if (config.economyDisabled()) {
				Messages msg = new Messages();
				sender.sendMessage(msg.getMessage("NoEco"));
				return true;
			}

			MainPermissions psender = new MainPermissions(sender);

			if (cmd.getName().equalsIgnoreCase("eco")) {
				Messages msg = new Messages();
				if (psender.hasPermission("main.eco")) {
					if (args.length == 3) {
						if (!NumberUtils.isNumber(args[2])) {
							sender.sendMessage(msg.getMessage("Args"));
							return true;
						}
						User user = new User(args[1]);
						if (!user.isOnline() && !args[1].equalsIgnoreCase("all")) {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;
						}

						if (args[0].equalsIgnoreCase("give")) {
							if (psender.hasPermission("main.eco.give")) {
								if (args[1].equalsIgnoreCase("all")) {
									Economy eco = new Economy(Double.parseDouble(args[2]));
									eco.giveAll();
									User.sendAllMessage(
											msg.getMessage("givedMoney").replaceAll("%player%", sender.getName())
													.replace("%balance%", eco.getMoneyToString()));
									sender.sendMessage(
											msg.getMessage("giveMoney").replaceAll("%player%", user.getName())
													.replace("%balance%", eco.getMoneyToString()));
								} else {
									Economy eco = new Economy(user.getPlayer(), Double.parseDouble(args[2]));
									eco.addMoney();
									user.sendMessage(
											msg.getMessage("givedMoney").replaceAll("%player%", sender.getName())
													.replace("%balance%", eco.getMoneyToString()));
									sender.sendMessage(
											msg.getMessage("giveMoney").replaceAll("%player%", user.getName())
													.replace("%balance%", eco.getMoneyToString()));
								}
								return true;
							} else {
								sender.sendMessage(msg.getMessage("No-Perm"));
								return true;
							}
						} else if (args[0].equalsIgnoreCase("take")) {
							if (psender.hasPermission("main.eco.take")) {
								if (args[1].equalsIgnoreCase("all")) {
									Economy eco = new Economy(Double.parseDouble(args[2]));
									eco.takeAll();
									;
									User.sendAllMessage(
											msg.getMessage("takedMoney").replaceAll("%player%", sender.getName())
													.replace("%balance%", eco.getMoneyToString()));
									sender.sendMessage(
											msg.getMessage("takeMoney").replaceAll("%player%", user.getName())
													.replace("%balance%", eco.getMoneyToString()));
								} else {
									Economy eco = new Economy(user.getPlayer(), Double.parseDouble(args[2]));
									eco.removeMoney();
									user.sendMessage(
											msg.getMessage("takedMoney").replaceAll("%player%", sender.getName())
													.replace("%balance%", eco.getMoneyToString()));
									sender.sendMessage(
											msg.getMessage("takeMoney").replaceAll("%player%", user.getName())
													.replace("%balance%", eco.getMoneyToString()));
								}
								return true;
							} else {
								sender.sendMessage(msg.getMessage("No-Perm"));
								return true;
							}
						} else if (args[0].equalsIgnoreCase("set")) {
							if (psender.hasPermission("main.eco.set")) {
								if (args[1].equalsIgnoreCase("all")) {
									Economy eco = new Economy(Double.parseDouble(args[2]));
									eco.setAll();
									sender.sendMessage(msg.getMessage("DONE"));
								} else {
									Economy eco = new Economy(user.getPlayer(), Double.parseDouble(args[2]));
									eco.setMoney();
									sender.sendMessage(msg.getMessage("DONE"));
								}
								return true;
							} else {
								sender.sendMessage(msg.getMessage("No-Perm"));
								return true;
							}
						} else {
							sender.sendMessage("§rUsage: /eco give/set/take <player/all>  <money>");
						}
					} else {
						sender.sendMessage("§rUsage: /eco give/set/take <player/all>  <money>");
					}
				} else {
					sender.sendMessage(msg.getMessage("No-Perm"));
					return true;
				}
			}

			if (cmd.getName().equalsIgnoreCase("balance")) {
				Messages msg = new Messages();
				if (psender.hasPermission("main.balance")) {
					if (args.length == 0) {
						if (!(sender instanceof Player))
							return true;
						Economy eco = new Economy((Player) sender);
						sender.sendMessage(msg.getMessage("Balance").replaceAll("%player%", sender.getName())
								.replace("%balance%", eco.toString()));
						return true;
					} else if (args.length == 1) {
						if (psender.hasPermission("main.balance.other")) {
							User user = new User(args[0]);
							if (user.isOnline()) {
								Economy eco = new Economy(user.getPlayer());
								sender.sendMessage(msg.getMessage("Balance").replaceAll("%player%", user.getName())
										.replace("%balance%", eco.toString()));
								return true;
							} else {
								sender.sendMessage(msg.getMessage("NoPlayer"));
								return true;
							}
						} else {
							sender.sendMessage(msg.getMessage("No-Perm"));
							return true;
						}
					}
				} else {
					sender.sendMessage(msg.getMessage("No-Perm"));
					return true;
				}
			}

			if (cmd.getName().equalsIgnoreCase("pay")) {
				Messages msg = new Messages();
				if (psender.hasPermission("main.pay")) {
					if (args.length == 2) {

						if (!(sender instanceof Player))
							return true;

						if (!NumberUtils.isNumber(args[1])) {
							sender.sendMessage(msg.getMessage("Args"));
							return true;
						}

						if (args[0].equalsIgnoreCase(sender.getName())) {
							sender.sendMessage(msg.getMessage("Self"));
							return true;
						}

						Economy ecosender = new Economy((Player) sender, Double.parseDouble(args[1]));
						User user = new User(args[0]);
						if (user.isOnline()) {

							if (!ecosender.detractable()) {
								sender.sendMessage(msg.getMessage("NoMoney"));
								return true;
							}

							Economy eco = new Economy(user.getPlayer(), Double.parseDouble(args[1]));
							ecosender.removeMoney();
							eco.addMoney();

							user.sendMessage(msg.getMessage("givedMoney").replaceAll("%player%", user.getName())
									.replace("%balance%", eco.getMoneyToString()));
							sender.sendMessage(msg.getMessage("giveMoney").replaceAll("%player%", user.getName())
									.replace("%balance%", eco.getMoneyToString()));
							return true;
						} else {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;
						}

					} else {
						sender.sendMessage("§rUsage: /pay <player> <money>");
					}
				} else {
					sender.sendMessage(msg.getMessage("No-Perm"));
					return true;
				}
			}

			if (cmd.getName().equalsIgnoreCase("baltop")) {
				Messages msg = new Messages();

				if (psender.hasPermission("main.baltop")) {

					sender.sendMessage("§e" + Economy.topToString(5));
					return true;
				} else {
					sender.sendMessage(msg.getMessage("No-Perm"));
					return true;
				}
			}
		}
		return false;
	}
}
