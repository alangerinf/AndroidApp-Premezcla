package com.ibao.premescla.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private int id;
    private String user;
    private String name;
    private String password;
    private String modulos;
   private List<Integer> idModulos;

    public String toString(){
        Gson gson = new Gson();

        return gson.toJson(
                this,
                new TypeToken<User>() {}.getType());
    }
    public User() {

        user="";
        name="";
        password="";
        modulos="";
        idModulos= new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getModulos() {
        return modulos;
    }

    public void setModulos(String modulos) {
        this.modulos = modulos;
    }

    public List<Integer> getIdModulos() {
        return idModulos;
    }

    private void setIdModulos(String modulos) {
        String[] ids = modulos.split(",");
        this.idModulos= new ArrayList<>();
        for(String id :ids){
            idModulos.add(Integer.valueOf(id));
        }
    }
}
