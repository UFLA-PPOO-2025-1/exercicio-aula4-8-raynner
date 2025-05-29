public interface VisaoSimulador {
    void mostrarStatus(int passo, Campo campo);
    void reiniciar();
    boolean ehViavel(Campo campo);
    void reabilitarOpcoes();
}
