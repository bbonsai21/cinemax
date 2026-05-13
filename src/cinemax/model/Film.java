package model;
import exception.ValidationException;

public class Film {
        public final String name;
        public final int year, length, minAge;

        Film( String name, int year, int length, int minAge ) throws ValidationException {
                if ( name == null || name.isBlank() )
                        throw new ValidationException( ValidationError.FILM_TITLE_EMPTY );

                if ( year < ( int )ValidationError.FILM_YEAR_INVALID.getParams()[0] ||
                        year > ( int )ValidationError.FILM_YEAR_INVALID.getParams()[1] )
                        throw new ValidationException( ValidationError.FILM_YEAR_INVALID );

                if ( length < 0 ) throw new ValidationException( ValidationError.FILM_LENGTH_NEGATIVE );

                if ( minAge < 0 ) throw new ValidationException( ValidationError.FILM_MIN_AGE_NEGATIVE );

                this.name = name;
                this.year = year;
                this.length = length;
                this.minAge = minAge;
        }

        @Override
        public String toString() {
                return this.name + "( " + this.year + ")";
        }
}