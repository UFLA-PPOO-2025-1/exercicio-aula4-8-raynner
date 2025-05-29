import java.util.List;
import java.util.Random;

public abstract class Animal implements Ator {

    // Características compartilhadas por todos os coelhos (atributos estáticos, da classe).

    // Um gerador de números aleatórios compartilhado para controlar a reprodução.
    private static final Random rand = Randomizador.obterRandom();

    // Características individuais (atributos comuns, de instância).
    
    // A idade do animal.
    private int idade;
    // Indica se o animal está vivo ou não.
    private boolean vivo;
    // A localização do animal.
    private Localizacao localizacao;
    // O campo ocupado.
    private Campo campo;

    public Animal(boolean idadeAleatoria, Campo campo, Localizacao localizacao)
    {
        vivo = true;
        idade = 0;
        if(idadeAleatoria) {
            idade = rand.nextInt(obterIdadeMaxima());
        }
        this.campo = campo;
        definirLocalizacao(localizacao);
    }

    public int obterIdade() {
        return idade;
    }
    
    /**
     * Aumenta a idade.
     * Isso pode resultar na morte do coelho.
     */
    protected void incrementarIdade()
    {
        idade++;
        if(idade > obterIdadeMaxima()) {
            morrer();
        }
    }
    
    /**
     * Verifica se o animal está vivo ou não.
     * @return verdadeiro se o animal ainda estiver vivo.
     */
    @Override
    public boolean estaAtivo() {
        return vivo;
    }
    
    /**
     * Define que o animal não está mais vivo.
     * Ele é removido do campo.
     */
    protected void morrer()
    {
        vivo = false;
        if(localizacao != null) {
            campo.limpar(localizacao);
            localizacao = null;
            campo = null;
        }
    }
    
    /**
     * Retorna a localização do animal.
     * @return A localização do animal.
     */
    public Localizacao obterLocalizacao()
    {
        return localizacao;
    }
    
    /**
     * Coloca o animal na nova localização no campo fornecido.
     * @param novaLocalizacao A nova localização do animal.
     */
    protected void definirLocalizacao(Localizacao novaLocalizacao)
    {
        if(localizacao != null) {
            campo.limpar(localizacao);
        }
        localizacao = novaLocalizacao;
        campo.colocar(this, novaLocalizacao);
    }

    public Campo obterCampo() {
        return campo;
    }

    /**
     * Gera um número representando o número de nascimentos,
     * se puder procriar.
     * @return O número de nascimentos (pode ser zero).
     */
    protected int procriar()
    {
        int nascimentos = 0;
        if(podeProcriar() && rand.nextDouble() <= obterProbabilidadeReproducao()) {
            nascimentos = rand.nextInt(obterTamanhoMaximoNinhada()) + 1;
        }
        return nascimentos;
    }
 
    /**
     * Verifica se este animal deve dar à luz neste passo.
     * Novos nascimentos serão feitos em locais vizinhos livres.
     * @param novosAnimais Uma lista para retornar os animais recém-nascidos.
     */
    protected void reproduzir(List<Ator> novosAtores) {
    // Novos animais nascem em locais vizinhos.
    List<Localizacao> locaisLivres = obterCampo().localizacoesVizinhasLivres(obterLocalizacao());
    int nascimentos = procriar();
    for (int n = 0; n < nascimentos && locaisLivres.size() > 0; n++) {
        Localizacao local = locaisLivres.remove(0);
        Animal filhote = criarNovoFilhote(false, obterCampo(), local);
        novosAtores.add(filhote); // 🔥 Agora adiciona na lista de Ator
    }
}

    /**
     * Uma raposa pode procriar se tiver atingido a idade de reprodução.
     */
    private boolean podeProcriar()
    {
        return obterIdade() >= obterIdadeReproducao();
    }

    @Override
    public abstract void agir(List<Ator> novosAtores);

    protected abstract int obterIdadeMaxima();
    
    protected abstract Animal criarNovoFilhote(boolean idadeAleatoria, Campo campo, Localizacao localizacao);
    
    protected abstract int obterIdadeReproducao();
    
    protected abstract double obterProbabilidadeReproducao();
    
    protected abstract int obterTamanhoMaximoNinhada();
}
