package jacksonjsonp;

class Util {
    private Util() {}

    static void throwIfNull(Object value, String name) {
        if (value == null) {
            throw new NullPointerException(name + " can not be null");
        }
    }
}
