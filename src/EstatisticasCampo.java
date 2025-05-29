import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Mantém as contagens de populações no campo.
 */
public class EstatisticasCampo {
    private Map<String, Contador> contadores;
    private boolean contagemValida;

    /**
     * Cria o objeto de estatísticas.
     */
    public EstatisticasCampo() {
        contadores = new HashMap<>();
        contagemValida = true;
    }

    /**
     * Zera todas as contagens.
     */
    public void zerar() {
        contagemValida = false;
        for (Contador contador : contadores.values()) {
            contador.zerar();
        }
    }

    /**
     * Incrementa a contagem para uma classe de animal.
     */
    public void incrementarContagem(String nomeClasse) {
        Contador contador = contadores.get(nomeClasse);
        if (contador == null) {
            contador = new Contador(nomeClasse);
            contadores.put(nomeClasse, contador);
        }
        contador.incrementar();
    }

    /**
     * Calcula as contagens de animais no campo.
     */
    public void gerarContagem(Campo campo) {
        zerar();
        for (Object animal : campo) {
            if (animal != null) {
                incrementarContagem(animal.getClass().getSimpleName());
            }
        }
        contagemValida = true;
    }

    /**
     * Retorna uma string formatada das contagens.
     */
    public String obterDetalhes(Campo campo) {
        StringBuilder buffer = new StringBuilder();
        if (!contagemValida) {
            gerarContagem(campo);
        }
        for (Contador contador : contadores.values()) {
            buffer.append(contador.obterNome());
            buffer.append(": ");
            buffer.append(contador.obterContagem());
            buffer.append(' ');
        }
        return buffer.toString().trim();
    }

    /**
     * Retorna as contagens como um Map.
     */
    public Map<String, Integer> obterContagem(Campo campo) {
        if (!contagemValida) {
            gerarContagem(campo);
        }
        Map<String, Integer> mapaContagem = new HashMap<>();
        for (Contador contador : contadores.values()) {
            mapaContagem.put(contador.obterNome(), contador.obterContagem());
        }
        return mapaContagem;
    }

    /**
     * Verifica se a simulação ainda é viável (mais de uma espécie).
     */
    public boolean ehViavel(Campo campo) {
        if (!contagemValida) {
            gerarContagem(campo);
        }
        int contadorNaoZero = 0;
        for (Contador contador : contadores.values()) {
            if (contador.obterContagem() > 0) {
                contadorNaoZero++;
            }
        }
        return contadorNaoZero > 1;
    }
}
