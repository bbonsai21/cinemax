package repository;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum FilePaths {
	PROJECTIONS("proiezioni.csv"),
	USERS("users.csv"),
	USER_BOOKINGS("user_bookings.csv");

	private final String filename;

	FilePaths(String filename) {
		this.filename = filename;
	}

	public String getPath() {
		try {
			Path jarDir = Paths.get(
					FilePaths.class
							.getProtectionDomain()
							.getCodeSource()
							.getLocation()
							.toURI())
					.getParent();

			return jarDir.resolve("../../data/" + filename)
					.normalize()
					.toString();
		} catch (Exception e) {
			return "data/" + filename;
		}
	}
}