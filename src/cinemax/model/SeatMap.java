package model;

import exception.ValidationException;

/**
 * Data enum class storing the seat map of the cinema.
 */
public final class SeatMap {
        private static SeatMap self;
        
        // rows and cols here are treated as natural counting numbers. They are NOT indices. It's NOT max_row nor max_col.
        private final static int rows = 20;
        private final static int cols = 10;
        
        private final static int totalSeats = 200; // must be between 0 and rows*cols
        
        private byte[] map;
        private byte[] unavailableSeatsMap;

        /**
         * Internal representation object of a seat in the map. 
         */
        public record Seat( int row, int col ) {}

        private SeatMap() {
                map = new byte[ ( totalSeats + 7 ) / 8 ];
        }

        /**
         * Returns an internal unique static representation object of the cinema map.
         * All seats are treated as single bits insie of a byte array.
         */
        public static SeatMap create() {
                if  ( self != null ) return self;

                SeatMap self = new SeatMap();
                return self;
        }

        /**
         * Sets specified indicised seat to a occupied state in the internal representation.
         * @param row the index of the row in the byte array
         * @param col the index of the column in the byte array
         * @throws ValidationException
         */
        public void occupySeat( int row, int col ) throws ValidationException {
                if ( row < 0 || row >= rows )
                        throw new ValidationException( ValidationError.SEATMAP_INVALID_ROW );

                if ( col < 0 || col >= cols )
                        throw new ValidationException( ValidationError.SEATMAP_INVALID_COL );

                int seatIndex = row * cols + col;
                int byteIndex = seatIndex / 8;
                int bitIndex = byteIndex % 8;

                byte mask = ( byte )( 1 << ( 7 - bitIndex ) );

                if ( ( unavailableSeatsMap[ byteIndex ] & mask ) != 0 )
                        throw new ValidationException( ValidationError.SEATMAP_UNAVAILABLE_SEAT );

                map[ byteIndex ] |= mask;
        }

        /**
         * Frees up the specified seat.
         * @param row index of the row to free in the byte array
         * @param col index of the column to free in the byte array
         */
        public void freeSeat( int row, int col ) {
                // TODO
        }

        public static int getRows()
        { return rows; }

        public static int getCols()
        { return cols; }

        public static int getTotalSeats()
        { return totalSeats; }
}
