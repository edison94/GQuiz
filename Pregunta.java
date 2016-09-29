package local.lrobalino.gquiz;

import java.io.Serializable;


public class Pregunta implements Serializable {
    private String text;
    private boolean resp;
    private int foto;

    public Pregunta(boolean resp, String text, int foto) {
        this.resp = resp;
        this.text = text;
        this.foto = foto;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isResp() {
        return resp;
    }

    public void setResp(boolean resp) {
        this.resp = resp;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
