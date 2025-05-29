import java.util.Map;

/**
 * Uma visualização textual do simulador.
 * Mostra no console o número de cada espécie a cada passo.
 */
public class VisaoDeTexto implements VisaoSimulador {
    private EstatisticasCampo estatisticas;

    public VisaoDeTexto() {
        estatisticas = new EstatisticasCampo();
    }

    @Override
    public void mostrarStatus(int passo, Campo campo) {
        if (!ehViavel(campo)) {
            System.out.println("A população colapsou.");
            return;
        }

        Map<String, Integer> contagem = estatisticas.obterContagem(campo);
        StringBuilder status = new StringBuilder();

        status.append("Passo ").append(passo).append(" - ");

        for (Map.Entry<String, Integer> entry : contagem.entrySet()) {
            status.append(entry.getKey()).append(": ").append(entry.getValue()).append(" ");
        }

        System.out.println(status.toString().trim());
    }

    @Override
    public void reiniciar() {
        estatisticas.zerar();
    }

    @Override
    public boolean ehViavel(Campo campo) {
        return estatisticas.ehViavel(campo);
    }

    @Override
    public void reabilitarOpcoes() {
        // Sem opções na visão textual.
    }
}
