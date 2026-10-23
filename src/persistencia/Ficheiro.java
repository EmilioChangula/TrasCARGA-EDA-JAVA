/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import tad.DequeArray;

/**
 *
 * @author user
 */
public class Ficheiro {
    public static <T> void guardar(String caminho, DequeArray<T> lista) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminho))) {
            out.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao guardar: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> DequeArray<T> carregar(String caminho) {
        File f = new File(caminho);
        if (!f.exists()) {
            return new DequeArray<>();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            return (DequeArray<T>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
            return new DequeArray<>();
        }
    }
    
}
