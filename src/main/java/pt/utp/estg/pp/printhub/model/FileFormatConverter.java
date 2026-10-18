package pt.utp.estg.pp.printhub.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class FileFormatConverter implements AttributeConverter<FileFormat, String> {

    @Override
    public String convertToDatabaseColumn(FileFormat format) {
        return format == null ? null : format.getDbValue();
    }

    @Override
    public FileFormat convertToEntityAttribute(String dbValue) {
        return dbValue == null ? null : FileFormat.fromDbValue(dbValue);
    }
}