package MainMC.folders;

import java.io.File;

import MainMC.Nothing00.MainPlugin;

public class Sql extends DataBase{
	
	public Sql(String db) {
		super(MainPlugin.getInstance().getDataFolder()+"/data/"+db);
	}
	
	public static File getDatabase() {
		Conf config = new Conf();
		return new File(MainPlugin.getInstance().getDataFolder()+"/data/"+config.getDBName());
	}

}
