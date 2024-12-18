package ua.igorg.userfusion.config.datasources.model;

/**
 * Created by igorg
 * on 01.06.2024
 */
public enum DatabaseStrategy {
	H2("org.h2.Driver"),

	POSTGRES("org.postgresql.Driver"),

	MYSQL("com.mysql.cj.jdbc.Driver"),

	SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver"),

	ORACLE("oracle.jdbc.driver.OracleDriver");

	private final String driverClass;

	DatabaseStrategy(final String driverClass) {
		this.driverClass = driverClass;
	}

	public String getDriver() {
		return driverClass;
	}
}
