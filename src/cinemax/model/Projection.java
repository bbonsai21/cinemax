package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import exception.ValidationException;

public final class Projection {
        private Film film;
        private LocalDateTime dateTime;
        private BigDecimal price;

        public Projection( Film film, LocalDateTime dateTime, BigDecimal price ) throws ValidationException {
                Objects.requireNonNull( film );
                Objects.requireNonNull( dateTime );
                Objects.requireNonNull( price );

                if ( price.compareTo( BigDecimal.ZERO ) == -1 )
                        throw new ValidationException( ValidationError.FILM_PRICE_NEGATIVE );

                this.film = film;
                this.dateTime = dateTime;
                this.price = price;
        }

        public Film getFilm()
        { return this.film; }

        public LocalDateTime getDateTime()
        { return this.dateTime; }

        public BigDecimal getPrice()
        { return this.price; }
}