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

public class PunishCommand implements CommandExecutor {

	public static String[] getCommands() {
		String[] array = {"kick", "kickall", "mute", "tempmute", "unmute", "lookup", "history"};
		return array;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

		if (sender instanceof Player || sender instanceof ConsoleCommandSender) {
			
			MainPermissions psender = new MainPermissions(sender);

			if (cmd.getName().equalsIgnoreCase("kick")) {
				Messages msg = new Messages();
				if (psender.hasPermission("main.kick")) {
					if (args.length == 1) {
						User user = new User(args[0]);
						if (user.isOnline()) {

							if (user.isUntouch()) {
								sender.sendMessage(msg.getMessage("Untouchable"));
								return true;
							}

							Conf config = new Conf();
							String motiv = config.getKickDefaultMotiv();
							user.kick(motiv, sender.getName());
							User.sendAllMessage(msg.getMessage("Kicked") + " " + motiv);
							sender.sendMessage(msg.getMessage("Kick").replaceAll("%player%", user.getName()));
							return true;
						} else {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;
						}
					} else if (args.length > 1) {
						User user = new User(args[0]);
						if (user.isOnline()) {

							if (user.isUntouch()) {
								sender.sendMessage(msg.getMessage("Untouchable"));
								return true;
							}

							String motiv = "";

							for (int i = 1; i < args.length; i++) {
								motiv += " " + args[i].replaceAll("&", "§");
							}

							user.kick(motiv, sender.getName());
							User.sendAllMessage(msg.getMessage("Kicked") + motiv);
							sender.sendMessage(msg.getMessage("Kick").replaceAll("%player%", user.getName()));
							return true;
						} else {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;
						}
					} else {
						sender.sendMessage("§rUsage: /kick <player> [motivation]");
						return true;
					}
				} else {
					sender.sendMessage(msg.getMessage("No-Perm"));
					return true;
				}
			}

			if (cmd.getName().equalsIgnoreCase("kickall")) {
				Messages msg = new Messages();
				if (psender.hasPermission("main.kickall")) {
					if (args.length == 0) {
						Conf config = new Conf();
						String motiv = config.getKickDefaultMotiv();
						User.kickAll(motiv, sender.getName());
						sender.sendMessage(msg.getMessage("Kick").replaceAll("%player%", "All"));
						return true;
					} else if (args.length > 0) {

						String motiv = "";

						for (int i = 0; i < args.length; i++) {
							motiv += " " + args[i].replaceAll("&", "§");
						}

						User.kickAll(motiv, sender.getName());
						sender.sendMessage(msg.getMessage("Kick").replaceAll("%player%", "All"));
						return true;

					} else {
						sender.sendMessage("§rUsage: /kickall [motivation]");
						return true;
					}
				} else {
					sender.sendMessage(msg.getMessage("No-Perm"));
					return true;
				}
			}

			if (cmd.getName().equalsIgnoreCase("mute")) {
				Messages msg = new Messages();
				if (psender.hasPermission("main.mute")) {
					if (args.length == 1) {
						User user = new User(args[0]);
						if (user.isOnline()) {
							if (user.isUntouch()) {
								sender.sendMessage(msg.getMessage("Untouchable"));
								return true;
							}

							if (user.isMuted()) {
								user.setMute(false);
								sender.sendMessage(msg.getMessage("unMute").replaceAll("%player%", user.getName()));
								user.sendMessage(msg.getMessage("unMuted"));
								return true;
							} else {
								user.setMute(true);
								user.setMuteAuthor(sender.getName());
								
								Conf config = new Conf();
								user.setLastMuteMotivation(config.getMuteDefaultMotiv().replaceAll("§", "&"));
								Punishment punish = new Punishment(user.getName(), sender.getName(),
										config.getMuteDefaultMotiv());
								punish.registerPunish("NORMAL", "MUTE");
								user.sendMessage(msg.getMessage("Muted"));
								sender.sendMessage(msg.getMessage("Mute").replaceAll("%player%", user.getName()));
								User.sendAllMessage(msg.getMessage("MuteNotify").replaceAll("%player%", user.getName())
										.replaceAll("%admin%", sender.getName())
										.replaceAll("%motivation%", config.getMuteDefaultMotiv()));
								return true;
							}
						} else {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;
						}
					} else if (args.length > 2) {
						User user = new User(args[0]);
						if (user.isOnline()) {
							if (user.isUntouch()) {
								sender.sendMessage(msg.getMessage("Untouchable"));
								return true;
							}

							if (user.isMuted()) {
								sender.sendMessage(msg.getMessage("AlreadyMuted"));
								return true;
							} else {
								user.setMute(true);
								user.setMuteAuthor(sender.getName());
								String motiv = "";
								for (int i = 1; i < args.length; i++) {
									motiv += " " + args[i];
								}

								Punishment punish = new Punishment(user.getName(), sender.getName(),
										motiv.replaceAll("&", "§"));
								punish.registerPunish("NORMAL", "MUTE");
								user.setLastMuteMotivation(motiv);
								user.sendMessage(msg.getMessage("Muted"));
								sender.sendMessage(msg.getMessage("Mute").replaceAll("%player%", user.getName()));
								User.sendAllMessage(msg.getMessage("MuteNotify").replaceAll("%player%", user.getName())
										.replaceAll("%admin%", sender.getName())
										.replaceAll("%motivation%", motiv.replaceAll("&", "§")));
								return true;
							}
						} else {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;
						}
					} else {
						sender.sendMessage("§rUsage /mute <player> [motivation]");
					}
				} else {
					sender.sendMessage(msg.getMessage("No-Perm"));
					return true;
				}
			}

			if (cmd.getName().equalsIgnoreCase("tempmute")) {
				Messages msg = new Messages();
				if (psender.hasPermission("main.tempmute")) {
					if (args.length == 2) {
						User user = new User(args[0]);
						if (user.isOnline()) {
							if (user.isUntouch()) {
								sender.sendMessage(msg.getMessage("Untouchable"));
								return true;
							}
							if (!user.isMuted()) {
								if (Time.isTimeFormat(args[1])) {
									Time time = new Time(args[1]);
									String pena = "";
									if (time.isDay()) {
										Time timeday = new Time(args[1], null, null, null, null, null);
										int day = timeday.translateDay();
										timeday = new Time(Time.getToDay());
										pena = timeday.addTime(day, 0, 0, 0, 0, 0);
									} else if (time.isHour()) {
										Time timehour = new Time(null, null, null, args[1], null, null);
										int hour = timehour.translateHour();
										timehour = new Time(Time.getToDay());
										pena = timehour.addTime(0, 0, 0, hour, 0, 0);
									} else if (time.isMinute()) {
										Time timeminute = new Time(null, null, null, null, args[1], null);
										int minute = timeminute.translateMinutes();
										timeminute = new Time(Time.getToDay());
										pena = timeminute.addTime(0, 0, 0, 0, minute, 0);
									} else if (time.isSecond()) {
										Time timesecond = new Time(null, null, null, null, null, args[1]);
										int second = timesecond.translateSeconds();
										timesecond = new Time(Time.getToDay());
										pena = timesecond.addTime(0, 0, 0, 0, 0, second);
									} else {
										sender.sendMessage(msg.getMessage("Args"));
										return true;
									}
									user.setMute(true);
									user.setMuteAuthor(sender.getName());
									user.setMuteExpire(pena);
									Conf config = new Conf();
									user.setLastMuteMotivation(config.getMuteDefaultMotiv().replaceAll("§", "&"));
									Punishment punish = new Punishment(user.getName(), sender.getName(),
											config.getMuteDefaultMotiv());
									punish.registerPunish("TEMPORARILY_" + args[1], "MUTE");
									user.sendMessage(msg.getMessage("tempMuted").replaceAll("%time%", pena));
									sender.sendMessage(msg.getMessage("Mute").replaceAll("%player%", user.getName()));
									User.sendAllMessage(
											msg.getMessage("MuteNotify").replaceAll("%player%", user.getName())
													.replaceAll("%admin%", sender.getName())
													.replaceAll("%motivation%", config.getMuteDefaultMotiv()));
									return true;
								} else {
									sender.sendMessage(msg.getMessage("Args"));
									return true;
								}
							} else {
								sender.sendMessage(msg.getMessage("AlreadyMuted"));
								return true;
							}
						} else {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;
						}
					} else if (args.length > 3) {
						User user = new User(args[0]);
						if (user.isOnline()) {
							if (user.isUntouch()) {
								sender.sendMessage(msg.getMessage("Untouchable"));
								return true;
							}
							if (!user.isMuted()) {
								if (Time.isTimeFormat(args[1])) {
									Time time = new Time(args[1]);
									String pena = "";
									if (time.isDay()) {
										Time timeday = new Time(args[1], null, null, null, null, null);
										int day = timeday.translateDay();
										timeday = new Time(Time.getToDay());
										pena = timeday.addTime(day, 0, 0, 0, 0, 0);
									} else if (time.isHour()) {
										Time timehour = new Time(null, null, null, args[1], null, null);
										int hour = timehour.translateHour();
										timehour = new Time(Time.getToDay());
										pena = timehour.addTime(0, 0, 0, hour, 0, 0);
									} else if (time.isMinute()) {
										Time timeminute = new Time(null, null, null, null, args[1], null);
										int minute = timeminute.translateMinutes();
										timeminute = new Time(Time.getToDay());
										pena = timeminute.addTime(0, 0, 0, 0, minute, 0);
									} else if (time.isSecond()) {
										Time timesecond = new Time(null, null, null, null, null, args[1]);
										int second = timesecond.translateSeconds();
										timesecond = new Time(Time.getToDay());
										pena = timesecond.addTime(0, 0, 0, 0, 0, second);
									} else {
										sender.sendMessage(msg.getMessage("Args"));
										return true;
									}
									user.setMute(true);
									user.setMuteAuthor(sender.getName());
									user.setMuteExpire(pena);

									String motiv = "";
									for (int i = 2; i < args.length; i++) {
										motiv += " " + args[i];
									}
									user.setLastMuteMotivation(motiv);
									Punishment punish = new Punishment(user.getName(), sender.getName(),
											motiv.replaceAll("&", "§"));
									punish.registerPunish("TEMPORARILY_" + args[1], "MUTE");
									user.sendMessage(msg.getMessage("tempMuted").replaceAll("%time%", pena));
									sender.sendMessage(msg.getMessage("Mute").replaceAll("%player%", user.getName()));
									User.sendAllMessage(
											msg.getMessage("MuteNotify").replaceAll("%player%", user.getName())
													.replaceAll("%admin%", sender.getName())
													.replaceAll("%motivation%", motiv.replaceAll("&", "§")));
									return true;
								} else {
									sender.sendMessage(msg.getMessage("Args"));
									return true;
								}
							} else {
								sender.sendMessage(msg.getMessage("AlreadyMuted"));
								return true;
							}
						} else {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;
						}
					} else {
						sender.sendMessage("§rUsage: /tempmute <player> <d/h/m/s> [motivation]");
					}
				} else {
					sender.sendMessage(msg.getMessage("No-Perm"));
					return true;
				}
			}

			if (cmd.getName().equalsIgnoreCase("unmute")) {
				Messages msg = new Messages();
				if (psender.hasPermission("main.unmute")) {
					if (args.length == 1) {
						User user = new User(args[0]);
						if (user.isOnline()) {
							if (user.isMuted()) {
								user.setMute(false);
								sender.sendMessage(msg.getMessage("unMute").replaceAll("%player%", user.getName()));
								user.sendMessage(msg.getMessage("unMuted"));
								return true;
							} else {
								sender.sendMessage(msg.getMessage("NoMute"));
								return true;
							}
						} else {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;
						}
					}else {
						sender.sendMessage("§rUsage: /unmute <player>");
					}
				} else {
					sender.sendMessage(msg.getMessage("No-Perm"));
					return true;
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("lookup")) {
				Messages msg = new Messages();
				if(psender.hasPermission("main.lookup")) {
					if(args.length==1) {
						User user = new User(args[0]);
						if(user.exists()) {
							sender.sendMessage(user.lookup());
							return true;
						}else {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;	
						}
					}else {
						sender.sendMessage("§rUsage: /lookup <player>");
					}
				}else {
					sender.sendMessage(msg.getMessage("No-Perm"));
					return true;	
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("history")) {
				Messages msg = new Messages();
				if(psender.hasPermission("main.history")) {
					if(args.length==1) {
						User user = new User(args[0]);
						if(user.exists()) {
							sender.sendMessage(user.history());
							return true;
						}else {
							sender.sendMessage(msg.getMessage("NoPlayer"));
							return true;	
						}
					}else {
						sender.sendMessage("§rUsage: /history <player>");
					}
				}else {
					sender.sendMessage(msg.getMessage("No-Perm"));
					return true;	
				}
			}

		}
		return false;
	}

}
