package org.mps.tree;

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

import java.util.Comparator;

public class BinarySearchTree<T> implements BinarySearchTreeStructure<T> {
    private Comparator<T> comparator;
    private T value;
    private BinarySearchTree<T> left;
    private BinarySearchTree<T> right;
    
    public String render(){
        String render = "";

        if (value != null) {
            render += value.toString();
        }

        if (left != null || right != null) {
            render += "(";
            if (left != null) {
                render += left.render();
            }
            render += ",";
            if (right != null) {
                render += right.render();
            }
            render += ")";
        }

        return render;
    }

    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.value = null;
        this.left = null;
        this.right = null;
    }

    @Override
    public void insert(T value) {
        if(this.value == null) {
            this.value = value;
        } else if(comparator.compare(this.value, value) == 0) {
            throw new BinarySearchTreeException("El elemento ya existe");
        } else if(comparator.compare(this.value, value) > 0) {
            if(left == null) {
                left = new BinarySearchTree<>(this.comparator);
                left.insert(value);
            } else {
                left.insert(value);
            }
        } else if(comparator.compare(this.value, value) < 0) {
            if(right == null) {
                right = new BinarySearchTree<>(this.comparator);
                right.insert(value);
            } else {
                right.insert(value);
            }
        }
    }

    @Override
    public boolean isLeaf() {
        boolean resultado = false;

        if(value == null) {
            throw new BinarySearchTreeException("El árbol binario está vacío");
        } else if(this.left == null && this.right == null) {
            resultado = true;
        }

        return resultado;
    }

    @Override
    public boolean contains(T value) {
        boolean resultado = false;

        if(this.value == null) {
            throw new BinarySearchTreeException("El árbol binario está vacío");
        }
        
        if(comparator.compare(this.value, value) == 0) {
            resultado = true;
        } else if(comparator.compare(this.value, value) > 0 && this.left != null) {
            resultado = this.left.contains(value);
        } else if(comparator.compare(this.value, value) < 0 && this.right != null) {
            resultado = this.right.contains(value);
        }

        return resultado;
    }

    @Override
    public T minimum() {
        if(this.value == null) {
            throw new BinarySearchTreeException("El árbol binario está vacío");
        }
        
        T minimo = this.value;
        
        if(this.left != null) {
            minimo = this.left.minimum();
        }

        return minimo;
    }

    @Override
    public T maximum() {
        if(this.value == null) {
            throw new BinarySearchTreeException("El árbol binario está vacío");
        }
        
        T maximo = this.value;
        
        if(this.right != null) {
            maximo = this.right.maximum();
        }

        return maximo;
    }

    @Override
    public void removeBranch(T value){
        if(!contains(value)) {
            throw new BinarySearchTreeException("El árbol no contiene el elemento");
        }

        if(this.comparator.compare(this.value, value) == 0) {
            this.comparator = null;
            this.value = null;
            this.right = null;
            this.left = null;
        } else if(comparator.compare(this.value, value) < 0) {
            this.right.removeBranch(value);
        } else {
            this.left.removeBranch(value);
        }
    }

    @Override
    public int size() {
        int tam = 0;

        if(this.value != null) {
            tam = 1;

            if(this.right != null) {
                tam += this.right.size();
            }
            
            if(this.left != null) {
                tam += this.left.size();
            }
        }

        return tam;
    }

    @Override
    public int depth() {
        int profundidad = 0;

        if(this.value != null) {
            int profundidadIzquierda = (this.left != null) ? this.left.depth() : 0;
            int profundidadDerecha = (this.right != null) ? this.right.depth() : 0;
            
            profundidad = 1 + Math.max(profundidadIzquierda, profundidadDerecha);
        }

        return profundidad;
    }

    // Complex operations
    // (Estas operaciones se incluirán más adelante para ser realizadas en la segunda
    // sesión de laboratorio de esta práctica)
}
