package GusFigue.example.STUK_Produtos.Entity;

public enum Canais {

    LOJA_FISICA("LF"),
    ECOMMERCE("EC"),
    AMBOS("Ambos");

    private final String canal;

    Canais(String canal) {
        this.canal = canal;
    }

    public String getCanal() {
        return canal;
    }
}
