package MainMC.Nothing00;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import MainMC.commands.BookCommand;
import MainMC.commands.ChatCommand;
import MainMC.commands.EconomyCommand;
import MainMC.commands.LockAccountCommand;
import MainMC.commands.SpawnCommand;
import MainMC.commands.TeleportCommand;
import MainMC.commands.admin.AddressCommand;
import MainMC.commands.admin.AdminHomeCommand;
import MainMC.commands.admin.BanCommand;
import MainMC.commands.admin.Gamemode;
import MainMC.commands.admin.ItemCommand;
import MainMC.commands.admin.JailCommand;
import MainMC.commands.admin.PunishCommand;
import MainMC.commands.admin.SeeInventory;
import MainMC.commands.admin.SpyMailCommand;
import MainMC.commands.admin.Utility;
import MainMC.commands.admin.WeatherTimeCommand;
import MainMC.commands.user.BasicCommand;
import MainMC.commands.user.HomeCommand;
import MainMC.commands.user.KitCommand;
import MainMC.commands.user.MailCommand;
import MainMC.commands.user.WarpCommand;
import MainMC.commands.vip.HatCommand;
import MainMC.commands.vip.Nickname;
import MainMC.commands.vip.SpeedCommand;
import MainMC.commands.vip.VipUtility;

public class ExecutorSystem {

	CommandMap commandMap;

