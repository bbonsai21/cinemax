package repository;

public enum FilePaths {
	PROJECTIONS( "data/proiezioni.csv" ),
	USERS( "data/users.csv" ),
	USER_BOOKINGS( "data/user_bookings.csv" );

	private String path;
	
	FilePaths( String path )
	{ this.path = path; }

	public String getPath()
	{ return this.path; }
}
