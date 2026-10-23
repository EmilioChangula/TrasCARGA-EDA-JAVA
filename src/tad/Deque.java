/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tad;

/**
 *
 * @author user
 */
public interface Deque<T> extends Iterable<T> {
    void addFirst(T elemento);
    void addLast(T elemento);
    T removeFirst();
    T removeLast();
    T peekFirst();
    T peekLast();
    T get(int indice);
    int size();
    boolean isEmpty();
    void clear();
    void duplicar();
    String toString();
}
