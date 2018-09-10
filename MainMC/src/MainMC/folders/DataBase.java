package MainMC.folders;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

	private final String url;
	ResultSet resultSet = null;
	Statement statement = null;
	final String driver;
	String user = "";
	String database = "";
	String password = "";
	String port = "";
	String host = "";
	Connection c = null;

	public DataBase(String filePath) {
		url = "jdbc:sqlite:" + new File(filePath).getAbsolutePath();
		driver = ("org.sqlite.JDBC");
	}

	public Connection open() {
		try {
			Class.forName(driver);
			this.c = DriverManager.getConnection(url);
			createUserTable();
			createNickTable();
			createTempMuteTable();
			createTempBanTable();
			createJailTable();
			createIpTable();
			createUnLockTable();
			createKitTable();
			createTimeTable();
			createEcoTable();
			return c;
		} catch (SQLException e) {
			System.out.println("Could not connect to SQLite server! because: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("SQLite file (JDBC Driver) not found!");
		}
		return this.c;
	}

	public boolean checkConnection() {
		if (this.c != null) {
			return true;
		} else {
			return false;
		}
	}

	public Connection getConnection() {
		return this.c;
	}

	public void closeConnection(Connection c) {
		c = null;
	}

	private void createUserTable() {
		String sql = "CREATE TABLE IF NOT EXISTS user_table (\n" + "	id integer PRIMARY KEY,\n"
				+ "	username text,\n" + "	uuid text,\n" + "	socialspy boolan,\n" + "	muted boolean,\n"
				+ "	banned boolean\n" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void createNickTable() {
		String sql = "CREATE TABLE IF NOT EXISTS nick_table (\n" + "	id integer PRIMARY KEY,\n"
				+ "	username text,\n" + "	nickname text\n" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void createEcoTable() {
		String sql = "CREATE TABLE IF NOT EXISTS eco_table (\n" + "	id integer PRIMARY KEY,\n" + "	username text,\n"
				+ "	money double\n" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void createTimeTable() {
		String sql = "CREATE TABLE IF NOT EXISTS time_table (\n" + "	id integer PRIMARY KEY,\n"
				+ "	username text,\n" + "	time text\n" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void createUnLockTable() {
		String sql = "CREATE TABLE IF NOT EXISTS lock_table (\n" + "	id integer PRIMARY KEY,\n" + "	uuid text,\n"
				+ "	keyword text,\n" + "	locked boolean\n" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void createKitTable() {
		String sql = "CREATE TABLE IF NOT EXISTS kit_table (\n" + "	id integer PRIMARY KEY,\n" + "	uuid text,\n"
				+ "	kit text,\n" + "	expire text\n" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void createJailTable() {
		String sql = "CREATE TABLE IF NOT EXISTS jail_table (\n" + "	id integer PRIMARY KEY,\n"
				+ "	username text,\n" + "	jail text\n" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void createIpTable() {
		String sql = "CREATE TABLE IF NOT EXISTS ip_table (\n" + "	id integer PRIMARY KEY,\n" + "	address text,\n"
				+ "	boolean banned\n" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void createTempMuteTable() {
		String sql = "CREATE TABLE IF NOT EXISTS mute_table (\n" + "	id integer PRIMARY KEY,\n"
				+ "	username text,\n" + "	expire text\n" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void createTempBanTable() {
		String sql = "CREATE TABLE IF NOT EXISTS ban_table (\n" + "	id integer PRIMARY KEY,\n" + "	username text,\n"
				+ "	expire text\n" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void createuser(String name, String UUID, boolean socialspy, boolean banned, boolean mute) {
		name = name.toLowerCase();

		String sql = "INSERT INTO user_table(username,uuid,socialspy,banned,muted) VALUES(?,?,?,?,?)";

		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, name);
			pstmt.setString(2, UUID);
			pstmt.setBoolean(3, socialspy);
			pstmt.setBoolean(4, banned);
			pstmt.setBoolean(5, mute);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void createBalance(String name, double money) {
		name = name.toLowerCase();

		String sql = "INSERT INTO eco_table(username,money) VALUES(?,?)";

		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, name);
			pstmt.setDouble(2, money);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void setLockAccount(String UUID, String keyword) {

		String sql = "INSERT INTO lock_table(uuid,keyword,locked) VALUES(?,?,?)";

		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, UUID);
			pstmt.setString(2, keyword);
			pstmt.setBoolean(3, false);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void setKitDelay(String UUID, String kit, String expire) {

		String sql = "INSERT INTO kit_table(uuid,kit,expire) VALUES(?,?,?)";

		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, UUID);
			pstmt.setString(2, kit);
			pstmt.setString(3, expire);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean hasKyword(String UUID) {

		String sql = "SELECT uuid FROM lock_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;

			while (rs.next() && !flag) {
				if (rs.getString("uuid").equalsIgnoreCase(UUID)) {
					flag = true;
				}
			}

			return flag;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public boolean hasBalance(String user) {
		user = user.toLowerCase();

		String sql = "SELECT username FROM eco_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;

			while (rs.next() && !flag) {
				if (rs.getString("username").equalsIgnoreCase(user)) {
					flag = true;
				}
			}

			return flag;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public boolean isLocked(String UUID) {

		String sql = "SELECT uuid,locked FROM lock_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false, locked = false;

			while (rs.next() && !flag) {
				if (rs.getString("uuid").equalsIgnoreCase(UUID)) {
					locked = rs.getBoolean("locked");
					flag = true;
				}
			}

			return locked;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public void updateKeyword(String UUID, String keyword) {

		String sql = "UPDATE lock_table SET keyword = ? WHERE uuid = ?";

		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, keyword);
			pstmt.setString(2, UUID);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void setBalance(String user, double money) {
		user=user.toLowerCase();

		String sql = "UPDATE eco_table SET money = ? WHERE username = ?";

		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setDouble(1, money);
			pstmt.setString(2, user);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void setLocked(String UUID, boolean locked) {

		String sql = "UPDATE lock_table SET locked = ? WHERE uuid = ?";

		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setBoolean(1, locked);
			pstmt.setString(2, UUID);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteKit(String UUID, String kit) {

		String sql = "DELETE FROM kit_table WHERE uuid = ? AND kit = ?";
		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, UUID);
			pstmt.setString(2, kit);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteKeyword(String UUID) {

		String sql = "DELETE FROM lock_table WHERE uuid = ?";
		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, UUID);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public String getKyword(String UUID) {

		String sql = "SELECT keyword,uuid FROM lock_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;
			String key = null;
			while (rs.next() && !flag) {
				if (rs.getString("uuid").equalsIgnoreCase(UUID)) {
					key = rs.getString("keyword");
					flag = true;
				}
			}

			return key;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public void banIp(String address) {

		String sql = "INSERT INTO ip_table(address,banned) VALUES(?,?)";

		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, address);
			pstmt.setBoolean(3, true);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void setJail(String name, String jail) {
		name = name.toLowerCase();

		String sql = "INSERT INTO jail_table(username,jail) VALUES(?,?)";

		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, name);
			pstmt.setString(2, jail);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertNick(String name, String nick) {
		name = name.toLowerCase();

		String sql = "INSERT INTO nick_table(username,nickname) VALUES(?,?)";

		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, name);
			pstmt.setString(2, nick);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void setExpire(String user, String expire) {
		user = user.toLowerCase();

		String sql = "SELECT username FROM mute_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			boolean flag = false;
			while (rs.next() && !flag) {
				if (rs.getString("username").equalsIgnoreCase(user)) {
					flag = true;
				}
			}

			if (flag) {
				sql = "UPDATE mute_table SET expire = ? WHERE username = ?";

				try (Connection conne = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

					pstmt.setString(1, expire);
					pstmt.setString(2, user);

					pstmt.executeUpdate();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			} else {
				sql = "INSERT INTO mute_table(username,expire) VALUES(?,?)";

				try (Connection conne = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

					pstmt.setString(1, user);
					pstmt.setString(2, expire);
					pstmt.executeUpdate();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void setTime(String user, String time) {
		user = user.toLowerCase();

		String sql = "SELECT username FROM time_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			boolean flag = false;
			while (rs.next() && !flag) {
				if (rs.getString("username").equalsIgnoreCase(user)) {
					flag = true;
				}
			}

			if (flag) {
				sql = "UPDATE time_table SET time = ? WHERE username = ?";

				try (Connection conne = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

					pstmt.setString(1, time);
					pstmt.setString(2, user);

					pstmt.executeUpdate();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			} else {
				sql = "INSERT INTO time_table(username,time) VALUES(?,?)";

				try (Connection conne = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

					pstmt.setString(1, user);
					pstmt.setString(2, time);
					pstmt.executeUpdate();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void setBanExpire(String user, String expire) {
		user = user.toLowerCase();

		String sql = "SELECT username FROM ban_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			boolean flag = false;
			while (rs.next() && !flag) {
				if (rs.getString("username").equalsIgnoreCase(user)) {
					flag = true;
				}
			}

			if (flag) {
				sql = "UPDATE ban_table SET expire = ? WHERE username = ?";

				try (Connection conne = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

					pstmt.setString(1, expire);
					pstmt.setString(2, user);

					pstmt.executeUpdate();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			} else {
				sql = "INSERT INTO ban_table(username,expire) VALUES(?,?)";

				try (Connection conne = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

					pstmt.setString(1, user);
					pstmt.setString(2, expire);
					pstmt.executeUpdate();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean isTempMuted(String user) {
		user = user.toLowerCase();

		String sql = "SELECT username FROM mute_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;

			while (rs.next() && !flag) {
				if (rs.getString("username").equalsIgnoreCase(user)) {
					flag = true;
				}
			}

			return flag;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public boolean hasDelay(String UUID, String kit) {

		String sql = "SELECT uuid,kit FROM kit_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;

			while (rs.next() && !flag) {
				if (rs.getString("uuid").equalsIgnoreCase(UUID) && rs.getString("kit").equalsIgnoreCase(kit)) {
					flag = true;
				}
			}

			return flag;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public String getTime(String user) {
		user = user.toLowerCase();

		String sql = "SELECT username,time FROM time_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;
			String time = null;
			while (rs.next() && !flag) {
				if (rs.getString("username").equalsIgnoreCase(user)) {
					time = rs.getString("time");
					flag = true;
				}
			}

			return time;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public double getBalance(String user) {
		user = user.toLowerCase();

		String sql = "SELECT username,money FROM eco_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;
			double money = 0;
			while (rs.next() && !flag) {
				if (rs.getString("username").equalsIgnoreCase(user)) {
					money = rs.getDouble("money");
					flag = true;
				}
			}

			return money;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return -1;
	}

	public String getDelay(String UUID, String kit) {

		String sql = "SELECT uuid,kit,expire FROM kit_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;
			String delay = null;

			while (rs.next() && !flag) {
				if (rs.getString("uuid").equalsIgnoreCase(UUID) && rs.getString("kit").equalsIgnoreCase(kit)) {
					delay = rs.getString("expire");
					flag = true;
				}
			}

			return delay;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public boolean isBannedIp(String address) {

		String sql = "SELECT address FROM ip_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;

			while (rs.next() && !flag) {
				if (rs.getString("address").equalsIgnoreCase(address)) {
					flag = true;
				}
			}

			return flag;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public boolean isTempBanned(String user) {
		user = user.toLowerCase();

		String sql = "SELECT username FROM ban_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;

			while (rs.next() && !flag) {
				if (rs.getString("username").equalsIgnoreCase(user)) {
					flag = true;
				}
			}

			return flag;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public String getMuteExpire(String user) {

		user = user.toLowerCase();

		String sql = "SELECT expire,username FROM mute_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();

				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;
			String expire = "";

			while (rs.next() && !flag) {
				if (user.equalsIgnoreCase(rs.getString("username"))) {
					expire = rs.getString("expire");
					flag = true;
				}
			}

			return expire;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public String getBanExpire(String user) {

		user = user.toLowerCase();

		String sql = "SELECT expire,username FROM ban_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();

				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;
			String expire = "";

			while (rs.next() && !flag) {
				if (user.equalsIgnoreCase(rs.getString("username"))) {
					expire = rs.getString("expire");
					flag = true;
				}
			}

			return expire;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public boolean hasNick(String user) {
		user = user.toLowerCase();

		String sql = "SELECT username FROM nick_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;

			while (rs.next() && !flag) {
				if (rs.getString("username").equalsIgnoreCase(user)) {
					flag = true;
				}
			}

			return flag;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public boolean isJailed(String user) {
		user = user.toLowerCase();

		String sql = "SELECT username FROM jail_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;

			while (rs.next() && !flag) {
				if (rs.getString("username").equalsIgnoreCase(user)) {
					flag = true;
				}
			}

			return flag;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public String getJail(String user) {
		user = user.toLowerCase();

		String sql = "SELECT username,jail FROM jail_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;
			String jail = null;

			while (rs.next() && !flag) {
				if (rs.getString("username").equalsIgnoreCase(user)) {
					jail = rs.getString("jail");
					flag = true;
				}
			}

			return jail;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public boolean exists(String user) {
		user = user.toLowerCase();

		String sql = "SELECT username FROM user_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;

			while (rs.next() && !flag) {
				if (rs.getString("username").equalsIgnoreCase(user)) {
					flag = true;
				}
			}

			return flag;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public String getNick(String user) {

		user = user.toLowerCase();

		String sql = "SELECT nickname,username FROM nick_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();

				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;
			String nick = "";

			while (rs.next() && !flag) {
				if (user.equalsIgnoreCase(rs.getString("username"))) {
					nick = rs.getString("nickname");
					flag = true;
				}
			}

			return nick;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public String getReal(String user) {

		user = user.toLowerCase();

		String sql = "SELECT nickname,username FROM nick_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();

				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;
			String nick = "";

			while (rs.next() && !flag) {
				if (user.equalsIgnoreCase(rs.getString("nickname"))) {
					nick = rs.getString("username");
					flag = true;
				}
			}

			return nick;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public boolean isUsed(String nick) {

		String sql = "SELECT nickname,username FROM nick_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();

				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false;

			while (rs.next() && !flag) {
				if (nick.equalsIgnoreCase(rs.getString("nickname"))) {
					flag = true;
				}
			}

			return flag;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public boolean getMuted(String user) {

		user = user.toLowerCase();

		String sql = "SELECT muted,username FROM user_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();

				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false, mute = false;

			while (rs.next() && !flag) {
				if (user.equalsIgnoreCase(rs.getString("username"))) {
					mute = rs.getBoolean("muted");
					flag = true;
				}
			}

			return mute;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public boolean getBanned(String user) {

		user = user.toLowerCase();

		String sql = "SELECT banned,username FROM user_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();

				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false, banned = false;

			while (rs.next() && !flag) {
				if (user.equalsIgnoreCase(rs.getString("username"))) {
					banned = rs.getBoolean("banned");
					flag = true;
				}
			}

			return banned;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public boolean getSocialspy(String user) {

		user = user.toLowerCase();

		String sql = "SELECT socialspy,username FROM user_table";

		try (Connection conn = this.open();

				Statement stmt = conn.createStatement();

				ResultSet rs = stmt.executeQuery(sql)) {

			boolean flag = false, social = false;

			while (rs.next() && !flag) {
				if (user.equalsIgnoreCase(rs.getString("username"))) {
					social = rs.getBoolean("socialspy");
					flag = true;
				}
			}

			return social;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public void setNick(String user, String nick) {

		user = user.toLowerCase();

		String sql = "UPDATE nick_table SET nickname = ? WHERE username = ?";

		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, nick);
			pstmt.setString(2, user);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void removeNick(String name) {
		name = name.toLowerCase();
		String sql = "DELETE FROM nick_table WHERE username = ?";
		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, name);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void removeJail(String name) {
		name = name.toLowerCase();
		String sql = "DELETE FROM jail_table WHERE username = ?";
		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, name);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void unBanIp(String address) {
		String sql = "DELETE FROM ip_table WHERE address = ?";
		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, address);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void removeTempMute(String name) {
		name = name.toLowerCase();

		if (!this.isTempMuted(name))
			return;

		String sql = "DELETE FROM mute_table WHERE username = ?";
		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, name);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void removeTempBan(String name) {
		name = name.toLowerCase();

		if (!this.isTempBanned(name))
			return;

		String sql = "DELETE FROM ban_table WHERE username = ?";
		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, name);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void setMuted(String user, boolean mute) {

		user = user.toLowerCase();

		String sql = "UPDATE user_table SET muted = ? WHERE username = ?";

		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setBoolean(1, mute);
			pstmt.setString(2, user);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void setBanned(String user, boolean ban) {

		user = user.toLowerCase();

		String sql = "UPDATE user_table SET banned = ? WHERE username = ?";

		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setBoolean(1, ban);
			pstmt.setString(2, user);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void setSocialspy(String user, boolean social) {

		user = user.toLowerCase();

		String sql = "UPDATE user_table SET socialspy = ? WHERE username = ?";

		try (Connection conn = this.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setBoolean(1, social);
			pstmt.setString(2, user);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// TEST METHOD
	public void selectAll() {
		String sql = "SELECT username, money FROM eco_table";

		try (Connection conn = this.open();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {
				System.out.println(rs.getString("username") + "\t" + rs.getString("money"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
