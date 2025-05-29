import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Um simulador simples de predador-presa, baseado em um campo retangular contendo 
 * coelhos e raposas.
 * 
 * @author David J. Barnes e Michael Kölling
 *  Traduzido por Julio César Alves
 * @version 2025.05.29
 */
public class Simulador {
    // Constantes de configuração do simulador.
    private static final int LARGURA_PADRAO = 120;
    private static final int COMPRIMENTO_PADRAO = 80;

    // Lista de todos os animais presentes no campo.
    private List<Animal> listaDeAnimais;
    // Representação do ambiente da simulação.
    private Campo ambiente;
    // Contador de passos da simulação.
    private int contadorDePassos;
    // Lista de visualizações da simulação.
    private List<VisaoSimulador> listaDeVisoes;

    /**
     * Construtor que cria um simulador com dimensões padrão.
     */
    public Simulador() {
        this(COMPRIMENTO_PADRAO, LARGURA_PADRAO);
    }

    /**
     * Construtor que cria um simulador com dimensões personalizadas.
     * @param comprimento O comprimento do campo.
     * @param largura A largura do campo.
     */
    public Simulador(int comprimento, int largura) {
        if (largura <= 0 || comprimento <= 0) {
            System.out.println("As dimensões devem ser maiores que zero.");
            System.out.println("Utilizando valores padrão.");
            comprimento = COMPRIMENTO_PADRAO;
            largura = LARGURA_PADRAO;
        }

        listaDeAnimais = new ArrayList<>();
        ambiente = new Campo(comprimento, largura);
        listaDeVisoes = new ArrayList<>();

        // Adiciona visão textual
        VisaoSimulador visaoTexto = new VisaoDeTexto();
        listaDeVisoes.add(visaoTexto);

        // Adiciona visão de grade
        VisaoSimulador visaoGrade = new VisaoDeGrade(comprimento, largura, this);
        GeradorDePopulacoes.definirCores(visaoGrade);
        listaDeVisoes.add(visaoGrade);

        // Adiciona visão gráfica
        VisaoSimulador visaoGrafico = new VisaoDeGrafico(800, 400, 500);
        GeradorDePopulacoes.definirCores(visaoGrafico);
        listaDeVisoes.add(visaoGrafico);

        reiniciarSimulacao();
    }

    /**
     * Executa uma simulação longa (4000 passos).
     */
    public void executarSimulacaoLonga() {
        executarSimulacao(4000, 0);
    }

    /**
     * Executa a simulação pelo número determinado de passos, 
     * com um possível atraso entre os passos.
     * @param totalDePassos Quantidade de passos.
     * @param atrasoMs Atraso entre passos em milissegundos.
     */
    public void executarSimulacao(int totalDePassos, int atrasoMs) {
        for (int passoAtual = 1; 
             passoAtual <= totalDePassos && listaDeVisoes.get(0).ehViavel(ambiente);
             passoAtual++) {
            executarUmPasso();
            if (atrasoMs > 0) {
                pausarSimulacao(atrasoMs);
            }
        }
        reabilitarOpcoesDeVisoes();
    }

    /**
     * Executa a simulação por um único passo.
     */
    public void executarUmPasso() {
        contadorDePassos++;

        List<Animal> novosAnimais = new ArrayList<>();

        for (Iterator<Animal> iterador = listaDeAnimais.iterator(); iterador.hasNext();) {
            Animal animal = iterador.next();
            animal.agir(novosAnimais);
            if (!animal.estaVivo()) {
                iterador.remove();
            }
        }

        listaDeAnimais.addAll(novosAnimais);
        atualizarTodasAsVisoes();
    }

    /**
     * Reinicia a simulação para o estado inicial.
     */
    public void reiniciarSimulacao() {
        contadorDePassos = 0;
        listaDeAnimais.clear();
        for (VisaoSimulador visao : listaDeVisoes) {
            visao.reiniciar();
        }

        GeradorDePopulacoes.povoar(ambiente, listaDeAnimais);
        atualizarTodasAsVisoes();
        reabilitarOpcoesDeVisoes();
    }

    /**
     * Atualiza todas as visualizações.
     */
    private void atualizarTodasAsVisoes() {
        for (VisaoSimulador visao : listaDeVisoes) {
            visao.mostrarStatus(contadorDePassos, ambiente);
        }
    }

    /**
     * Reabilita as opções nas visualizações.
     */
    private void reabilitarOpcoesDeVisoes() {
        for (VisaoSimulador visao : listaDeVisoes) {
            visao.reabilitarOpcoes();
        }
    }

    /**
     * Pausa a simulação por um tempo definido.
     * @param milissegundos Tempo de pausa em milissegundos.
     */
    private void pausarSimulacao(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            // Interrupção ignorada.
        }
    }
}
