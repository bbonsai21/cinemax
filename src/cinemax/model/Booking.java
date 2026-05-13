package model;

import java.util.Objects;

import model.SeatMap.Seat;

/**
 * Class meant to store raw booking data in memory.
 */
public final class Booking {
        private final Projection projection;
        private final Seat[] seats;

        public Booking( Projection projection, Seat[] seats ) {
                Objects.requireNonNull( projection );
                Objects.requireNonNull( seats );

                this.projection = projection;
                this.seats = seats;
        }
}
