package br.com.projectws.agendastartup.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jonas on 14/05/16.
 */
public class Cliente implements Parcelable {
    private String nome, telefone, tags;

    public Cliente() {}

    public Cliente(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public Cliente(String nome, String telefone, String tags) {
        this.nome = nome;
        this.telefone = telefone;
        this.tags = tags;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    Cliente(Parcel in) {
        this.nome = in.readString();
        this.telefone = in.readString();
        this.tags = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(telefone);
        dest.writeString(tags);
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Cliente> CREATOR
            = new Parcelable.Creator<Cliente>() {

        public Cliente createFromParcel(Parcel in) {
            Cliente Cliente = new Cliente();
            Cliente.nome = in.readString();
            Cliente.telefone = in.readString();
            Cliente.tags = in.readString();
            return Cliente;
        }

        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };
}
