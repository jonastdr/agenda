package br.com.projectws.agendastartup.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by guilhermino on 5/15/16.
 */
public class Mensagem implements Parcelable {

    private String id, titulo, mensagem;

    public Mensagem() {}

    public Mensagem(String titulo, String mensagem) {
        this.titulo = titulo;
        this.mensagem = mensagem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    Mensagem(Parcel in) {
        this.titulo = in.readString();
        this.mensagem = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(mensagem);
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Mensagem> CREATOR
            = new Parcelable.Creator<Mensagem>() {

        public Mensagem createFromParcel(Parcel in) {
            Mensagem Mensagem = new Mensagem();
            Mensagem.titulo = in.readString();
            Mensagem.mensagem = in.readString();
            return Mensagem;
        }

        public Mensagem[] newArray(int size) {
            return new Mensagem[size];
        }
    };
}
