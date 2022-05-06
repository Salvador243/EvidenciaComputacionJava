/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computacionJava.evidencia;

import com.computacionJava.Medicos;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import net.minidev.json.JSONArray;
import net.minidev.json.parser.JSONParser;

import java.io.*;
import java.util.*;

/**
 * @author jajimenez
 */
public class ConsultorioMain {

    public static List<Usuario> usuarios;
    public static List<Cita> citas = new ArrayList();
    public static List<Medicos> medicos = new ArrayList<Medicos>();
    public static List<Paciente> pacientes = new ArrayList<Paciente>();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        boolean existeUsuario;
        String usuario = "";
        String contrasena = "";
        Scanner credenciales = new Scanner(System.in);
        System.out.println("Cargando sistema... ");
        cargarUsuarios();
        System.out.println("-------------------------Inicio de sesion-----------------------");
        System.out.println("Usuario:");
        usuario = credenciales.nextLine();
        System.out.println("Contraseña");
        contrasena = credenciales.nextLine();
        existeUsuario = validarCredenciales(usuario, contrasena);
        if (existeUsuario) {
            System.out.println("existe el usuario");
            cargarCita();
            cargarPacientes();
            menu();

        } else {
            System.out.println("el usuario no existe");
        }
    }
        
    public static void menu() {
        Integer opcion = -1;
        while (opcion != 0) {

            Scanner opcionScanner = new Scanner(System.in);
            System.out.println("1.-Dar de alta a medico\n"
                    + "2.-Dar de alta a un paciente\n"
                    + "4.Ver medicos y pacientes en la lista\n"
                    + "5.-Ver las citas por nombre del medico\n"
                    + "7.-Crear cita\n"
                    + "8.-ver todas las citas\n"
                    + "9.-Guardar\n"
                    + "0.-Salir");
            System.out.println("Opción:");
            opcion = opcionScanner.nextInt();

            switch (opcion) {
                case 1:
                    crearMedico();
                    break;
                case 2:
                    crearPaciente();
                    break;
                case 4:
                    verMedicos();
                    verPacientes();
                    break;
                case 7:
                    crearCita();
                    break;
                case 8:
                    imprimirTodasCitas();
                    break;
                case 9:
                    save();
                    guardarMedicos();
                    guardarPacientes();
                    break;
            }
        }

    }
    
    public static boolean validarCredenciales(String usuario, String contrasena) {
        return usuarios.stream().anyMatch(x -> x.getNombre().equals(usuario) && x.getContrasena().equals(contrasena));
    }

    public static void cargarUsuarios() {

        if (usuarios == null) {
            usuarios = new ArrayList<>();
        }

        usuarios.add(new Usuario(1, "carlos", "1234"));
        usuarios.add(new Usuario(2, "sofia", "1234"));
        usuarios.add(new Usuario(2, "ithan", "0000"));
        usuarios.add(new Usuario(2, "alfredo", "0000"));
        System.out.println("Los usuarios han sido cargados: " + usuarios.size());

    }
    
    // incio Medicos
    public static void verMedicos() {
        for (Medicos cita : medicos) {
            System.out.println("---------------------------------------------------");
            System.out.println("Id medico:" + cita.getId());
            System.out.println("Nombre Medico:" + cita.getMedico().getNombre());
            System.out.println("Especialidad Medico:" + cita.getMedico().getEspecialida());
        }
    }

    public static void crearMedico() {
        try {
            Scanner leer = new Scanner(System.in);
            System.out.println("Escriba el id del Medico");
            int id = Integer.parseInt(leer.nextLine());
            System.out.println("Escriba el nombre del Medico");
            String nombre = leer.nextLine();
            System.out.println("Escriba la especialidad del Medico");
            String especialidad = leer.nextLine();

            Medico doc = new Medico();
            doc.setId(id);
            doc.setEspecialida(especialidad);
            doc.setNombre(nombre);
            Medicos medico = new Medicos();
            medico.setId(id);
            medico.setMedico(doc);
            medicos.add(medico);

        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
    }

    public static void guardarMedicos() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(medicos);
            String ruta = "src/archivos/medicos.json";

            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(json);
            bw.close();
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }

        /* Guardar variable */
    }

    public static String leerMedico() throws IOException {
        String archivo = "src/archivos/medicos.json";
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        StringBuilder json = new StringBuilder();
        String cadena;
        while ((cadena = b.readLine()) != null) {
            json.append(cadena);
        }
        b.close();

        return json.toString();
    }
    //fin medicos

    //inicio pacientes
    public static void verPacientes() {
        for (Paciente paciente : pacientes) {
            System.out.println("---------------------------------------------------");
            System.out.println("Id paciente:" + paciente.getId());
            System.out.println("Nombre paciente:" + paciente.getNombre());
        }
    }

    public static void crearPaciente(){
        try {
            Scanner leer = new Scanner(System.in);
            System.out.println("Escriba el id del Paciente");
            int id = Integer.parseInt(leer.nextLine());
            System.out.println("Escriba el nombre del Paciente");
            String nombre = leer.nextLine();

            Paciente pac = new Paciente();
            pac.setId(id);
            pac.setNombre(nombre);
            
            pacientes.add(pac);

        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
    }

    public static void guardarPacientes() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(pacientes);
            String ruta = "src/archivos/pacientes.json";

            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(json);
            bw.close();
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }

        /* Guardar variable */
    }

    public static String leerPaciente() throws IOException {
        String archivo = "src/archivos/pacientes.json";
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        StringBuilder json = new StringBuilder();
        String cadena;
        while ((cadena = b.readLine()) != null) {
            json.append(cadena);
        }
        b.close();

        return json.toString();
    }
    
    public static void cargarPacientes() throws IOException {
        String json = leerPaciente();
        String jsonMedicos = leerMedico();
        Gson gson = new Gson();
        Paciente[] pc = gson.fromJson(json, Paciente[].class);
        for (Paciente temp : pc) {
            pacientes.add(temp);
        }
        Medicos[] medico = gson.fromJson(jsonMedicos, Medicos[].class);
        for (Medicos temp : medico) {
            medicos.add(temp);
        }
    }
    //fin pacientes
    
    //inicio citas
    public static void save() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(citas);
            System.out.println(json);
            String ruta = "citas.json";

            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(json);
            bw.close();
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }

        /* Guardar variable */
    }

    public static void crearCita() {
        verMedicos();
        verPacientes();
        Scanner leer = new Scanner(System.in);
        Cita cita = new Cita();
        Medico medico = new Medico();
        Paciente paciente = new Paciente();

        try{
            System.out.println("Dijite el id del medico que desea meter a la lista");
            int idmedico =Integer.parseInt(leer.nextLine());

            System.out.println("Dijite el id del paciente que desea meter a la lista");
            int idpaciente =Integer.parseInt(leer.nextLine());

            for (Medicos md : medicos) {
                if(idmedico == md.getId()){
                    medico.setId(md.getId());
                    medico.setNombre(md.getMedico().getNombre());
                    medico.setEspecialida(md.getMedico().getEspecialida());                    
                }
            }

            for (Paciente pc : pacientes) {
                if(idpaciente == pc.getId()){
                    paciente.setId(pc.getId());
                    paciente.setNombre(pc.getNombre());
                }
            }

            System.out.println("Dijite el id de la cita que desea meter a la lista");
            int idcita =Integer.parseInt(leer.nextLine());

            cita.setId(idcita);

            System.out.println("Dijite el motivo de la cita");
            String motivo = leer.nextLine();

            System.out.println("Dijite la fecha de la cita que desea meter a la lista");
            String fecha = leer.nextLine();
            cita.setFecha(fecha);
            cita.setNombreCita(motivo);
            cita.setMedico(medico);
            cita.setPaciente(paciente);
        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
        citas.add(cita);
    }

    public static void cargarCita() throws IOException {
        String json = leerArchivo();
        String jsonMedicos = leerMedico();
        Gson gson = new Gson();
        Cita[] cita = gson.fromJson(json, Cita[].class);
        for (Cita temp : cita) {
            citas.add(temp);
        }
        Medicos[] medico = gson.fromJson(jsonMedicos, Medicos[].class);
        for (Medicos temp : medico) {
            medicos.add(temp);
        }
    }

    public static void imprimirTodasCitas() {
        for (Cita cita : citas) {
            System.out.println("---------------------------------------------------");
            System.out.println("Nombre cita:" + cita.getNombreCita());
            System.out.println("Nombre paciente:" + cita.getPaciente().getNombre());
            System.out.println("Nombre medico:" + cita.getMedico().getNombre());
        }
    }

    public static String leerArchivo() throws IOException {
        String archivo = "citas.json";
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        StringBuilder json = new StringBuilder();
        String cadena;
        while ((cadena = b.readLine()) != null) {
            json.append(cadena);
        }
        b.close();
        return json.toString();
    }
    //fin citas
}
