/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tad;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author user
 */
public class DequeArray<T> implements Deque<T>, Serializable{
    private static final long serialVersionUID = 1L;

    private T[] elementos;
    private int inicio;
    private int tamanho;
    
    @SuppressWarnings("unchecked")
    public DequeArray() {
        this.elementos = (T[]) new Object[10];
        this.inicio = 0;
        this.tamanho = 0;
    }

    @Override
    public void addFirst(T elemento) {
        if (tamanho == elementos.length) duplicar();
        inicio = (inicio - 1 + elementos.length) % elementos.length;
        elementos[inicio] = elemento;
        tamanho++;
    }

    @Override
    public void addLast(T elemento) {
       if (tamanho == elementos.length) duplicar();
        elementos[(inicio + tamanho) % elementos.length] = elemento;
        tamanho++;
    }

    @Override
    public T removeFirst() {
       if (isEmpty()) return null;
        T x = elementos[inicio];
        elementos[inicio] = null;
        inicio = (inicio + 1) % elementos.length;
        tamanho--;
        return x;
    }

    @Override
    public T removeLast() {
       if (isEmpty()) return null;
        int pos = (inicio + tamanho - 1) % elementos.length;
        T x = elementos[pos];
        elementos[pos] = null;
        tamanho--;
        return x;
    }

    @Override
    public T peekFirst() {
         return isEmpty() ? null : elementos[inicio];
    }

    @Override
    public T peekLast() {
         return isEmpty() ? null : elementos[(inicio + tamanho - 1) % elementos.length];
    }

    @Override
    public T get(int indice) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Índice inválido: " + indice);
        }
        return elementos[(inicio + indice) % elementos.length];
    }

    @Override
    public int size() {
       return tamanho;
      } 

    @Override
    public boolean isEmpty() {
       return tamanho == 0;
    }

    @Override
    public void clear() {
         for (int i = 0; i < elementos.length; i++) elementos[i] = null;
        inicio = 0;
        tamanho = 0;
    }

    @Override
    public void duplicar() {
        T[] novo = (T[]) new Object[elementos.length * 2];
        for (int i = 0; i < tamanho; i++) {
            novo[i] = elementos[(inicio + i) % elementos.length];
        }
        elementos = novo;
        inicio = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < tamanho;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                return get(i++);
            }
        };
    }
 @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < tamanho; i++) {
            sb.append(get(i));
            if (i < tamanho - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }    
    
}
