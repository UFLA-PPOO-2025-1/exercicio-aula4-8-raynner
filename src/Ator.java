import java.util.List;

public interface Ator {
    /**
     * Define o comportamento do ator na simulação.
     * @param atores A lista de atores que podem ser modificados durante a ação.
     */
    void agir(List<Ator> atores);

    /**
     * Indica se o ator está ativo na simulação.
     * @return true se ativo, false caso contrário.
     */
    boolean estaAtivo();
}