package repository;

import java.io.IOException;
import java.io.CharConversionException;
import java.io.EOFException;
import java.io.FileNotFoundException;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import exception.ParsingException;
import model.ParsingError;

/**
 * Parser class for .csv files.
 */
public final class CsvReader {
        private static CsvReader self;
        private static String path;
		private static CsvProcessor processor;

        private CsvReader() {}

        /**
         * Creates and returns a unique static new CsvParser object instance.
         * @return CsvReader object instance.
         */
        public static CsvReader init() {
                if ( self != null ) return self;

                self = new CsvReader();
		path = "";

                return self;
        }

	/**
	 * Processes the file from the set path with the set processor. Uses UTF-8 as standard charset.
	 * @throws ParsingException
	 * @see #setPath(String)
	 * @see #setProcessor(CsvProcessor)
	 */
	public boolean process() throws ParsingException
	{
		// UTF-8 will suffice, even with movie names using non-latin alphabet letters
		try( Stream<String> lines = Files.lines( Paths.get( path ), StandardCharsets.UTF_8 ) )
		{
			Optional<String> result = lines.filter( line -> processor.process( line ) )
							.findFirst();

			return result.isPresent();
		} catch (IOException e)
		{
			switch (e) {
				case FileNotFoundException _ -> throw new ParsingException( ParsingError.CSVPARSER_FILE_INVALID );
				case EOFException _ -> throw new ParsingException( ParsingError.CSVPARSER_FILE_EOF );
				case CharacterCodingException _ -> throw new ParsingException( ParsingError.CSVPARSER_FILE_CHARCODING_INVALID );
				case CharConversionException _ -> throw new ParsingException( ParsingError.CSVPARSER_FILE_CHARCONVERSION_INVALID );
				default -> throw new ParsingException( ParsingError.CSVPARSER_IOEXCEPTION );
			}
		}
	}

	public void setPath( String fpath )
	{
		Objects.requireNonNull( fpath );
		path = fpath;
	}

	public void setProcessor( CsvProcessor csvProcessor )
	{
		Objects.requireNonNull( csvProcessor );
		processor = csvProcessor;
	}
}
