/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo5.beans;

import com.demo5.dao.RacionDAO;
import com.demo5.models.ComedorAlumno;
import com.demo5.utils.ClientJavaRest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author raul
 */
@ManagedBean(name = "racionBeans")
@SessionScoped
public class RacionBeans {
       
    private int dniAlumno;
    private int codigoAlumno;
    private String nombresAlumno;
    private String apellidosAlumno;
    private String facultad;
    private String escuela;
    private int piso;
    private int turno;
    private String response;
    ClientJavaRest clientJavaRest;
    private String msjdataalumno;
     
    public RacionBeans(){
        clientJavaRest = new ClientJavaRest();
        msjdataalumno="";
    }
    
    
    public void addRacion(){
        ComedorAlumno comedorAlumno = new ComedorAlumno(dniAlumno, codigoAlumno, nombresAlumno, apellidosAlumno, facultad, escuela, piso, turno);
        RacionDAO racionDAO = new RacionDAO();
        racionDAO.addRacion(comedorAlumno);
        limpiarCampos();
    }
    
    public void limpiarCampos(){
        setDniAlumno(-1);
        setCodigoAlumno(-1);
        setNombresAlumno("");
        setApellidosAlumno("");
        setFacultad("");
        setEscuela("");
        setPiso(-1);
        setTurno(-1);
    }
    
    public void cargarAlumno() throws IOException, TimeoutException {

        //ClienteRPC clienteRPC =  new ClienteRPC();
        //response = clienteRPC.consumirApi("1-" + codigoAlumno); 
        response = clientJavaRest.consumirAPI(codigoAlumno);
        System.out.println(response);
        
        String[] parts = response.split(" ");
        String part1 = parts[0];
        
        if (part1.equals("[AttributeError")) {
            msjdataalumno = "Alumno no encontrado";
            limpiarCampos();
            
        } else {
            msjdataalumno = "";
            JsonParser parser = new JsonParser();

            JsonArray gsonArr = parser.parse(response).getAsJsonArray();

            // for each element of array
            for (JsonElement obj : gsonArr) {

                JsonObject gsonObj = obj.getAsJsonObject();
                    dniAlumno = gsonObj.get("dnialumno").getAsInt();
                    
                    nombresAlumno = gsonObj.get("nombresalumno").getAsString();
                    apellidosAlumno = gsonObj.get("apellidosalumno").getAsString();
                    facultad = gsonObj.get("facultad").getAsString();
                    escuela = gsonObj.get("escuela").getAsString();

            }

        }

    }
    
    
    public int getDniAlumno() {
        return dniAlumno;
    }

    public void setDniAlumno(int dniAlumno) {
        this.dniAlumno = dniAlumno;
    }

    public int getCodigoAlumno() {
        return codigoAlumno;
    }

    public void setCodigoAlumno(int codigoAlumno) {
        this.codigoAlumno = codigoAlumno;
    }

    public String getNombresAlumno() {
        return nombresAlumno;
    }

    public void setNombresAlumno(String nombresAlumno) {
        this.nombresAlumno = nombresAlumno;
    }

    public String getApellidosAlumno() {
        return apellidosAlumno;
    }

    public void setApellidosAlumno(String apellidosAlumno) {
        this.apellidosAlumno = apellidosAlumno;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ClientJavaRest getClientJavaRest() {
        return clientJavaRest;
    }

    public void setClientJavaRest(ClientJavaRest clientJavaRest) {
        this.clientJavaRest = clientJavaRest;
    }
     
    
    
     
    
    
    
    
}
