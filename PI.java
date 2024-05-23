import java.util.ArrayList;
import javax.swing.JOptionPane;;
 
public class PI {
 
    // Construtor de Perguntas (Usado para todas as matérias)
    static class PerguntaClass {
        private String texto;
        private String resposta;
        private String dica;
        private String eliminarDuas;
 
        public PerguntaClass(String texto, String resposta, String dica, String eliminarDuas) {
            this.texto = texto;
            this.resposta = resposta;
            this.dica = dica;
            this.eliminarDuas = eliminarDuas;
        }
 
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
 
    // Construtor de Perguntas Repetidas
    static class PerguntasRepetidas {
        private String textoRepetido;
 
        public PerguntasRepetidas(String texto){
            this.textoRepetido = texto;
        }
 
        public String getTextoRepetido(){
            return textoRepetido;
        }
       
    }
 
    // Lista de perguntas
    private ArrayList<PerguntaClass> perguntas;
 
    // Lista de perguntas repetidas
    private ArrayList<PerguntasRepetidas> repetidas;
 
    // Quantidade de Pontos
    private int pontos = 0;
 
    private int quantPerg = 1;
 
    int pulosRestantes = 3;
 
    int dicasRestantes = 2;

    int eliminarDuasRestantes = 2;
    boolean eliminarQuestao = false;
 
    String[] opcoes_SIM_NAO = {"Sim", "Não"};
 
 
    public void jogoDoBilhao(String pergunta, String resposta, String dica, String eliminarDuas){

        String respostaUsuario;

        String mensagemNormal = Integer.toString(quantPerg) + " - " + (pergunta + ("\n\n5 - Pular Pergunta (" + pulosRestantes + ")     6 - Dicas (" + dicasRestantes + ")     7 - Eliminar Duas (" + eliminarDuasRestantes + ")\n\n"));
        String mensagemEliminarDuas = Integer.toString(quantPerg) + " - " + (eliminarDuas + ("\n\n5 - Pular Pergunta (" + pulosRestantes + ")     6 - Dicas (" + dicasRestantes + ")\n\n"));

        if (!eliminarQuestao) {
            respostaUsuario = JOptionPane.showInputDialog(null, mensagemNormal);
        }
        else{        
            respostaUsuario = JOptionPane.showInputDialog(null, mensagemEliminarDuas);
        }
        
        if (respostaUsuario.equals("7")) {
            if(eliminarDuasRestantes > 0){
                respostaUsuario = JOptionPane.showInputDialog(null, mensagemEliminarDuas);
                eliminarQuestao = true;
                eliminarDuasRestantes--;
            }
            else{
                JOptionPane.showMessageDialog(null, "Sua opção de eliminar 2 acabou!");
                jogoDoBilhao(pergunta, resposta, dica, eliminarDuas);
            }
        }

        switch (respostaUsuario) {
            case "5" -> {
                if (pulosRestantes <= 0) {
                    JOptionPane.showMessageDialog(null, "Seus Pulos acabaram!");
                    jogoDoBilhao(pergunta, resposta, dica, eliminarDuas);
                }
                else{
                    int resp = JOptionPane.showOptionDialog(null, "Você deseja Pular a pergunta? \n\nPulos Restantes: " + pulosRestantes, "Pular", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes_SIM_NAO, opcoes_SIM_NAO[0]);
                    if (resp == JOptionPane.YES_OPTION) {
                        pulosRestantes--;
                        eliminarQuestao = false;
                        return;
                    }
                    else{
                        jogoDoBilhao(pergunta, resposta, dica, eliminarDuas);
                    }
                }
            }
            case "6" -> {
                if (dicasRestantes > 0) {
                    JOptionPane.showMessageDialog(null, dica);
                    dicasRestantes--;
                    jogoDoBilhao(pergunta, resposta, dica, eliminarDuas);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Suas Dicas acabaram!");
                    jogoDoBilhao(pergunta, resposta, dica, eliminarDuas);
                }
            }
            default -> {
                if (respostaUsuario.equalsIgnoreCase(resposta)) {
                    JOptionPane.showMessageDialog(null, "Resposta Correta");
                    pontos += 1;
                    quantPerg += 1;
                }
         
                else {
                    JOptionPane.showMessageDialog(null, "Resposta Incorreta!");
                    quantPerg += 1;
                }
            }
        }

        eliminarQuestao = false;
    }

    public void loopJogo(){
        while (quantPerg <= 5) {
 
            // Sorteia uma pergunta da lista
            PerguntaClass sortearPergunta = perguntas.get((int) (Math.random() * perguntas.size()));
 
            String pergunta = sortearPergunta.getTexto();
            String resposta = sortearPergunta.getResposta();
            String dica = sortearPergunta.getDica();
            String eliminarDuas = sortearPergunta.getEliminarDuas();
 
            boolean perguntaRepetida = false;
 
            // Verifica se a pergunta já foi feita antes
            for (PerguntasRepetidas p : repetidas) {
                // Compara o texto da pergunta atual com o texto das perguntas repetidas
                if (p.getTextoRepetido().equals(pergunta)) {
                    perguntaRepetida = true;
                    break;
                }
            }
 
            // Se a pergunta não foi repetida, apresenta-a ao usuário
            if (!perguntaRepetida) {

                // Adiciona pergunta à lista de perguntas repetidas
                repetidas.add(new PerguntasRepetidas(pergunta));
 
                jogoDoBilhao(pergunta, resposta, dica, eliminarDuas);
            }
        }
 
        JOptionPane.showMessageDialog(null, "Quantidade de Pontos: " + pontos + "/5");
        resetarJogo();
        menu();
    }

