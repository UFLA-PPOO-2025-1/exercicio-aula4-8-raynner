/**
 * Uma visão textual da simulação que imprime no terminal o número
 * de raposas e coelhos a cada passo.
 * 
 * Exemplo de saída:
 * Passo N - Raposas: 121 Coelhos: 266
 * 
 * @author 
 * @version 2025.05.29
 */
public class VisaoDeTexto implements VisaoSimulador
{
    // Objeto que coleta estatísticas do campo.
    private EstatisticasCampo estatisticas;

    /**
     * Constrói uma visão textual.
     */
    public VisaoDeTexto()
    {
        estatisticas = new EstatisticasCampo();
    }

    /**
     * Define uma cor para uma classe de animal. Como esta é uma visão textual,
     * essa operação não tem efeito.
     * @param classeAnimal A classe do animal.
     * @param cor A cor associada (ignorável).
     */
    @Override
    public void definirCor(Class<?> classeAnimal, java.awt.Color cor)
    {
        // Não utilizado na visão textual.
    }

    /**
     * Determina se a simulação deve continuar a ser executada.
     * Aqui delegamos para o método da classe EstatisticasCampo.
     * @param campo O campo da simulação.
     * @return true se houver mais de uma espécie viva.
     */
    @Override
    public boolean ehViavel(Campo campo)
    {
        return estatisticas.ehViavel(campo);
    }

    /**
     * Mostra o estado atual do campo.
     * Imprime no terminal uma linha do tipo:
     * Passo N - Raposas: X Coelhos: Y
     * @param passo O passo atual da simulação.
     * @param campo O campo cujo estado será exibido.
     */
    @Override
    public void mostrarStatus(int passo, Campo campo)
    {
        estatisticas.invalidarContagens();  // <<-- chama aqui para resetar as contagens antes de contar de novo

        int numRaposas = estatisticas.obterContagemPopulacao(campo, Raposa.class);
        int numCoelhos = estatisticas.obterContagemPopulacao(campo, Coelho.class);

        System.out.println("Passo " + passo + " - Raposas: " + numRaposas + " Coelhos: " + numCoelhos);
    }
        /**
     * Prepara para uma nova execução.
     * Reinicia as estatísticas.
     */
    @Override
    public void reiniciar()
    {
        estatisticas.reiniciar();
    }

    /**
     * Reabilita as opções da visão.
     * Aqui não há opções, então não faz nada.
     */
    @Override
    public void reabilitarOpcoes()
    {
        // Sem opções para a visão textual.
    }
}
