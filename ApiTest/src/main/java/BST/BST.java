package BST;

import java.math.BigDecimal;

public class BST {
    //korzeń naszego drzewa
    Node root = null;
    //wyjątki wyrzucane przez drzewo
    private class TreeException extends Throwable {
        TreeException() {}
        TreeException(String msg) {
            super(msg);
        }
    }
    //klasa węzeł - która jest używana jako struktura
    private class Node {
        BigDecimal key;
        Node left, right, parent = null;
        Node(BigDecimal key) {
            this.key = key;
        }
    }

    //dodawanie elementów
    public void insert(BigDecimal key) {
        if(root == null)
            root = new Node(key);
        else {
            Node actual = root;
            Node parent = null;
            while(actual != null) {
                parent = actual;
                actual = (actual.key.compareTo(key) == 1) ? actual.left : actual.right;
            }
            if(parent.key.compareTo(key) == 1) {
                parent.left = new Node(key);
                parent.left.parent = parent;
            } else {
                parent.right = new Node(key);
                parent.right.parent = parent;
            }
        }
    }
 
    //wyszukiwanie elementu
    public Node search(BigDecimal key) throws TreeException {
        Node actual = root;
        while(actual != null && actual.key != key)
            actual = (actual.key.compareTo(key) == 1) ? actual.left : actual.right;
        if(actual == null)
            throw new TreeException("Not found key");
        return actual;
    }

    //znajdowanie minimalnego klucza
    private Node min(Node node) {
        while(node.left != null)
            node = node.left;
        return node;
    }

    //znajdowanie maksymalnego klucza
    private Node max(Node node) {
        while(node.right != null)
            node = node.right;
        return node;
    }
 
    //znajdowanie następnika
    private Node successor(BigDecimal key) throws TreeException {
        Node node = this.search(key);
    //szukanie następnika gdy węzeł ma prawego potomka
        if(node.right != null) {
            node = node.right;
            while(node.left != null)
                node = node.left;
            return node;
        }
    //szukanie następnika gdy węzeł nie ma prawego potomka
        else if(node.right == null && node != root && node != this.max(root)) {
            Node parent = node.parent;
            while(parent != root && parent.key.compareTo(node.key) == -1)
                parent = parent.parent;
            return parent;
        }
        else
            throw new TreeException("Not found successor");
    }
 
    //znajdowanie poprzednika
    private Node predecessor(BigDecimal key) throws TreeException {
        Node node = this.search(key);
    //szukanie poprzednika gdy węzeł ma lewego potomka
        if(node.left != null) {
            node = node.left;
            while(node.right != null)
                node = node.right;
            return node;
        }
    //szukanie poprzednika gdy węzeł nie ma lewego potomka
        else if(node.left == null && node != root  && node != this.min(root)) {
            Node parent = node.parent;
            while(parent != root && parent.key.compareTo(node.key) == 1)
                parent = parent.parent;
            return parent;
        }
        else
            throw new TreeException("Not found predecessor");
    }

    //inOrder (przeszukiwanie poprzeczne) lewe poddrzewo, korzeń, prawe poddrzewo
    public void inOrder(Node node) {
        if(node != null) {
            inOrder(node.left);
            System.out.print(node.key + ", ");
            inOrder(node.right);
        }
    }

    //preOrder (przeszukiwanie wzdłużne) korzeń, lewe poddrzewo, prawe poddrzewo
    public void preOrder(Node node) {
        if(node != null) {
            System.out.print(node.key + ", ");
            if(node.left != null)
                System.out.print(node.left.key + ", ");
            else System.out.print("-, ");

            if(node.right != null)
                System.out.println(node.right.key);
            else System.out.println("-");

            preOrder(node.left);
            preOrder(node.right);
        }
    }

    //postOrder (przeszukiwanie poprzeczne) lewe poddrzewo, prawe poddrzewo, korzeń
    public void postOrder(Node node) {
        if(node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.key + ", ");
            if(node.left != null)
                System.out.print(node.left.key + ", ");
            else System.out.print("-, ");

            if(node.right != null)
                System.out.println(node.right.key);
            else System.out.println("-");
        }
    }

}