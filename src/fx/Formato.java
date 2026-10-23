/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fx;

import java.time.format.DateTimeFormatter;

public class Formato {

    public static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String mzn(double valor) {
        return String.format("%.2f MZN", valor);
    }

    private Formato() { }
}