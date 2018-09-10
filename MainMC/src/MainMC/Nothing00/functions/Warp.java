package MainMC.Nothing00.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import MainMC.Nothing00.MainPermissions;
import MainMC.folders.Conf;
import MainMC.folders.WarpData;

public class Warp extends WarpData {

	private String warp;

	public Warp(String name) {
		this.warp = name.toLowerCase();
	}

	public String getName() {
		return this.warp;
	}

	public static List<String> getWarps() {
		List<String> warps = new ArrayList<String>();
		WarpData data = new WarpData();
		Set<String> warpdata = data.getConfiguration("Warps");
		warps.addAll(warpdata);
		return warps;
	}

	public void setWarp(Location loc) {
		super.setWarp(this.warp, loc);
	}

	public Location getWarp() {
		return super.getWarp(this.warp);
	}

	public void delWarp() {
		super.get().set("Warps." + this.warp, null);
		super.save();
	}

	public boolean exists() {
		return getWarps().contains(this.warp);
	}

	public boolean hasWarpPermissions(Player p) {
		Conf config = new Conf();
		MainPermissions sender = new MainPermissions(p);
		if (config.perWarpPermission()) {
			return sender.hasPermission("main.warp." + this.warp);
		}
		return true;
	}

}
