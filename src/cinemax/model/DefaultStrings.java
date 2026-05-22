package model;

public enum DefaultStrings {
	USER_TYPE( "default.user.type" ),
	USER_NAME("default.user.name");

	private String msgKey;
	private Object[] params;
	DefaultStrings( String msgKey, Object... params )
        {
                this.msgKey = msgKey;

                // creating a variable-size list of params instead of choosing a precise set of
                // finite amount params since this way it's easier to expand the code and allow
                // the introduction of new params wherever they're needed
                this.params = params;
        }
}
