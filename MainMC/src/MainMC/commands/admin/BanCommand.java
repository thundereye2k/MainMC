package MainMC.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import MainMC.Nothing00.MainPermissions;
import MainMC.Nothing00.Utils.Punishment;
import MainMC.Nothing00.Utils.Time;
import MainMC.Nothing00.functions.User;
import MainMC.folders.Conf;
import MainMC.folders.Messages;

public class BanCommand implements CommandExecutor {

	public static String[] getCommands() {
		String[] array = { "ban", "unban", "unbanall", "tempban" };
		return array;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

		MainPermissions psender = new MainPermissions(sender);

		if (sender instanceof Player || sender instanceof ConsoleCommandSender) {

			if (cmd.getName().equalsIgnoreCase("ban")) {
				Messages msg = new Messages();
				if (psender.hasPermission("main.ban")) {
					if (args.length == 1) {
						User user = new User(args[0]);
						if (user.exists()) {
							if (user.isUntouch()) {
								sender.sendMessage(msg.getMessage("Untouchable"));
								return true;
							}

							if (user.isBanned()) {
								sender.sendMessage(msg.getMessage("AlreadyBanned"));
								return true;
							}

							Conf config = new Conf();
							user.setBanned(true);
							user.setBanAuthor(sender.getName());
							user.setLastBanMotivation(config.getBanDefaultMotiv());
							Punishment punish = new Punishment(user.getName(), sender.getName(),
									config.getBanDefaultMotiv());
							punish.registerPunish("NORMAL", "BAN");
							sender.sendMessage(msg.getMessage("Ban").replaceAll("%player%", user.getName()));
							if (user.isOnline()) {
								user.getPlayer().kickPlayer(msg.getMessage("Banned").replaceAll("%motivation%",
										config.getBanDefaultMotiv()));
							}
							User.sendAllMessage(msg.getMessage("BanNotify").replaceAll("%player%", user.getName())
									.replaceAll("%admin%", sender.getName())
									.replaceAll("%motivation%", config.getBanDefaultMotiv()));
							return true;
						} else {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;
						}
					} else if (args.length > 2) {
						User user = new User(args[0]);
						if (user.exists()) {
							if (user.isUntouch()) {
								sender.sendMessage(msg.getMessage("Untouchable"));
								return true;
							}

							if (user.isBanned()) {
								sender.sendMessage(msg.getMessage("AlreadyBanned"));
								return true;
							}

							String motiv = "";
							for (int i = 1; i < args.length; i++) {
								motiv += " " + args[i];
							}

							user.setBanned(true);
							user.setBanAuthor(sender.getName());
							user.setLastBanMotivation(motiv);
							Punishment punish = new Punishment(user.getName(), sender.getName(), motiv);
							punish.registerPunish("NORMAL", "BAN");
							sender.sendMessage(msg.getMessage("Ban").replaceAll("%player%", user.getName()));
							if (user.isOnline()) {
								user.getPlayer().kickPlayer(msg.getMessage("Banned").replaceAll("%motivation%", motiv));
							}
							User.sendAllMessage(msg.getMessage("BanNotify").replaceAll("%player%", user.getName())
									.replaceAll("%admin%", sender.getName()).replaceAll("%motivation%", motiv));
							return true;
						} else {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;
						}
					} else {
						sender.sendMessage("§rUsage: /ban <player> [motivation]");
					}
				} else {
					sender.sendMessage(msg.getMessage("No-Perm"));
					return true;
				}
			}

			if (cmd.getName().equalsIgnoreCase("tempban")) {
				Messages msg = new Messages();
				if (psender.hasPermission("main.tempban")) {
					if (args.length == 0) {
						sender.sendMessage("§rUsage: /tempban <player> <d/y/h> [motivation]");
						return true;
					}
					User user = new User(args[0]);
					if (args.length == 2) {
						if (user.exists()) {
							if (user.isUntouch()) {
								sender.sendMessage(msg.getMessage("Untouchable"));
								return true;
							}

							if (user.isBanned()) {
								sender.sendMessage(msg.getMessage("AlreadyBanned"));
								return true;
							}

							if (Time.isTimeFormat(args[1])) {
								Time time = new Time(args[1]);
								String pena = "";
								if (time.isYear()) {
									Time timeyear = new Time(null, null, args[1], null, null, null);
									int year = timeyear.translateDay();
									timeyear = new Time(Time.getToDay());
									pena = timeyear.addTime(0, 0, year, 0, 0, 0);
								} else if (time.isDay()) {
									Time timeday = new Time(args[1], null, null, null, null, null);
									int day = timeday.translateDay();
									timeday = new Time(Time.getToDay());
									pena = timeday.addTime(day, 0, 0, 0, 0, 0);
								} else if (time.isHour()) {
									Time timehour = new Time(null, null, null, args[1], null, null);
									int hour = timehour.translateHour();
									timehour = new Time(Time.getToDay());
									pena = timehour.addTime(0, 0, 0, hour, 0, 0);
								} else {
									sender.sendMessage(msg.getMessage("Args"));
									return true;
								}

								Conf config = new Conf();
								user.setBanned(true);
								user.setBanAuthor(sender.getName());
								user.setBanExpire(pena);
								user.setLastBanMotivation(config.getBanDefaultMotiv());
								Punishment punish = new Punishment(user.getName(), sender.getName(),
										config.getBanDefaultMotiv());
								punish.registerPunish("TEMPORARILY_" + args[1], "BAN");
								sender.sendMessage(msg.getMessage("tempBan").replaceAll("%player%", user.getName())
										.replaceAll("%time%", args[1]));
								if (user.isOnline()) {
									user.getPlayer()
											.kickPlayer(msg.getMessage("tempBanned")
													.replaceAll("%motivation%", config.getBanDefaultMotiv())
													.replaceAll("%time%", pena));
								}
								User.sendAllMessage(msg.getMessage("BanNotify").replaceAll("%player%", user.getName())
										.replaceAll("%admin%", sender.getName()).replaceAll("%motivation%", args[1]));
								return true;
							} else {
								sender.sendMessage(msg.getMessage("Args"));
								return true;
							}

						} else {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;
						}
					} else if (args.length > 2) {
						if (user.exists()) {
							if (user.isUntouch()) {
								sender.sendMessage(msg.getMessage("Untouchable"));
								return true;
							}

							if (user.isBanned()) {
								sender.sendMessage(msg.getMessage("AlreadyBanned"));
								return true;
							}

							if (Time.isTimeFormat(args[1])) {
								Time time = new Time(args[1]);
								String pena = "";
								if (time.isYear()) {
									Time timeyear = new Time(null, null, args[1], null, null, null);
									int year = timeyear.translateDay();
									timeyear = new Time(Time.getToDay());
									pena = timeyear.addTime(0, 0, year, 0, 0, 0);
								} else if (time.isDay()) {
									Time timeday = new Time(args[1], null, null, null, null, null);
									int day = timeday.translateDay();
									timeday = new Time(Time.getToDay());
									pena = timeday.addTime(day, 0, 0, 0, 0, 0);
								} else if (time.isHour()) {
									Time timehour = new Time(null, null, null, args[1], null, null);
									int hour = timehour.translateHour();
									timehour = new Time(Time.getToDay());
									pena = timehour.addTime(0, 0, 0, hour, 0, 0);
								} else {
									sender.sendMessage(msg.getMessage("Args"));
									return true;
								}

								String motiv = "";
								for (int i = 1; i < args.length; i++) {
									motiv += " " + args[i];
								}

								user.setBanned(true);
								user.setBanAuthor(sender.getName());
								user.setBanExpire(pena);
								user.setLastBanMotivation(motiv);
								Punishment punish = new Punishment(user.getName(), sender.getName(), motiv);
								punish.registerPunish("TEMPORARILY_" + args[1], "BAN");
								sender.sendMessage(msg.getMessage("tempBan").replaceAll("%player%", user.getName())
										.replaceAll("%time%", args[1]));
								if (user.isOnline()) {
									user.getPlayer().kickPlayer(msg.getMessage("tempBanned")
											.replaceAll("%motivation%", motiv).replaceAll("%time%", pena));
								}
								User.sendAllMessage(msg.getMessage("BanNotify").replaceAll("%player%", user.getName())
										.replaceAll("%admin%", sender.getName()).replaceAll("%motivation%", args[1]));
								return true;
							} else {
								sender.sendMessage(msg.getMessage("Args"));
								return true;
							}

						} else {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;
						}
					} else {
						sender.sendMessage("§rUsage: /tempban <player> <d/y/h> [motivation]");
					}
				} else {
					sender.sendMessage(msg.getMessage("No-Perm"));
					return true;
				}
			}

			if (cmd.getName().equalsIgnoreCase("unban")) {
				Messages msg = new Messages();
				if (psender.hasPermission("main.unban")) {
					if (args.length == 1) {
						User user = new User(args[0]);
						if (user.exists()) {
							if (user.isBanned()) {
								user.setBanned(false);
								sender.sendMessage(msg.getMessage("Unban").replaceAll("%player%", user.getName()));
								return true;
							} else {
								sender.sendMessage(msg.getMessage("NoBanned"));
								return true;
							}
						} else {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;
						}
					} else {
						sender.sendMessage("§rUsage: /unban <player>");
					}
				} else {
					sender.sendMessage(msg.getMessage("No-Perm"));
					return true;
				}
			}

			if (cmd.getName().equalsIgnoreCase("unbanall")) {
				Messages msg = new Messages();
				if (psender.hasPermission("main.unbanall")) {
					for (User user : User.getUsers()) {
						if (user.isBanned()) {
							user.setBanned(false);
						}
					}
					sender.sendMessage(msg.getMessage("Unban").replaceAll("%player%", "All"));
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
