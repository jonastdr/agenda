package br.com.projectws.agendastartup.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cliente implements Parcelable {
    private String id, nome, telefone, endereco, email;

    public Cliente() {}

    public Cliente(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public Cliente(String nome, String telefone, String endereco, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    Cliente(Parcel in) {
        this.id = in.readString();
        this.nome = in.readString();
        this.telefone = in.readString();
        this.endereco = in.readString();
        this.email = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nome);
        dest.writeString(telefone);
        dest.writeString(endereco);
        dest.writeString(email);
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Cliente> CREATOR
            = new Parcelable.Creator<Cliente>() {

        public Cliente createFromParcel(Parcel in) {
            Cliente Cliente = new Cliente();
            Cliente.id = in.readString();
            Cliente.nome = in.readString();
            Cliente.telefone = in.readString();
            Cliente.endereco = in.readString();
            Cliente.email = in.readString();
            return Cliente;
        }

        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };
}
