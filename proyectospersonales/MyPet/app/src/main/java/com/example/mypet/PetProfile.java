package com.example.mypet;

import android.net.Uri;

import java.io.Serializable;

public class PetProfile implements Serializable {
    private String name;
    private int idPet;
    private Uri uriPet;

    public PetProfile(String name, int idPet, Uri uriPet) {
        this.name = name;
        this.idPet = idPet;
        this.uriPet = uriPet;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public Uri getUriPet() {
        return uriPet;
    }

    public void setUriPet(Uri uriPet) {
        this.uriPet = uriPet;
    }

}