    public void resetarJogo() {
        quantPerg = 1;
        pontos = 0;
        pulosRestantes = 3;
        dicasRestantes = 2;
        eliminarDuasRestantes = 2;
    }

    public void facil(){
       
        // Cria uma nova lista de perguntas
        perguntas = new ArrayList<>();
 
        // Adicione aqui suas perguntas à lista
        perguntas.add(new PerguntaClass("Qual animal representa o símbolo da paz? \n\na - Pomba. \nb - Gato \nc - Cobra \nd - Elefante", "a", "$$$$DICA1####", "Qual animal representa o símbolo da paz? \n\na - Pomba. \nb - Gato"));
        perguntas.add(new PerguntaClass("Qual é o sobrenome mais comum no Brasil? \n\na - Silva \nb - Santos \nc - Souza \nd - Oliveira", "a", "$$$$DICA2####", "Qual é o sobrenome mais comum no Brasil? \n\na - Silva \nc - Souza")); 
        perguntas.add(new PerguntaClass("Qual inventor brasileiro é conhecido com o “pai da aviação”? \n\na - Tom Jobim \nb - Paulo Coelho. \nc - Santos Dumont. \nd - Ary Barroso", "c", "$$$$DICA3####", "Qual inventor brasileiro é conhecido com o “pai da aviação”? \n\nb - Paulo Coelho. \nc - Santos Dumont."));
        perguntas.add(new PerguntaClass("Qual cidade brasileira é conhecida como Terra da Garoa? \n\na - Rio de Janeiro. \nb - Salvador. \nc - Brasília. \nd - São Paulo.", "d", "$$$$DICA4####", "Qual cidade brasileira é conhecida como Terra da Garoa? \n\nc - Brasília. \nd - São Paulo."));
        perguntas.add(new PerguntaClass("Quantos dias tem um ano bissexto? \n\na - 364 dias \nb - 365 dias \nc - 366 dias \nd - 600 dias", "c", "$$$$DICA5####", "Quantos dias tem um ano bissexto? \n\nb - 365 dias \nc - 366 dias"));
        perguntas.add(new PerguntaClass("Quantos anos tem um século? \n\na - 10 \nb - 100 \nc - 110 \nd - 1000", "b", "$$$$DICA6####", "Quantos anos tem um século? \n\nb - 100 \nc - 110"));
        perguntas.add(new PerguntaClass("Quais destas doenças são sexualmente transmissíveis? \n\na - Aids, tricomoníase e ebola \nb - Chikungunya, aids e herpes genital \nc - Gonorreia, clamídia e sífilis \nd - Botulismo, cistite e gonorreia", "c", "$$$$DICA7####", "Quais destas doenças são sexualmente transmissíveis? \n\nb - Chikungunya, aids e herpes genital \nc - Gonorreia, clamídia e sífilis"));
        perguntas.add(new PerguntaClass("Quantas casas decimais tem o número pi? \n\na - Duas \nb - Centenas \nc - Infinitas \nd - Vinte", "c", "$$$$DICA7####", "Quantas casas decimais tem o número pi? \n\na - Duas \nc - Infinitas"));
        perguntas.add(new PerguntaClass("Qual o número de jogadores em cada time numa partida de futebol? \n\na - 9 \nb - 11 \nc - 10 \nd - 12", "b", "$$$$DICA7####", "Qual o número de jogadores em cada time numa partida de futebol? \n\nb - 11 \nd - 12"));

        // Cria uma nova lista de perguntas repetidas
        repetidas = new ArrayList<>();

        loopJogo();
    }  
     
 
    public void menu(){
        int itemMenu;
        String menu = "##### JOGO DO BILHÃO ##### \n\nEscolha o nível: \n\n1 - Fácil \n2 - Médio \n3 - Difícil \n\n4 - Sair\n\n";
 
        while (true) {
            try {
                itemMenu = Integer.parseInt(JOptionPane.showInputDialog(null, menu));
                switch (itemMenu) {
                    case 1 -> facil();
 
                    case 2 -> JOptionPane.showMessageDialog(null, "Em processo...");
                    //médio
 
                    case 3 -> JOptionPane.showMessageDialog(null, "Em processo...");
                    //dificil
 
                    case 4 -> {
                        JOptionPane.showMessageDialog(null, "Saindo...");
                        System.exit(0);
                        // saindo
 
                    }
                    default -> JOptionPane.showMessageDialog(null, "Opção Inválida!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            catch (NumberFormatException e) {
                int resp = JOptionPane.showOptionDialog(null, "Deseja Sair?", "Sair", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes_SIM_NAO, opcoes_SIM_NAO[0]);
                if (resp == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }
    }
 
    public static void main(String[] args) {
        PI Quiz = new PI();
        Quiz.menu();
    }
}