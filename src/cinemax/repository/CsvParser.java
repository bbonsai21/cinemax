package repository;

import exception.ParsingException;
import model.Projection;

public final class CsvParser {
        private static CsvParser self;
        
        private CsvParser() {}

        /**
         * Creates and returns a unique static new CsvParser object instance.
         * @return CsvParser object instance. instance.
         */
        public CsvParser initiate() {
                if ( self != null ) return self;

                self = new CsvParser();
                return self;
        }

        /**
         * Stores a projection in proiezioni.csv file.
         * @param projection the projection to store in the file
         * @see Projection
         */
        public void saveProjection( Projection projection ) {
                
        }

        /**
         * Deletes an existing projection from proiezioni.csv file.
         * @throws ParsingException 
         * @param projection the projection to be removed from the file.
         */
        public void removeProjection( Projection projection ) throws ParsingException {
                
        }

        /**
         * Updates an existing projection from proiezioni.csv file.
         * @param projection
         * @throws ParsingException
         */
        public void updateProjection( Projection projection ) throws ParsingException {

        }
}
