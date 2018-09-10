package MainMC.Nothing00.functions;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import MainMC.Nothing00.Utils.Punishment;
import MainMC.folders.Conf;
import MainMC.folders.Sql;

public class Ip {

	private String address;

	public Ip(String address) {
		this.address = address;
	}

	public String toString() {
		return this.address;
	}

	public void banIp(String motiv) {
		Conf config = new Conf();
		Sql sql = new Sql(config.getDBName());
		sql.banIp(this.address);
		banAllPlayersWithThisAddress(motiv);
	}

	public List<String> getUsersWithThisAddress() {
		List<String> users = new ArrayList<String>();
		for (String user : User.getUserList()) {
			User u = new User(user);
			if (u.getCurrentHost().split(":")[0].equals(this.address)) {
				users.add(u.getName());
			}
		}
		return users;
	}

	public void unBanIp() {
		Conf config = new Conf();
		Sql sql = new Sql(config.getDBName());
		sql.unBanIp(this.address);

	}

	public boolean isBanned() {
		Conf config = new Conf();
		Sql sql = new Sql(config.getDBName());
		return sql.isBannedIp(this.address);
	}

	public boolean isAddress() {
		char[] car = this.address.toCharArray();
		int cont = 0;
		for (int i = 0; i < car.length; i++) {
			if (car[i] == '.')
				cont++;
		}
		if (cont != 3)
			return false;
		String[] split = this.address.split(".");
		return NumberUtils.isNumber(split[0]) && NumberUtils.isNumber(split[1]) && NumberUtils.isNumber(split[2])
				&& NumberUtils.isNumber(split[3]);

	}

	public boolean isValidAddress() {
		char[] car = this.address.toCharArray();
		int cont = 0;
		for (int i = 0; i < car.length; i++) {
			if (car[i] == '.')
				cont++;
		}
		if (cont != 3)
			return false;
		String[] split = this.address.split(".");
		return NumberUtils.isNumber(split[0]) && NumberUtils.isNumber(split[1]) && NumberUtils.isNumber(split[2])
				&& NumberUtils.isNumber(split[3]) && Integer.parseInt(split[0]) <= 256
				&& Integer.parseInt(split[1]) <= 256 && Integer.parseInt(split[2]) <= 256
				&& Integer.parseInt(split[3]) <= 256;

	}

	public void banAllPlayersWithThisAddress(String motiv) {
		for (String user : User.getUserList()) {
			User u = new User(user);
			if (u.getCurrentHost().split(":")[0].equals(this.address)) {
				if (u.isBanned())
					continue;
				u.setBanned(true);
				u.setBanAuthor("hidden");
				Punishment punish = new Punishment(u.getName(), "hidden", motiv);
				punish.registerPunish("IP", "BAN");
				u.setLastBanMotivation(motiv.replaceAll("§", "&").replaceAll(" ", "_") + " [IP]");
				if (u.isOnline()) {
					u.getPlayer().kickPlayer(motiv);
				}
			}
			List<String> ips = u.getOldAdresses();
			for (String ip : ips) {
				if (ip.split(":")[0].equals(this.address)) {
					if (u.isBanned())
						continue;
					u.setBanned(true);
					u.setBanAuthor("hidden");
					Punishment punish = new Punishment(u.getName(), "hidden", motiv);
					punish.registerPunish("IP", "BAN");
					u.setLastBanMotivation(motiv.replaceAll("§", "&").replaceAll(" ", "_") + " [IP]");
					if (u.isOnline()) {
						u.getPlayer().kickPlayer(motiv);
					}
				}
				Ip add = new Ip(ip);
				add.banIp(motiv);
			}
		}
	}

}
