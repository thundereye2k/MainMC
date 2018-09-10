package MainMC.Nothing00.Utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import MainMC.Nothing00.MainPlugin;
import MainMC.folders.BanData;
import MainMC.folders.Conf;
import MainMC.folders.Config;
import MainMC.folders.KickData;
import MainMC.folders.MuteData;

public class Punishment {

	private String victim;
	private String author;
	private String motiv;

	public Punishment(String victim, String author, String motiv) {
		this.victim = victim;
		this.author = author;
		this.motiv = motiv;
	}

	public void registerPunish(String punishtype, String type) {
		this.motiv = this.motiv.replaceAll(" ", "_").replaceAll("§", "&");
		Conf config = new Conf();
		if (config.thereIsHistory()) {
			String punish = punishtype + " " + LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonthValue()
					+ "/" + LocalDate.now().getYear() + " " + LocalTime.now().getHour() + ":"
					+ LocalTime.now().getMinute() + " " + this.motiv + " " + this.author;
			if (config.punishDivided()) {
				if (type.equals("MUTE")) {

					MuteData mute = new MuteData();

					List<String> mutelist = new ArrayList<String>();

					if (mute.getStringList("Mutes." + this.victim) == null) {
						mutelist.add(punish);
						mute.get().set("Mutes." + this.victim, mutelist);
					} else {
						mutelist = mute.getStringList("Mutes." + this.victim);
						mutelist.add(punish);
						mute.get().set("Mutes." + this.victim, mutelist);
					}
					mute.save();

				} else if (type.equals("KICK")) {
					KickData kick = new KickData();

					List<String> kicklist = new ArrayList<String>();

					if (kick.getStringList("Kicks." + this.victim) == null) {
						kicklist.add(punish);
						kick.get().set("Kicks." + this.victim, kicklist);
					} else {
						kicklist = kick.getStringList("Kicks." + this.victim);
						kicklist.add(punish);
						kick.get().set("Kicks." + this.victim, kicklist);
					}
					kick.save();
				} else if (type.equals("BAN")) {
					BanData ban = new BanData();

					List<String> banlist = new ArrayList<String>();

					if (ban.getStringList("Bans." + this.victim) == null) {
						banlist.add(punish);
						ban.get().set("Bans." + this.victim, banlist);
					} else {
						banlist = ban.getStringList("Bans." + this.victim);
						banlist.add(punish);
						ban.get().set("Bans." + this.victim, banlist);
					}
					ban.save();
				}
			} else {

				Config history = new Config(
						new File(MainPlugin.getInstance().getDataFolder() + "/data/punishment-history.yml"));

				List<String> punishes = new ArrayList<String>();

				if (history.getStringList("History." + this.victim) == null) {
					punish = punish + " (" + type.toLowerCase() + ")";
					punishes.add(punish);
					history.get().set("History." + this.victim, punishes);
				} else {
					punishes = history.getStringList("History." + this.victim);
					punish = punish + " (" + type.toLowerCase() + ")";
					punishes.add(punish);
					history.get().set("History." + this.victim, punishes);
				}
				history.save();

			}

		}
	}

	public File getFile() {
		File file = new File(MainPlugin.getInstance().getDataFolder() + "/data/punishment-history.yml");
		return file;
	}

	public static void createFile() {
		File file = new File(MainPlugin.getInstance().getDataFolder() + "/data/punishment-history.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int getKickCount() {
		Config history = new Config(
				new File(MainPlugin.getInstance().getDataFolder() + "/data/punishment-history.yml"));
		if (history.get().get("History." + this.victim) != null
				&& !history.getStringList("History." + this.victim).isEmpty()) {
			int cont = 0;
			for (String punish : history.getStringList("History." + this.victim)) {
				if (punish.contains("(kick)"))
					cont++;
			}
			return cont;
		}
		return 0;
	}

	public int getMuteCount() {
		Config history = new Config(
				new File(MainPlugin.getInstance().getDataFolder() + "/data/punishment-history.yml"));
		if (history.get().get("History." + this.victim) != null
				&& !history.getStringList("History." + this.victim).isEmpty()) {
			int cont = 0;
			for (String punish : history.getStringList("History." + this.victim)) {
				if (punish.contains("(mute)"))
					cont++;
			}
			return cont;
		}
		return 0;
	}

	public int getBanCount() {
		Config history = new Config(
				new File(MainPlugin.getInstance().getDataFolder() + "/data/punishment-history.yml"));
		if (history.get().get("History." + this.victim) != null
				&& !history.getStringList("History." + this.victim).isEmpty()) {
			int cont = 0;
			for (String punish : history.getStringList("History." + this.victim)) {
				if (punish.contains("(ban)"))
					cont++;
			}
			return cont;
		}
		return 0;
	}

	public static void exporting() {

		Config history = new Config(
				new File(MainPlugin.getInstance().getDataFolder() + "/data/punishment-history.yml"));

		if (!history.getFile().exists())
			return;

		Set<String> list = history.getConfiguration("History");
		String[] user = list.toArray(new String[0]);

		KickData kick = new KickData();
		kick.onCreate();
		MuteData mute = new MuteData();
		mute.onCreate();
		BanData ban = new BanData();
		ban.onCreate();

		for (int i = 0; i < user.length; i++) {
			List<String> punish = history.getStringList("History." + user[i]);
			String[] array = punish.toArray(new String[0]);
			for (int j = 0; j < array.length; j++) {
				String[] split = array[j].split(" ");
				if (split[5].equals("(kick)")) {
					List<String> kicked = new ArrayList<String>();
					array[j] = array[j].replace(" (kick)", "");
					if (kick.get().getStringList("Kicks." + user[i]) != null) {
						kicked = kick.getStringList("Kicks." + user[i]);
						kicked.add(array[j]);
						kick.set("Kicks." + user[i], kicked);
					} else {
						kicked.add(array[j]);
						kick.set("Kicks." + user[i], kicked);
					}
					kick.save();
				}
				if (split[5].equals("(mute)")) {
					List<String> muted = new ArrayList<String>();
					array[j] = array[j].replace(" (mute)", "");
					if (mute.get().getStringList("Mutes." + user[i]) != null) {
						muted = mute.getStringList("Mutes." + user[i]);
						muted.add(array[j]);
						mute.set("Mutes." + user[i], muted);
					} else {
						muted.add(array[j]);
						mute.set("Mutes." + user[i], muted);
					}
					mute.save();
				}
				if (split[5].equals("(ban)")) {
					List<String> banned = new ArrayList<String>();
					array[j] = array[j].replace(" (ban)", "");
					if (ban.get().getStringList("Bans." + user[i]) != null) {
						banned = mute.getStringList("Bans." + user[i]);
						banned.add(array[j]);
						ban.set("Bans." + user[i], banned);
					} else {
						banned.add(array[j]);
						ban.set("Bans." + user[i], banned);
					}
					ban.save();
				}
			}
		}
		history.delete();
		System.out.println("exporting complete!");
	}

}
