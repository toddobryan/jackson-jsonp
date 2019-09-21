package jacksonjsonp;


import com.fasterxml.jackson.core.JsonLocation;

public class JsonLocationJackson implements javax.json.stream.JsonLocation {
    private long lineNumber;
    private long columnNumber;
    private long streamOffset;

    JsonLocationJackson(long lineNumber, long columnNumber, long streamOffset) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.streamOffset = streamOffset;
    }

    static JsonLocationJackson fromJacksonLocation(JsonLocation location, long plusOffset) {

        this(location.getLineNr(),
                location.getColumnNr() + plusOffset,
                location.getCharOffset() + plusOffset);
    }

    JsonLocationJackson(JsonLocation location) {
        this(location, 0L);
    }

    @Override
    public long getLineNumber() {
        return lineNumber;
    }

    @Override
    public long getColumnNumber() {
        return columnNumber;
    }

    @Override
    public long getStreamOffset() {
        return streamOffset;
    }

    @Override
    public String toString() {
        return String.format("[%d,%d,%d]", getLineNumber(), getColumnNumber(), getStreamOffset());
    }
}
