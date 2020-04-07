package com.ibao.premezcla.models;

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
    private int currentModule;
    private String modulesString;
    private List<Integer> modulesList;

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
        currentModule =0;
        modulesString ="";
        modulesList= new ArrayList<>();
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

    public int getCurrentModule() {
        return currentModule;
    }

    public void setCurrentModule(int currentModule) {
        this.currentModule = currentModule;
    }

    public String getModulesString() {
        return modulesString;
    }

    public void setModulesString(String modulesString) {
        this.modulesString = modulesString;
        setModulesList(modulesString);
    }

    public List<Integer> getModulesList() {
        return modulesList;
    }

    public void setModulesList(List<Integer> modulesList) {
        this.modulesList = modulesList;
    }

    private void setModulesList(String modulos) {
        String[] ids = modulos.split(",");
        this.modulesList= new ArrayList<>();
        for(String id :ids){
            modulesList.add(Integer.valueOf(id));
        }
    }

}
