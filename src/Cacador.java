// src/Cacador.java
import java.util.List;

public class Cacador implements Ator {
    private Campo campo;
    private Localizacao localizacao;
    private boolean ativo;
    private static final int NUMERO_TIRO = 3;

    public Cacador(Campo campo, Localizacao localizacao) {
        this.campo = campo;
        this.localizacao = localizacao;
        this.ativo = true;
        campo.colocar(this, localizacao);
    }

    @Override
    public void agir(List<Ator> novosAtores) {
        if (!ativo) return;

        // Mover para uma posição livre qualquer
        Localizacao novaLocalizacao = campo.localLivreAleatoria();
        if (novaLocalizacao != null) {
            campo.limpar(localizacao);
            localizacao = novaLocalizacao;
            campo.colocar(this, localizacao);
        }

        // Atirar em N posições aleatórias
        for (int i = 0; i < NUMERO_TIRO; i++) {
            Localizacao alvo = campo.localAleatoria();
            Object objeto = campo.getObjeto(alvo);
            if (objeto instanceof Animal) {
                ((Animal) objeto).morrer();
            }
        }
    }

    @Override
    public boolean estaAtivo() {
        return ativo;
    }
}
