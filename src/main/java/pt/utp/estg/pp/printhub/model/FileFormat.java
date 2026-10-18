package pt.utp.estg.pp.printhub.model;

public enum FileFormat {
    STL("STL"),
    OBJ("OBJ"),
    MF3("3MF"); // Must be MF3 because Java identifiers cannot start with numbers

    private final String dbValue;

    FileFormat(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static FileFormat fromDbValue(String dbValue) {
        for (FileFormat format : values()) {
            if (format.dbValue.equals(dbValue)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unknown format: " + dbValue);
    }

    public static FileFormat fromFileName(String fileName) {
        if (fileName == null) {
            return null;
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == fileName.length() - 1) {
            return null;
        }
        String extension = fileName.substring(dotIndex + 1);
        for (FileFormat format : values()) {
            if (format.dbValue.equalsIgnoreCase(extension)) {
                return format;
            }
        }
        return null;
    }
}