package ua.igorg.userfusion.config.datasources.model;

import lombok.Data;

/**
 * @Author igorg
 * @create 01.06.2024
 */
@Data
public class DataSource {
	private DatabaseStrategy strategy; //: postgres
	private String url; //: jdbc://.....
	private String table; //: users
	private String user; //: testuser
	private String password; //: testpass
	private Mapping mapping;

//	public DataSource() {
//	}
//
//	public DataSource(
//		final DatabaseStrategy strategy,
//		final String url,
//		final String table,
//		final String user,
//		final String password
//	) {
//		this.strategy = strategy;
//		this.url = url;
//		this.table = table;
//		this.user = user;
//		this.password = password;
//	}
//
//	public DatabaseStrategy getStrategy() {
//		return strategy;
//	}
//
//	public void setStrategy(final DatabaseStrategy strategy) {
//		this.strategy = strategy;
//	}
//
//	public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(final String url) {
//		this.url = url;
//	}
//
//	public String getTable() {
//		return table;
//	}
//
//	public void setTable(final String table) {
//		this.table = table;
//	}
//
//	public String getUser() {
//		return user;
//	}
//
//	public void setUser(final String user) {
//		this.user = user;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(final String password) {
//		this.password = password;
//	}
//
//	@Override
//	public String toString() {
//		return "Name{" +
//			"strategy=" + strategy +
//			", url='" + url + '\'' +
//			", table='" + table + '\'' +
//			", user='" + user + '\'' +
//			", password='" + password + '\'' +
//			'}';
//	}
}
