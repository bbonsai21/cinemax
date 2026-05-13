package model;

import java.util.Objects;

public enum ValidationError {
        FILM_TITLE_EMPTY( "error.film.title.empty" ),
        FILM_DIRECTOR_NAME_EMPTY( "error.film.directorName.empty" ),
        FILM_YEAR_INVALID( "error.film.year.invalid", 1850, 2100 ),
        FILM_LENGTH_NEGATIVE( "error.film.length.negative" ),
        FILM_MIN_AGE_NEGATIVE( "error.film.minAge.negative" ),
        FILM_PRICE_NEGATIVE( "error.film.price.negative" ),
        
        SEATMAP_INVALID_ROW( "error.seatMap.invalidRow", 0, SeatMap.getRows() ),
        SEATMAP_INVALID_COL( "error.seatMap.invalidCol", 0, SeatMap.getCols() ),
        SEATMAP_INVALID_SEAT( "error.seatMap.invalidSeat" ),
        SEATMAP_UNAVAILABLE_SEAT( "error.seatMap.unavailableSeat" );

        private final String msgKey;
        private final Object[] params;

        ValidationError( String msgKey, Object... params )
        {
                this.msgKey = msgKey;

                // creating a variable-size list of params instead of choosing a precise set of
                // finite amount params since this way it's easier to expand the code and allow
                // the introduction of new params wherever they're needed
                this.params = params;
        }

        public static String getMessageKey( ValidationError error ) {
                Objects.nonNull( error );

                return error.msgKey;
        }

        public Object[] getParams()
        { return params; }
}