package br.edu.ifsp.enums;

public enum Role {
    ALUNO, SERVIDOR, CAE;

    @Override
    public String toString() {
        return name();
    }
}
