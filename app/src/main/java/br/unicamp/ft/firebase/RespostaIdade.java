package br.unicamp.ft.firebase;

public class RespostaIdade {
    private String nome;
    private String answer;
    private String chosen;

    public RespostaIdade() {
    }

    public RespostaIdade(String nome, String answer, String chosen) {
        this.nome = nome;
        this.answer = answer;
        this.chosen = chosen;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getChosen() {
        return chosen;
    }

    public void setChosen(String chosen) {
        this.chosen = chosen;
    }
}
