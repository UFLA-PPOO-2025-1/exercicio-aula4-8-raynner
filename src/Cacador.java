// src/Cacador.java
import java.util.List;
import java.util.Random;

public class Cacador implements Ator {
    private Campo campo;
    private Localizacao localizacao;
    private static final int NUM_TIROS = 3;
    private static final Random rand = Randomizador.obterRandom();

    public Cacador(Campo campo, Localizacao localizacao) {
        this.campo = campo;
        definirLocalizacao(localizacao);
    }

    @Override
    public void agir(List<Ator> novosAtores) {
        // Move para posição aleatória livre
        Localizacao novaLocalizacao = campo.localLivreAleatoria();
        if (novaLocalizacao != null) {
            definirLocalizacao(novaLocalizacao);
        }

        // Atira em alvos aleatórios
        for (int i = 0; i < NUM_TIROS; i++) {
            Localizacao alvo = campo.localAleatoria();
            Object objeto = campo.getObjeto(alvo);
            if (objeto instanceof Animal animal && animal.estaAtivo()) {
                animal.morrer(); // Mata o animal
            }
        }
    }

    @Override
    public boolean estaAtivo() {
        return true; // Caçadores sempre ativos
    }

    public void definirLocalizacao(Localizacao novaLocalizacao) {
        if (localizacao != null) {
            campo.limpar(localizacao);
        }
        localizacao = novaLocalizacao;
        campo.colocar(this, localizacao);
    }

    public Localizacao obterLocalizacao() {
        return localizacao;
    }

    public Campo obterCampo() {
        return campo;
    }
}
