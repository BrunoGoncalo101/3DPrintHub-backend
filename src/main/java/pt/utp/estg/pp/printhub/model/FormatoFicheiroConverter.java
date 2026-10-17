package pt.utp.estg.pp.printhub.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class FormatoFicheiroConverter implements AttributeConverter<FormatoFicheiro, String> {

    @Override
    public String convertToDatabaseColumn(FormatoFicheiro formato) {
        return formato == null ? null : formato.getValor();
    }

    @Override
    public FormatoFicheiro convertToEntityAttribute(String valor) {
        return valor == null ? null : FormatoFicheiro.deValor(valor);
    }
}
