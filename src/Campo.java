import java.util.Random;

/**
 * Representa uma grade retangular de posições no campo.
 * Cada posição pode armazenar um único animal.
 * 
 * @author David J. Barnes e Michael Kölling
 *  Traduzido por Julio César Alves
 * @version 2025.05.24
 */
public class Campo {
    // Um gerador de números aleatórios para fornecer localizações aleatórias.
    private static final Random rand = Randomizador.obterRandom();
    
    // O comprimento e a largura do campo.
    private int comprimento, largura;
    // Armazenamento para os animais.
    private Object[][] campo;

    /**
     * Representa um campo com as dimensões fornecidas.
     * @param comprimento O comprimento do campo.
     * @param largura A largura do campo.
     */
    public Campo(int comprimento, int largura) {
        this.comprimento = comprimento;
        this.largura = largura;
        campo = new Object[comprimento][largura];
    }
    
    // ... (outros métodos existentes) ...

    /**
     * Retorna as estatísticas do campo, incluindo o número de raposas e coelhos.
     * @return Um objeto EstatisticasCampo com as contagens de raposas e coelhos.
     */
    public EstatisticasCampo getEstatisticas() {
        int numRaposas = 0;
        int numCoelhos = 0;

        // Percorre o campo e conta o número de raposas e coelhos
        for (int linha = 0; linha < comprimento; linha++) {
            for (int coluna = 0; coluna < largura; coluna++) {
                Object objeto = campo[linha][coluna];
                if (objeto instanceof Raposa) {
                    numRaposas++;
                } else if (objeto instanceof Coelho) {
                    numCoelhos++;
                }
            }
        }

        return new EstatisticasCampo(numRaposas, numCoelhos);
    }

    // ... (outros métodos existentes) ...
}