	public ExecutorSystem() {
		this.commandMap = null;
		try {
			final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			f.setAccessible(true);
			this.commandMap = (CommandMap) f.get(Bukkit.getServer());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private PluginCommand[] getCommands() throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		List<PluginCommand> list = new ArrayList<PluginCommand>();
		List<String> cmdlist = new ArrayList<String>();
		for (String cmd : BookCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : ChatCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : EconomyCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : LockAccountCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : SpawnCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : TeleportCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : AddressCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : AdminHomeCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : BanCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : Gamemode.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : ItemCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : JailCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : PunishCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : SeeInventory.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : SpyMailCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : Utility.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : WeatherTimeCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : BasicCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : HomeCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : KitCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : MailCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : WarpCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : HatCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : Nickname.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : SpeedCommand.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : VipUtility.getCommands()) {
			cmdlist.add(cmd);
		}
		for (String cmd : cmdlist) {

			if (isPluginCommand(cmd))
				continue;

			if (isBukkitCommand(cmd))
				removeDefault(cmd);

			PluginCommand pcommand;
			Constructor<PluginCommand> constructor;
			constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			constructor.setAccessible(true);
			pcommand = constructor.newInstance(cmd, MainPlugin.getInstance());
			list.add(pcommand);
			constructor.setAccessible(false);
		}
		for (PluginCommand cmd : list) {

			if (isCommandOf(BookCommand.getCommands(), cmd)) {
				cmd.setExecutor(new BookCommand());
				continue;
			}
			if (isCommandOf(ChatCommand.getCommands(), cmd)) {
				cmd.setExecutor(new ChatCommand());
				continue;
			}
			if (isCommandOf(EconomyCommand.getCommands(), cmd)) {
				cmd.setExecutor(new EconomyCommand());
				continue;
			}
			if (isCommandOf(LockAccountCommand.getCommands(), cmd)) {
				cmd.setExecutor(new LockAccountCommand());
				continue;
			}
			if (isCommandOf(SpawnCommand.getCommands(), cmd)) {
				cmd.setExecutor(new SpawnCommand());
				continue;
			}
			if (isCommandOf(TeleportCommand.getCommands(), cmd)) {
				cmd.setExecutor(new TeleportCommand());
				continue;
			}
			if (isCommandOf(AddressCommand.getCommands(), cmd)) {
				cmd.setExecutor(new AddressCommand());
				continue;
			}
			if (isCommandOf(AdminHomeCommand.getCommands(), cmd)) {
				cmd.setExecutor(new AdminHomeCommand());
				continue;
			}
			if (isCommandOf(BanCommand.getCommands(), cmd)) {
				cmd.setExecutor(new BanCommand());
				continue;
			}
			if (isCommandOf(Gamemode.getCommands(), cmd)) {
				cmd.setExecutor(new Gamemode());
				continue;
			}
			if (isCommandOf(ItemCommand.getCommands(), cmd)) {
				cmd.setExecutor(new ItemCommand());
				continue;
			}
			if (isCommandOf(JailCommand.getCommands(), cmd)) {
				cmd.setExecutor(new JailCommand());
				continue;
			}
			if (isCommandOf(PunishCommand.getCommands(), cmd)) {
				cmd.setExecutor(new PunishCommand());
			}
			if (isCommandOf(SeeInventory.getCommands(), cmd)) {
				cmd.setExecutor(new SeeInventory());
				continue;
			}
			if (isCommandOf(SpyMailCommand.getCommands(), cmd)) {
				cmd.setExecutor(new SpyMailCommand());
				continue;
			}
			if (isCommandOf(Utility.getCommands(), cmd)) {
				cmd.setExecutor(new Utility());
				continue;
			}
			if (isCommandOf(WeatherTimeCommand.getCommands(), cmd)) {
				cmd.setExecutor(new WeatherTimeCommand());
				continue;
			}
			if (isCommandOf(BasicCommand.getCommands(), cmd)) {
				cmd.setExecutor(new BasicCommand());
				continue;
			}
			if (isCommandOf(HomeCommand.getCommands(), cmd)) {
				cmd.setExecutor(new HomeCommand());
				continue;
			}
			if (isCommandOf(KitCommand.getCommands(), cmd)) {
				cmd.setExecutor(new KitCommand());
				continue;
			}
			if (isCommandOf(MailCommand.getCommands(), cmd)) {
				cmd.setExecutor(new MailCommand());
				continue;
			}
			if (isCommandOf(WarpCommand.getCommands(), cmd)) {
				cmd.setExecutor(new WarpCommand());
				continue;
			}
			if (isCommandOf(HatCommand.getCommands(), cmd)) {
				cmd.setExecutor(new HatCommand());
				continue;
			}
			if (isCommandOf(Nickname.getCommands(), cmd)) {
				cmd.setExecutor(new Nickname());
				continue;
			}
			if (isCommandOf(SpeedCommand.getCommands(), cmd)) {
				cmd.setExecutor(new SpeedCommand());
				continue;
			}
			if (isCommandOf(VipUtility.getCommands(), cmd)) {
				cmd.setExecutor(new VipUtility());
				continue;
			}
		}
		return list.toArray(new PluginCommand[0]);

	}

	private boolean isCommandOf(String[] array, PluginCommand cmd) {
		int i = 0;
		boolean t = false;
		while (i < array.length && !t) {
			if (array[i].equals(cmd.getName())) {
				t = true;
			} else {
				i++;
			}
		}
		return t;
	}

	public void loadCommands() {
		try {
			for (PluginCommand cmd : getCommands()) {

				if (getAliases(cmd.getName()) != null) {
					cmd.setAliases(getAliases(cmd.getName()));
				}

				this.commandMap.register(cmd.getName(), cmd);
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	private List<String> getAliases(String cmd) {
		List<String> list = new ArrayList<String>();
		switch (cmd) {
		case "gmc":
			list.add("c");
			list.add("creative");
			break;
		case "gms":
			list.add("s");
			list.add("survival");
			break;
		case "gma":
			list.add("a");
			list.add("adventure");
			break;
		case "gamemode":
			list.add("gm");
			break;
		case "vanish":
			list.add("v");
			break;
		case "item":
			list.add("i");
			break;
		case "enchant":
			list.add("enchantment");
			break;
		case "xp":
			list.add("exp");
			break;
		case "feed":
			list.add("eat");
			break;
		case "god":
			list.add("godmode");
			list.add("tgm");
			break;
		case "hat":
			list.add("head");
			break;
		case "walkspeed":
			list.add("ws");
			list.add("wspeed");
			break;
		case "flyspeed":
			list.add("fs");
			list.add("fspeed");
			break;
		case "repair":
			list.add("fix");
			break;
		case "workbench":
			list.add("craft");
			list.add("wb");
			list.add("wbench");
			break;
		case "me":
			list.add("description");
			break;
		case "clear":
			list.add("clearinventory");
			list.add("clearinv");
			list.add("clean");
			break;
		case "invsee":
			list.add("inventorysee");
			list.add("invs");
			list.add("inventorys");
			list.add("isee");
			break;
		case "enderchest":
			list.add("endersee");
			list.add("enderchestsee");
			list.add("esee");
			list.add("enders");
			list.add("ec");
			break;
		case "broadcast":
			list.add("bc");
			list.add("broadc");
			list.add("bcast");
			break;
		case "getpos":
			list.add("pos");
			list.add("getp");
			break;
		case "thor":
			list.add("strike");
			list.add("linghting");
			break;
		case "helpstaff":
			list.add("hstaff");
			list.add("msgstaff");
			list.add("helps");
			list.add("msghelp");
			list.add("staffmessage");
			list.add("report");
			break;
		case "tell":
			list.add("m");
			list.add("msg");
			list.add("pm");
			list.add("whisper");
			list.add("w");
			list.add("playermessage");
			list.add("pmessage");
			list.add("pmsg");
			break;
		case "reply":
			list.add("r");
			break;
		case "tpaccept":
			list.add("tpyes");
			break;
		case "nick":
			list.add("nickname");
			break;
		case "realname":
			list.add("realnick");
			list.add("real");
			list.add("name");
			break;
		case "socialspy":
			list.add("chatspy");
			list.add("spy");
			break;
		case "mail":
			list.add("email");
			break;
		case "spymail":
			list.add("spyemail");
			list.add("smail");
			list.add("adminmail");
			list.add("amail");
			break;
		case "adminhome":
			list.add("ahome");
			list.add("adminh");
			break;
		case "tempmute":
			list.add("tmute");
			break;
		case "tempban":
			list.add("tban");
			break;
		case "createjail":
			list.add("cjail");
			list.add("cj");
			list.add("createj");
			break;
		case "deljail":
			list.add("djail");
			list.add("dj");
			list.add("deletej");
			list.add("deletejail");
			break;
		case "lookup":
			list.add("look");
			list.add("checkban");
			list.add("checkmute");
			list.add("check");
			break;
		case "banip":
			list.add("ban-ip");
			list.add("ipban");
			break;
		case "unbanip":
			list.add("unban-ip");
			list.add("unipban");
			list.add("ipunban");
			break;
		case "seen":
			list.add("ip");
			list.add("iplookup");
			break;
		case "setlockaccount":
			list.add("setkeyword");
			break;
		case "unlockaccount":
			list.add("keyword");
			break;
		case "history":
			list.add("bandata");
			list.add("punish");
			list.add("punishdata");
			break;
		case "balance":
			list.add("money");
			list.add("bal");
			break;
		case "eco":
			list.add("economy");
			break;
		case "firstlogin":
			list.add("first");
			list.add("flogin");
			break;
		case "lastlocation":
			list.add("last");
			list.add("lastloc");
			break;
		case "kitcreator":
			list.add("kitc");
			list.add("kitcrea");
			break;
		case "ptime":
			list.add("playertime");
			break;
		default:
			return null;
		}
		return list;
	}

	private boolean isPluginCommand(String cmd) {
		if (this.commandMap.getCommand(cmd) == null)
			return false;
		boolean flag = false;
		for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
			if (this.commandMap.getCommand(p.getName() + ":" + cmd) != null) {
				if (p.getName().equals("WorldGuard")) {
					removeDefault(cmd);
					continue;
				} else {
					flag = true;
				}
			}
		}
		return flag;
	}

	private boolean isBukkitCommand(String cmd) {
		boolean flag = true;
		for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
			if (this.commandMap.getCommand(p.getName() + ":" + cmd) != null) {
				flag = false;
			}
		}
		return flag;
	}

	private static void removeDefault(String cmd) {
		Object result;
		try {
			result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
			SimpleCommandMap commandMap = (SimpleCommandMap) result;
			Object map;
			map = getPrivateField(commandMap, "knownCommands");
			@SuppressWarnings("unchecked")
			HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
			knownCommands.remove(cmd);
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}

	}

	private static Object getPrivateField(Object object, String field)
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		Field objectField = clazz.getDeclaredField(field);
		objectField.setAccessible(true);
		Object result = objectField.get(object);
		objectField.setAccessible(false);
		return result;
	}

}
