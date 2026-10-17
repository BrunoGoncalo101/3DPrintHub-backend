package pt.utp.estg.pp.printhub.model;

public enum FormatoFicheiro {
    STL("STL"),
    OBJ("OBJ"),
    MF3("3MF"); // tem que ser MF3 pk nao pode começar por numeros

    private final String valor;

    FormatoFicheiro(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor; //valor na BD
    }

    public static FormatoFicheiro deValor(String valor){
        for (FormatoFicheiro f : values()) {
            if (f.valor.equals(valor)){
                return f;
            }
        }
        throw new IllegalArgumentException("Formato desconhecido: " + valor);
    }


public static FormatoFicheiro deNomeFicheiro(String nomeFicheiro){
    if(nomeFicheiro == null) {
        return null;
    }
    int ponto = nomeFicheiro.lastIndexOf('.');
    if(ponto < 0 || ponto == nomeFicheiro.length() - 1) {
        return null;
    }
    String extensao = nomeFicheiro.substring(ponto + 1);
    for (FormatoFicheiro f : values()) {
        if (f.valor.equalsIgnoreCase(extensao)){
            return f;
        }
    }
    return null;
}
}