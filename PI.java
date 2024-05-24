import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PI {

    // Classe interna para representar uma pergunta
    static class PerguntaClass {
        private String texto;
        private String resposta;
        private String dica;
        private String eliminarDuas;

        // Construtor da PerguntaClass
        public PerguntaClass(String texto, String resposta, String dica, String eliminarDuas) {
            this.texto = texto;
            this.resposta = resposta;
            this.dica = dica;
            this.eliminarDuas = eliminarDuas;
        }

        // Métodos para obter os valores dos atributos da pergunta
        public String getTexto() {
            return texto;
        }

        public String getResposta() {
            return resposta;
        }

        public String getDica() {
            return dica;
        }

        public String getEliminarDuas() {
            return eliminarDuas;
        }
    }

    // Classe interna para representar perguntas repetidas
    static class PerguntasRepetidas {
        private String textoRepetido;

        // Construtor da PerguntasRepetidas
        public PerguntasRepetidas(String texto) {
            this.textoRepetido = texto;
        }

        // Método para obter o texto repetido
        public String getTextoRepetido() {
            return textoRepetido;
        }
    }

    // Lista de perguntas
    private ArrayList<PerguntaClass> perguntas;

    // Lista de perguntas repetidas
    private ArrayList<PerguntasRepetidas> repetidas;

    // Variáveis de estado do jogo
    private int pontos = 0;
    private int quantPerg = 1;
    int pulosRestantes = 3;
    int dicasRestantes = 2;
    int eliminarDuasRestantes = 2;
    double valorQuestao = 0;
    double saldo = 0;

    // Variavel para verificar quando o usuário utilizou o "Eliminar Duas"
    boolean eliminarQuestao = false;

    // Opções de resposta SIM e NÃO
    String[] opcoes_SIM_NAO = {"Sim", "Não"};

    // Método principal do jogo
    public void jogoDoBilhao(String pergunta, String resposta, String dica, String eliminarDuas) {

        String respostaUsuario;

        // Mensagens a serem exibidas dependendo se a opção de eliminar duas respostas está ativa
        String mensagemNormal = "Pergunta Valendo:  R$"+ String.format("%,.2f", valorQuestao) + "\n\n" + quantPerg + " - " + (pergunta + "\n\n5 - Pular Pergunta (" + pulosRestantes + ")     6 - Dicas (" + dicasRestantes + ")     7 - Eliminar Duas (" + eliminarDuasRestantes + ") \n\nSaldo Atual:  R$" + String.format("%,.2f", saldo) + "\n\n");
        String mensagemEliminarDuas = "Pergunta Valendo:  R$"+ String.format("%,.2f", valorQuestao) + "\n\n" + quantPerg + " - " + (eliminarDuas + "\n\n5 - Pular Pergunta (" + pulosRestantes + ")     6 - Dicas (" + dicasRestantes + ")\n\nSaldo Atual:  R$" + String.format("%,.2f", saldo) + "\n\n");

        // Exibe a pergunta ao usuário
        if (!eliminarQuestao) {
            respostaUsuario = JOptionPane.showInputDialog(null, mensagemNormal);
        } else {
            respostaUsuario = JOptionPane.showInputDialog(null, mensagemEliminarDuas);
        }

        if (respostaUsuario == null) {
            int resp = JOptionPane.showOptionDialog(null, "Deseja voltar para o menu?", "Voltar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes_SIM_NAO, opcoes_SIM_NAO[0]);
            if (resp == JOptionPane.YES_OPTION) {
                menu();
            }
            else{
                jogoDoBilhao(pergunta, resposta, dica, eliminarDuas);
            }
        }

        // Se o usuário escolhe a opção de eliminar duas respostas
        if (respostaUsuario.equals("7")) {
            if (eliminarDuasRestantes > 0) {
                respostaUsuario = JOptionPane.showInputDialog(null, mensagemEliminarDuas);
                eliminarQuestao = true;
                eliminarDuasRestantes--;
            } else {
                JOptionPane.showMessageDialog(null, "Sua opção de eliminar 2 acabou!");
                jogoDoBilhao(pergunta, resposta, dica, eliminarDuas);
            }
        }

        // Processa a resposta do usuário
        switch (respostaUsuario) {
            case "5" -> {
                if (pulosRestantes <= 0) {
                    JOptionPane.showMessageDialog(null, "Seus Pulos acabaram!");
                    jogoDoBilhao(pergunta, resposta, dica, eliminarDuas);
                } else {
                    int resp = JOptionPane.showOptionDialog(null, "Você deseja Pular a pergunta? \n\nPulos Restantes: " + pulosRestantes, "Pular", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes_SIM_NAO, opcoes_SIM_NAO[0]);
                    if (resp == JOptionPane.YES_OPTION) {
                        pulosRestantes--;
                        eliminarQuestao = false;
                        return;
                    } else {
                        jogoDoBilhao(pergunta, resposta, dica, eliminarDuas);
                    }
                }
            }
            case "6" -> {
                if (dicasRestantes > 0) {
                    JOptionPane.showMessageDialog(null, dica);
                    dicasRestantes--;
                    jogoDoBilhao(pergunta, resposta, dica, eliminarDuas);
                } else {
                    JOptionPane.showMessageDialog(null, "Suas Dicas acabaram!");
                    jogoDoBilhao(pergunta, resposta, dica, eliminarDuas);
                }
            }
            default -> {
                if (respostaUsuario.equalsIgnoreCase(resposta)) {
                    JOptionPane.showMessageDialog(null, "Resposta Correta");
                    pontos += 1;
                    quantPerg += 1;
                    saldo += valorQuestao;
                } else {
                    JOptionPane.showMessageDialog(null, "Resposta Incorreta!");
                    quantPerg += 1;
                }
            }
        }

        eliminarQuestao = false;
    }

    // Método que controla o loop do jogo
    public void loopJogo() {
        while (quantPerg <= 5) {

            valorQuestao += 1000;
            // Sorteia uma pergunta da lista
            PerguntaClass sortearPergunta = perguntas.get((int) (Math.random() * perguntas.size()));

            String pergunta = sortearPergunta.getTexto();
            String resposta = sortearPergunta.getResposta();
            String dica = sortearPergunta.getDica();
            String eliminarDuas = sortearPergunta.getEliminarDuas();

            boolean perguntaRepetida = false;

            // Verifica se a pergunta já foi feita antes
            for (PerguntasRepetidas p : repetidas) {
                if (p.getTextoRepetido().equals(pergunta)) {
                    perguntaRepetida = true;
                    break;
                }
            }

            // Se a pergunta não foi repetida, apresenta-a ao usuário
            if (!perguntaRepetida) {
                repetidas.add(new PerguntasRepetidas(pergunta));
                jogoDoBilhao(pergunta, resposta, dica, eliminarDuas);
            }
        }

        // Exibe a quantidade de pontos do usuário e reseta o jogo
        JOptionPane.showMessageDialog(null, "Quantidade de Pontos: " + pontos + "/5");
        resetarJogo();
        menu();
    }

    // Método para resetar as variáveis do jogo
    public void resetarJogo() {
        quantPerg = 1;
        pontos = 0;
        pulosRestantes = 3;
        dicasRestantes = 2;
        eliminarDuasRestantes = 2;
    }

    // Método para iniciar o nível fácil do jogo
    public void facil() {
        // Cria uma nova lista de perguntas
        perguntas = new ArrayList<>();

        // Adiciona perguntas à lista
        perguntas.add(new PerguntaClass("Qual animal representa o símbolo da paz? \n\na - Pomba. \nb - Gato \nc - Cobra \nd - Elefante", "a", "DICA: É um pássaro.", "Qual animal representa o símbolo da paz? \n\na - Pomba. \nb - Gato"));
        perguntas.add(new PerguntaClass("Qual é o sobrenome mais comum no Brasil? \n\na - Silva \nb - Santos \nc - Souza \nd - Oliveira", "a", "DICA: Começa com S.", "Qual é o sobrenome mais comum no Brasil? \n\na - Silva \nc - Souza"));
        perguntas.add(new PerguntaClass("Qual inventor brasileiro é conhecido como o “pai da aviação”? \n\na - Tom Jobim \nb - Paulo Coelho. \nc - Santos Dumont. \nd - Ary Barroso", "c", "DICA: Seu nome é conhecido mundialmente.", "Qual inventor brasileiro é conhecido como o “pai da aviação”? \n\nb - Paulo Coelho. \nc - Santos Dumont."));
        perguntas.add(new PerguntaClass("Qual cidade brasileira é conhecida como Terra da Garoa? \n\na - Rio de Janeiro. \nb - Salvador. \nc - Brasília. \nd - São Paulo.", "d", "DICA: É a cidade mais populosa do Brasil.", "Qual cidade brasileira é conhecida como Terra da Garoa? \n\nc - Brasília. \nd - São Paulo."));
        perguntas.add(new PerguntaClass("Quantos dias tem um ano bissexto? \n\na - 364 dias \nb - 365 dias \nc - 366 dias \nd - 600 dias", "c", "DICA: É um dia a mais que o ano comum.", "Quantos dias tem um ano bissexto? \n\nb - 365 dias \nc - 366 dias"));
        perguntas.add(new PerguntaClass("Quantos anos tem um século? \n\na - 10 \nb - 100 \nc - 110 \nd - 1000", "b", "DICA: Um século é igual a 10 décadas.", "Quantos anos tem um século? \n\nb - 100 \nc - 110"));
        perguntas.add(new PerguntaClass("Quais destas doenças são sexualmente transmissíveis? \n\na - Aids, tricomoníase e ebola \nb - Chikungunya, aids e herpes genital \nc - Gonorreia, clamídia e sífilis \nd - Botulismo, cistite e gonorreia", "c", "DICA: Inclui gonorreia.", "Quais destas doenças são sexualmente transmissíveis? \n\nb - Chikungunya, aids e herpes genital \nc - Gonorreia, clamídia e sífilis"));
        perguntas.add(new PerguntaClass("Quantas casas decimais tem o número pi? \n\na - Duas \nb - Centenas \nc - Infinitas \nd - Vinte", "c", "DICA: Não tem fim.", "Quantas casas decimais tem o número pi? \n\na - Duas \nc - Infinitas"));
        perguntas.add(new PerguntaClass("Qual o número de jogadores em cada time numa partida de futebol? \n\na - 9 \nb - 11 \nc - 10 \nd - 12", "b", "DICA: É um número ímpar.", "Qual o número de jogadores em cada time numa partida de futebol? \n\nb - 11 \nd - 12"));

        // Cria uma nova lista de perguntas repetidas
        repetidas = new ArrayList<>();

        // Inicia o loop do jogo
        loopJogo();
    }

    // Método para exibir o menu principal
    public void menu() {
        int itemMenu;
        String menu = "##### JOGO DO BILHÃO ##### \n\nEscolha o nível: \n\n1 - Fácil \n2 - Médio \n3 - Difícil \n\n4 - Sair\n\n";

        while (true) {
            try {
                itemMenu = Integer.parseInt(JOptionPane.showInputDialog(null, menu));
                switch (itemMenu) {
                    case 1 -> facil();  // Inicia o nível fácil
                    case 2 -> JOptionPane.showMessageDialog(null, "Em processo...");  // Placeholder para nível médio
                    case 3 -> JOptionPane.showMessageDialog(null, "Em processo...");  // Placeholder para nível difícil
                    case 4 -> {
                        JOptionPane.showMessageDialog(null, "Saindo...");
                        System.exit(0);  // Sai do jogo
                    }
                    default -> JOptionPane.showMessageDialog(null, "Opção Inválida!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                int resp = JOptionPane.showOptionDialog(null, "Deseja Sair?", "Sair", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes_SIM_NAO, opcoes_SIM_NAO[0]);
                if (resp == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Saindo...");
                    System.exit(0);
                }
            }
        }
    }

    // Método main para iniciar o programa
    public static void main(String[] args) {
        PI Quiz = new PI();
        Quiz.menu();
    }
}
