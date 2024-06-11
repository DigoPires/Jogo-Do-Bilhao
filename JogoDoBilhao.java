// Developed by Heverton Oliveira, Matheus Assunção, Pedro Gabriel, Rodrigo Pires e Ryan Cavalcanti.

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class JogoDoBilhao {

    // Classe interna para representar uma pergunta
    static class PerguntaClass {
        private String texto;
        private String resposta;
        private String dica;
        private String eliminarDuas;
        private String alternativaExtra;

        // Construtor da PerguntaClass
        public PerguntaClass(String texto, String resposta, String dica, String eliminarDuas, String alternativaExtra) {
            this.texto = texto;
            this.resposta = resposta;
            this.dica = dica;
            this.eliminarDuas = eliminarDuas;
            this.alternativaExtra = alternativaExtra;
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

        public String getAlternativaExtra(){
            return alternativaExtra;
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
    int quantPerg;
    int pulosRestantes;
    int dicasRestantes;
    int eliminarDuasRestantes;
    double valorQuestao;
    double saldo;

    // Variavel para verificar quando o usuário utilizou a opção "Eliminar Duas"
    boolean usouEliminarQuestao = false;
    // Variavel para verificar quando o usuário utilizou a opção "Dica"
    boolean usouDica = false;

    // Opções de resposta SIM e NÃO
    String[] opcoes_SIM_NAO = {"Sim", "Não"};

    // Método principal do jogo
    public void jogo(String pergunta, String resposta, String dica, String eliminarDuas, String alternativaExtra) {

        String respostaUsuario;

        // Mensagens a serem exibidas dependendo das opções usadas
        String mensagemNormal = "Pergunta Valendo:  R$" + String.format("%,.2f", valorQuestao) + "\n\n" + quantPerg + " - " + pergunta + "\n\n1 - Pular Pergunta (" + pulosRestantes + ")     2 - Dicas (" + dicasRestantes + ")     3 - Eliminar Duas (" + eliminarDuasRestantes + ") \n\nSaldo Atual:  R$" + String.format("%,.2f", saldo) + "\n\n";

        String mensagemDica = "Pergunta Valendo:  R$" + String.format("%,.2f", valorQuestao) + "\n\n" + quantPerg + " - " + pergunta + "\n\n" + dica + "\n\n1 - Pular Pergunta (" + pulosRestantes + ")     3 - Eliminar Duas (" + eliminarDuasRestantes + ") \n\nSaldo Atual:  R$" + String.format("%,.2f", saldo) + "\n\n";

        String mensagemEliminarDuas = "Pergunta Valendo:  R$" + String.format("%,.2f", valorQuestao) + "\n\n" + quantPerg + " - " + eliminarDuas + "\n\n1 - Pular Pergunta (" + pulosRestantes + ")     2 - Dicas (" + dicasRestantes + ")\n\nSaldo Atual:  R$" + String.format("%,.2f", saldo) + "\n\n";

        String mensagemDica_EliminarDuas = "Pergunta Valendo:  R$" + String.format("%,.2f", valorQuestao) + "\n\n" + quantPerg + " - " + eliminarDuas + "\n\n" + dica + "\n\n1 - Pular Pergunta (" + pulosRestantes + ")  \n\nSaldo Atual:  R$" + String.format("%,.2f", saldo) + "\n\n";

        // Exibe a pergunta ao usuário com base nas opções usadas
        if (usouDica && usouEliminarQuestao) {
            respostaUsuario = JOptionPane.showInputDialog(null, mensagemDica_EliminarDuas);
            if (respostaUsuario != null && !respostaUsuario.matches("[" + resposta + "," + alternativaExtra + ",1]")) {
                JOptionPane.showMessageDialog(null, "Opção Inválida!");
                jogo(pergunta, resposta, dica, eliminarDuas, alternativaExtra);
                return;
            }
        }
        else if (usouDica) {
            respostaUsuario = JOptionPane.showInputDialog(null, mensagemDica);
            if (respostaUsuario != null && !respostaUsuario.matches("[a-dA-D,1,3]")) {
                JOptionPane.showMessageDialog(null, "Opção Inválida!");
                jogo(pergunta, resposta, dica, eliminarDuas, alternativaExtra);
                return;
            }
        }
        else if (usouEliminarQuestao) {
            respostaUsuario = JOptionPane.showInputDialog(null, mensagemEliminarDuas);
            if (respostaUsuario != null && !respostaUsuario.matches("[" + resposta + "," + alternativaExtra + ",1-2]")) {
                JOptionPane.showMessageDialog(null, "Opção Inválida!");
                jogo(pergunta, resposta, dica, eliminarDuas, alternativaExtra);
                return;
            }
        }
        else {
            respostaUsuario = JOptionPane.showInputDialog(null, mensagemNormal);
        }

        // Verifica se a resposta do usuário é válida
        if (respostaUsuario == null || respostaUsuario.trim().isEmpty()) {
            int resp = JOptionPane.showOptionDialog(null, "Deseja voltar para o menu?", "Voltar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes_SIM_NAO, opcoes_SIM_NAO[0]);
            if (resp == JOptionPane.YES_OPTION) {
                menu();
                return;
            } else {
                jogo(pergunta, resposta, dica, eliminarDuas, alternativaExtra);
                return;
            }
        }

        // Processa a resposta do usuário
        switch (respostaUsuario) {
            case "1" -> {
                if (pulosRestantes <= 0) {
                    JOptionPane.showMessageDialog(null, "Seus Pulos acabaram!");
                    jogo(pergunta, resposta, dica, eliminarDuas, alternativaExtra);
                } else {
                    int resp = JOptionPane.showOptionDialog(null, "Você deseja Pular a pergunta? \n\nPulos Restantes: " + pulosRestantes, "Pular", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes_SIM_NAO, opcoes_SIM_NAO[0]);
                    if (resp == JOptionPane.YES_OPTION) {
                        pulosRestantes--;
                        usouEliminarQuestao = false;
                        usouDica = false;
                        return;
                    } else {
                        jogo(pergunta, resposta, dica, eliminarDuas, alternativaExtra);
                    }
                }
                return;
            }
            case "2" -> {
                if (dicasRestantes > 0) {
                    JOptionPane.showMessageDialog(null, dica);
                    dicasRestantes--;
                    usouDica = true;
                    jogo(pergunta, resposta, dica, eliminarDuas, alternativaExtra);
                } else {
                    JOptionPane.showMessageDialog(null, "Suas Dicas acabaram!");
                    jogo(pergunta, resposta, dica, eliminarDuas, alternativaExtra);
                }
                return;
            }
            case "3" -> {
                if (eliminarDuasRestantes > 0) {
                    usouEliminarQuestao = true;
                    eliminarDuasRestantes--;
                    jogo(pergunta, resposta, dica, eliminarDuas, alternativaExtra);
                } else {
                    JOptionPane.showMessageDialog(null, "Sua opção de eliminar 2 acabou!");
                    jogo(pergunta, resposta, dica, eliminarDuas, alternativaExtra);
                }
                return;
            }
            default -> {
                if (respostaUsuario != null && !respostaUsuario.matches("[a-dA-D,1-3]")) {
                    JOptionPane.showMessageDialog(null, "Opção Inválida!");
                    jogo(pergunta, resposta, dica, eliminarDuas, alternativaExtra);
                    return;
                }
                else if (respostaUsuario.equalsIgnoreCase(resposta)) {
                    JOptionPane.showMessageDialog(null, "Resposta Correta");
                    quantPerg++;
                    saldo += valorQuestao;

                } else {
                    JOptionPane.showMessageDialog(null, "Resposta Incorreta! Você perdeu!");
                    menu();
                    return;
                }
            }
        }

        usouEliminarQuestao = false;
        usouDica = false;
    }


    // Método que controla o loop do jogo
    public void loopJogo(int contador) {
        while (quantPerg <= contador) {

            // Sorteia uma pergunta da lista
            PerguntaClass sortearPergunta = perguntas.get((int) (Math.random() * perguntas.size()));

            String pergunta = sortearPergunta.getTexto();
            String resposta = sortearPergunta.getResposta();
            String dica = sortearPergunta.getDica();
            String eliminarDuas = sortearPergunta.getEliminarDuas();
            String alternativaExtra = sortearPergunta.getAlternativaExtra();

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
                jogo(pergunta, resposta, dica, eliminarDuas, alternativaExtra);
            }
        }
    }

    // Método para resetar as variáveis do jogo
    public void resetarJogo() {
        quantPerg = 1;
        saldo = 0;
        pulosRestantes = 2;
        dicasRestantes = 2;
        eliminarDuasRestantes = 1;
        usouEliminarQuestao = false;
        usouDica = false;
    }

    // Método para iniciar o nível fácil do jogo
    public void rodada1() {

        JOptionPane.showMessageDialog(null, "Primeira Rodada!");

        // Cria uma nova lista de perguntas
        perguntas = new ArrayList<>();

        // Adiciona perguntas à lista
        perguntas.add(new PerguntaClass("Qual animal representa o símbolo da paz? \n\na - Pomba. \nb - Gato \nc - Cobra \nd - Elefante", "a", "DICA: É um pássaro.", "Qual animal representa o símbolo da paz? \n\na - Pomba. \nb - Gato", "b"));

        perguntas.add(new PerguntaClass("Qual é o sobrenome mais comum no Brasil? \n\na - Silva \nb - Santos \nc - Souza \nd - Oliveira", "a", "DICA: Começa com S.", "Qual é o sobrenome mais comum no Brasil? \n\na - Silva \nc - Souza", "c"));

        perguntas.add(new PerguntaClass("Qual inventor brasileiro é conhecido como o “pai da aviação”? \n\na - Tom Jobim \nb - Paulo Coelho. \nc - Santos Dumont. \nd - Ary Barroso", "c", "DICA: Seu nome é conhecido mundialmente.", "Qual inventor brasileiro é conhecido como o “pai da aviação”? \n\nb - Paulo Coelho. \nc - Santos Dumont.", "b"));

        perguntas.add(new PerguntaClass("Qual cidade brasileira é conhecida como Terra da Garoa? \n\na - Rio de Janeiro. \nb - Salvador. \nc - Brasília. \nd - São Paulo.", "d", "DICA: É a cidade mais populosa do Brasil.", "Qual cidade brasileira é conhecida como Terra da Garoa? \n\nc - Brasília. \nd - São Paulo.", "c"));

        perguntas.add(new PerguntaClass("Quantos dias tem um ano bissexto? \n\na - 364 dias \nb - 365 dias \nc - 366 dias \nd - 600 dias", "c", "DICA: É um dia a mais que o ano comum.", "Quantos dias tem um ano bissexto? \n\nb - 365 dias \nc - 366 dias", "b"));

        perguntas.add(new PerguntaClass("Quantos anos tem um século? \n\na - 10 \nb - 100 \nc - 110 \nd - 1000", "b", "DICA: Um século é igual a 10 décadas.", "Quantos anos tem um século? \n\nb - 100 \nc - 110", "c"));

        perguntas.add(new PerguntaClass("Quais destas doenças são sexualmente transmissíveis? \n\na - Aids, tricomoníase e ebola \nb - Chikungunya, aids e herpes genital \nc - Gonorreia, clamídia e sífilis \nd - Botulismo, cistite e gonorreia", "c", "DICA: Inclui gonorreia.", "Quais destas doenças são sexualmente transmissíveis? \n\nb - Chikungunya, aids e herpes genital \nc - Gonorreia, clamídia e sífilis", "b"));

        perguntas.add(new PerguntaClass("Quantas casas decimais tem o número pi? \n\na - Duas \nb - Centenas \nc - Infinitas \nd - Vinte", "c", "DICA: Não tem fim.", "Quantas casas decimais tem o número pi? \n\na - Duas \nc - Infinitas", "a"));

        perguntas.add(new PerguntaClass("Qual o número de jogadores em cada time numa partida de futebol? \n\na - 9 \nb - 11 \nc - 10 \nd - 12", "b", "DICA: É um número ímpar.", "Qual o número de jogadores em cada time numa partida de futebol? \n\nb - 11 \nd - 12", "d"));

        perguntas.add(new PerguntaClass("Qual é o nome do processo pelo qual as plantas produzem seu próprio alimento usando luz solar? \n\na - Respiração \nb - Fotossíntese \nc - Quimiossíntese \nd - Fermentação", "b", "DICA: Utiliza a luz solar para converter dióxido de carbono e água em glicose.", "Qual é o nome do processo pelo qual as plantas produzem seu próprio alimento usando luz solar? \n\nb - Fotossíntese \nc - Quimiossíntese", "c"));

        perguntas.add(new PerguntaClass("Qual é a língua oficial do Brasil? \n\na - Espanhol \nb - Português \nc - Inglês \nd - Francês", "b", "DICA: É a mesma língua falada em Portugal.", "Qual é a língua oficial do Brasil? \n\na - Espanhol \nb - Português", "a"));

        perguntas.add(new PerguntaClass("Qual foi o primeiro país a aprovar o casamento entre pessoas do mesmo sexo? \n\na - Canadá \nb - Espanha \nc - Holanda \nd - Suécia", "c", "DICA: A legalização ocorreu em 2001.", "Qual foi o primeiro país a aprovar o casamento entre pessoas do mesmo sexo? \n\nb - Espanha \nc - Holanda", "b"));

        perguntas.add(new PerguntaClass("Qual é a moeda oficial do Japão? \n\na - Yuan \nb - Won \nc - Iene \nd - Dólar", "c", "DICA: Seu símbolo é ¥.", "Qual é a moeda oficial do Japão? \n\na - Yuan \nc - Iene", "a"));

        perguntas.add(new PerguntaClass("Qual cientista formulou a lei da gravitação universal? \n\na - Albert Einstein \nb - Isaac Newton \nc - Galileo Galilei \nd - James Clerk Maxwell", "b", "DICA: Ele também desenvolveu as leis do movimento.", "Qual cientista formulou a lei da gravitação universal? \n\na - Albert Einstein \nb - Isaac Newton", "a"));

        perguntas.add(new PerguntaClass("Qual é o maior órgão do corpo humano? \n\na - Fígado \nb - Cérebro \nc - Pele \nd - Coração", "c", "DICA: Cobre toda a superfície do corpo.", "Qual é o maior órgão do corpo humano? \n\nb - Cérebro \nc - Pele", "b"));

        perguntas.add(new PerguntaClass("Qual é o ponto mais alto da Terra? \n\na - K2 \nb - Everest \nc - Kangchenjunga \nd - Lhotse", "b", "DICA: Está localizado na cordilheira do Himalaia.", "Qual é o ponto mais alto da Terra? \n\na - K2 \nb - Everest", "a"));

        perguntas.add(new PerguntaClass("Qual é o maior país da América do Sul? \n\na - Argentina \nb - Brasil \nc - Peru \nd - Colômbia", "b", "DICA: É o quinto maior país do mundo em área territorial.", "Qual é o maior país da América do Sul? \n\na - Argentina \nb - Brasil", "a"));

        perguntas.add(new PerguntaClass("Qual é a fórmula química da água? \n\na - CO2 \nb - H2O \nc - O2 \nd - H2SO4", "b", "DICA: Consiste em dois átomos de hidrogênio e um de oxigênio.", "Qual é a fórmula química da água? \n\na - CO2 \nb - H2O", "a"));

        // Cria uma nova lista de perguntas repetidas
        repetidas = new ArrayList<>();

        // Inicia o loop do jogo e reseta as ajudas
        valorQuestao = 1000000;
        resetarJogo();
        loopJogo(5);
        rodada2();
        return;
    }

    public void rodada2(){

        JOptionPane.showMessageDialog(null, "Segunda Rodada!");

        perguntas = new ArrayList<>();

        perguntas.add(new PerguntaClass("Qual é o maior oceano do mundo? \n\na - Atlântico \nb - Índico \nc - Pacífico \nd - Ártico", "c", "DICA: Fica entre a Ásia e as Américas.", "Qual é o maior oceano do mundo? \n\nb - Índico \nc - Pacífico", "b"));

        perguntas.add(new PerguntaClass("Qual planeta é conhecido como o Planeta Vermelho? \n\na - Marte \nb - Júpiter \nc - Saturno \nd - Vênus", "a", "DICA: É o quarto planeta do Sistema Solar.", "Qual planeta é conhecido como o Planeta Vermelho? \n\na - Marte \nc - Saturno", "c"));

        perguntas.add(new PerguntaClass("Quem pintou a Mona Lisa? \n\na - Vincent van Gogh \nb - Pablo Picasso \nc - Leonardo da Vinci \nd - Claude Monet", "c", "DICA: Era também um inventor famoso.", "Quem pintou a Mona Lisa? \n\nb - Pablo Picasso \nc - Leonardo da Vinci", "b"));

        perguntas.add(new PerguntaClass("Qual é o elemento químico mais abundante no universo? \n\na - Oxigênio \nb - Hidrogênio \nc - Carbono \nd - Hélio", "b", "DICA: É o primeiro elemento da tabela periódica.", "Qual é o elemento químico mais abundante no universo? \n\na - Oxigênio \nb - Hidrogênio", "a"));

        perguntas.add(new PerguntaClass("Quantos segundos há em uma hora? \n\na - 600 \nb - 3000 \nc - 3600 \nd - 6000", "c", "DICA: É o produto de 60 minutos por 60 segundos.", "Quantos segundos há em uma hora? \n\nc - 3600 \nd - 6000", "d"));

        perguntas.add(new PerguntaClass("Qual é a capital da Austrália? \n\na - Sydney \nb - Melbourne \nc - Brisbane \nd - Canberra", "d", "DICA: Não é a maior cidade do país.", "Qual é a capital da Austrália? \n\na - Sydney \nd - Canberra", "a"));

        perguntas.add(new PerguntaClass("Em que ano o homem pisou na Lua pela primeira vez? \n\na - 1965 \nb - 1969 \nc - 1972 \nd - 1975", "b", "DICA: A missão foi a Apollo 11.", "Em que ano o homem pisou na Lua pela primeira vez? \n\na - 1965 \nb - 1969", "a"));

        perguntas.add(new PerguntaClass("Qual é a maior floresta tropical do mundo? \n\na - Floresta Amazônica \nb - Floresta do Congo \nc - Floresta da Indonésia \nd - Floresta da Nova Guiné", "a", "DICA: Está localizada na América do Sul.", "Qual é a maior floresta tropical do mundo? \n\na - Floresta Amazônica \nb - Floresta do Congo", "b"));

        repetidas = new ArrayList<>();

        valorQuestao = 10000000;
        loopJogo(10);
        rodada3();
        return;
    }

    public void rodada3(){

        JOptionPane.showMessageDialog(null, "Terceira Rodada!");

        perguntas = new ArrayList<>();

        perguntas.add(new PerguntaClass("Qual é o segundo maior país do mundo em área territorial? \n\na - Estados Unidos \nb - Canadá \nc - China \nd - Rússia", "b", "DICA: Fica na América do Norte.", "Qual é o segundo maior país do mundo em área territorial? \n\na - Estados Unidos \nb - Canadá", "a"));

        perguntas.add(new PerguntaClass("Qual elemento químico tem o símbolo 'K'? \n\na - Cálcio \nb - Potássio \nc - Criptônio \nd - Cloro", "b", "DICA: É um metal alcalino.", "Qual elemento químico tem o símbolo 'K'? \n\na - Cálcio \nb - Potássio", "a"));

        perguntas.add(new PerguntaClass("Qual é a capital da Finlândia? \n\na - Helsinque \nb - Estocolmo \nc - Oslo \nd - Copenhague", "a", "DICA: É a maior cidade da Finlândia.", "Qual é a capital da Finlândia? \n\na - Helsinque \nc - Oslo", "c"));

        perguntas.add(new PerguntaClass("Qual é o maior deserto do mundo? \n\na - Deserto do Saara \nb - Deserto da Arábia \nc - Deserto de Gobi \nd - Antártica", "d", "DICA: É um deserto polar.", "Qual é o maior deserto do mundo? \n\na - Deserto do Saara \nd - Antártica", "a"));

        perguntas.add(new PerguntaClass("Quem desenvolveu a teoria da relatividade? \n\na - Isaac Newton \nb - Albert Einstein \nc - Niels Bohr \nd - Galileo Galilei", "b", "DICA: Ele recebeu o Prêmio Nobel de Física em 1921.", "Quem desenvolveu a teoria da relatividade? \n\na - Isaac Newton \nb - Albert Einstein", "a"));

        perguntas.add(new PerguntaClass("Qual país é conhecido como a Terra do Sol Nascente? \n\na - China \nb - Coreia do Sul \nc - Japão \nd - Tailândia", "c", "DICA: É uma ilha no leste da Ásia.", "Qual país é conhecido como a Terra do Sol Nascente? \n\nb - Coreia do Sul \nc - Japão", "b"));

        perguntas.add(new PerguntaClass("Qual é o maior planeta do Sistema Solar? \n\na - Terra \nb - Marte \nc - Júpiter \nd - Saturno", "c", "DICA: É conhecido por sua grande mancha vermelha.", "Qual é o maior planeta do Sistema Solar? \n\na - Terra \nc - Júpiter", "a"));

        perguntas.add(new PerguntaClass("Quem pintou o teto da Capela Sistina? \n\na - Leonardo da Vinci \nb - Michelangelo \nc - Rafael \nd - Donatello", "b", "DICA: Ele também esculpiu a estátua de David.", "Quem pintou o teto da Capela Sistina? \n\na - Leonardo da Vinci \nb - Michelangelo", "a"));

        perguntas.add(new PerguntaClass("Qual é o rio mais longo do mundo? \n\na - Amazonas \nb - Nilo \nc - Yangtzé \nd - Mississipi", "b", "DICA: Está localizado na África.", "Qual é o rio mais longo do mundo? \n\na - Amazonas \nb - Nilo", "a"));

        repetidas = new ArrayList<>();

        valorQuestao = 100000000;
        loopJogo(15);
        rodadaFinal();
        return;
    }

    public void rodadaFinal(){

        JOptionPane.showMessageDialog(null, "Última Pergunta!");

        perguntas = new ArrayList<>();

        perguntas.add(new PerguntaClass("Qual é a capital da Mongólia? \n\na - Ulaanbaatar \nb - Bishkek \nc - Astana \nd - Tashkent", "a", "DICA: Seu nome significa 'Herói Vermelho'.", "Qual é a capital da Mongólia? \n\na - Ulaanbaatar \nc - Astana", "c"));

        perguntas.add(new PerguntaClass("Quem escreveu 'Guerra e Paz'? \n\na - Fyodor Dostoevsky \nb - Leo Tolstoy \nc - Anton Chekhov \nd - Vladimir Nabokov", "b", "DICA: É um dos maiores autores russos.", "Quem escreveu 'Guerra e Paz'? \n\na - Fyodor Dostoevsky \nb - Leo Tolstoy", "a"));

        perguntas.add(new PerguntaClass("Qual é a unidade de medida da radiação ionizante? \n\na - Coulomb \nb - Sievert \nc - Pascal \nd - Ohm", "b", "DICA: É usada para medir a dose absorvida por tecidos humanos.", "Qual é a unidade de medida da radiação ionizante? \n\na - Coulomb \nb - Sievert", "a"));

        perguntas.add(new PerguntaClass("Qual é a galáxia mais próxima da Via Láctea? \n\na - Andrômeda \nb - Triângulo \nc - LMC (Grande Nuvem de Magalhães) \nd - SMC (Pequena Nuvem de Magalhães)", "c", "DICA: Está a cerca de 160 mil anos-luz de distância.", "Qual é a galáxia mais próxima da Via Láctea? \n\na - Andrômeda \nc - LMC (Grande Nuvem de Magalhães)", "a"));

        perguntas.add(new PerguntaClass("Em que ano aconteceu a Revolução Francesa? \n\na - 1776 \nb - 1789 \nc - 1812 \nd - 1848", "b", "DICA: É conhecida como o início da era moderna.", "Em que ano aconteceu a Revolução Francesa? \n\na - 1776 \nb - 1789", "a"));

        perguntas.add(new PerguntaClass("Qual elemento químico tem o símbolo 'W'? \n\na - Tungstênio \nb - Tungstato \nc - Tântalo \nd - Titânio", "a", "DICA: É um metal de alta densidade usado em filamentos de lâmpadas.", "Qual elemento químico tem o símbolo 'W'? \n\na - Tungstênio \nd - Titânio", "d"));

        perguntas.add(new PerguntaClass("Quem foi o primeiro cientista a propor que a Terra orbita o Sol? \n\na - Johannes Kepler \nb - Galileo Galilei \nc - Nicolau Copérnico \nd - Tycho Brahe", "c", "DICA: Seu modelo é conhecido como heliocentrismo.", "Quem foi o primeiro cientista a propor que a Terra orbita o Sol? \n\nb - Galileo Galilei \nc - Nicolau Copérnico", "b"));

        perguntas.add(new PerguntaClass("Qual é a distância da Terra ao Sol? \n\na - 93 mil milhas \nb - 93 milhões de milhas \nc - 93 mil quilômetros \nd - 93 milhões de quilômetros", "b", "DICA: A distância é aproximadamente 150 milhões de quilômetros.", "Qual é a distância da Terra ao Sol? \n\nb - 93 milhões de milhas \nd - 93 milhões de quilômetros", "d"));

        perguntas.add(new PerguntaClass("Em qual país a língua Basca é falada? \n\na - Espanha \nb - Itália \nc - França \nd - Suíça", "a", "DICA: É uma região conhecida como País Basco.", "Em qual país a língua Basca é falada? \n\na - Espanha \nc - França", "c"));

        repetidas = new ArrayList<>();

        valorQuestao = 1000000000;
        loopJogo(16);
        JOptionPane.showMessageDialog(null, "Parabéns, Você ganhou R$1.000.000.000,00!!! \n\nSaldo Atual: R$ R$1.000.000.000,00");
        return;
    }

    // Método para ler as Regras do jogo
    public void regras(){
        JOptionPane.showMessageDialog(null, " ##### Regras ##### \n\n " +
                "1. Perguntas e Alternativas:\n Cada pergunta possui quatro alternativas de resposta (a, b, c, d).\n Apenas uma alternativa é a correta. \n\n " +
                "2. Ajudas:\nPular Pergunta: O jogador pode pular uma pergunta até duas vezes durante o jogo.\n" +
                "Dica: O jogador pode pedir dicas duas vezes durante o jogo. \n" +
                "Eliminar duas alternativas: O jogador pode usar esta ajuda uma vez para eliminar duas alternativas incorretas. \n\n" +
                "3. Fim de jogo:\nSe o jogador errar a resposta, o jogo termina e ele perde \n" +
                "Se o jogador acertar, avança para a proxima pergunta e acumula saldo \n\n" +
                "##### Boa Sorte #####" );
        return;
    }

    // Método para exibir o menu principal
    public void menu() {
        resetarJogo();
        int itemMenu;
        String menu = "##### JOGO DO BILHÃO ##### \n\nEscolha uma opção: \n\n1 - Regras \n2 - Jogar \n\n3 - Sair \n\n";

        while (true) {
            try {
                itemMenu = Integer.parseInt(JOptionPane.showInputDialog(null, menu));
                switch (itemMenu) {
                    case 1 -> regras();
                    case 2 -> rodada1();  // Inicia o nível fácil
                    case 3 -> {
                        JOptionPane.showMessageDialog(null, "Saindo...");
                        System.exit(0);  // Sair do jogo
                    }
                    default -> JOptionPane.showMessageDialog(null, "Opção Inválida!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                int resp = JOptionPane.showOptionDialog(null, "Deseja Sair?", "Sair", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes_SIM_NAO, opcoes_SIM_NAO[0]);
                if (resp == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }
    }

    // Método main para iniciar o programa
    public static void main(String[] args) {
        JogoDoBilhao Quiz = new JogoDoBilhao();
        Quiz.menu();
    }
}
