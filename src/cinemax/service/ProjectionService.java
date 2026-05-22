package service;

import java.time.LocalDateTime;

import model.Projection;

/**
 * Class operating between memory and disk, retrieving projections.
 * @see Projection
 * @see CsvParser
 */
public final class ProjectionService {
        private ProjectionService() {}

        /**
         * Retrieves an array of projections, given a dateTime.
         * @param dateTime LocalDateTime of the projection we're looking for
         * @return Projection[] array of compatible projections
         * @see Projection
         */
        public static Projection[] getProjectionFromDateTime( LocalDateTime dateTime ) {
                return null;
        }

        /**
         * Retrieves an array of projections, given a name.
         * @param name String of the projection name we're looking for
         * @return Projection[] array of compatible projections
         * @see Projection
         */
        public static Projection[] getProjectionFromName( String name ) {
                return null;
        }

        /**
         * Retrieves an array of projections, given a year.
         * @param year int of the projection year we're looking for
         * @return Projection[] array of compatible projections
         * @see Projection
         */
        public static Projection[] getProjectionFromYear( int year ) {
                return null;
        }

        /**
         * Retrieves an array of projections, given a director name.
         * @param director String of the projection director we're looking for
         * @return Projection[] array of compatible projections
         * @see Projection
         */
        public static Projection[] getProjectionFromDirector( String director ) {
                return null;
        }
}