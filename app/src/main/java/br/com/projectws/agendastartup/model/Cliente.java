package br.com.projectws.agendastartup.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cliente implements Parcelable {
    private int id;
    private String nome, telefone, endereco, email;

    public Cliente() {}

    public Cliente(int id, String nome, String telefone, String endereco, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.id = in.readInt();
        this.nome = in.readString();
        this.telefone = in.readString();
        this.endereco = in.readString();
        this.email = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
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
            Cliente.id = in.readInt();
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
