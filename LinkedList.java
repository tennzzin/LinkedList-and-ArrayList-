// Written by Tenzin Chonyi, chony003
// Written by Tenzin Gendun, gendu002

public class LinkedList<T extends Comparable<T>> implements List<T> {

    private Node<T> dummyHead;
    private boolean isSorted;
    private int size = 0;

    public LinkedList() {
        isSorted = true;
        dummyHead = new Node<T>(null, null);
    }

    @Override
    public boolean add(T element) {
        // Check if element null
        if (element == null) {
            return false;
        }
        // Add to empty or non-empty list
        Node<T> newNode = new Node<T>(element, null);
        Node<T> currNode = dummyHead;
        while (currNode.getNext() != null) {
            currNode = currNode.getNext();
        }
        currNode.setNext(newNode);

        // Check if list is sorted
        size++;
        isSorted = checkSorted(); // checkSorted helper function
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        // Check if element null, index in bounds
        if (element == null || index < 0 || index >= size) {
            return false;
        }
        // Insert to beginning of list
        Node<T> newNode = new Node<T>(element, null);
        if (index == 0) {
            newNode.setNext(dummyHead.getNext());
            dummyHead.setNext(newNode);

        // Insert at specific index
        } else {
            Node<T> currNode = dummyHead;
            int currIdx = 0;
            while (currNode.getNext() != null && currIdx < index) {
                currNode = currNode.getNext();
                currIdx++;
            }
            newNode.setNext(currNode.getNext());
            currNode.setNext(newNode);
        }
        // Check if list is sorted
        size++;
        isSorted = checkSorted();
        return true;
    }

    @Override
    public void clear() {
        dummyHead.setNext(null);
        isSorted = true;
        size = 0;
    }

    @Override
    public T get(int index) {
        // Check if index in bounds
        if (index < 0 || index >= size) {
            return null;
        }
        // Find element of given index
        Node<T> currNode = dummyHead.getNext();
        int currIdx = 0;
        while (currNode.getNext() != null && currIdx < index) {
            currNode = currNode.getNext();
            currIdx++;
        }
        return currNode.getData();
    }

    @Override
    public int indexOf(T element) {
        // Check if element null
        if (element == null) {
            return -1;
        }
        // Find index of element
        Node<T> currNode = dummyHead;
        int idx = -1;
        while (currNode.getNext() != null) {
            currNode = currNode.getNext();
            idx++;
            if (currNode.getData() != null && currNode.getData().equals(element)) {
                return idx;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void sort() {
        // Check if already sorted
        if (isSorted) {
            return;
        }
        // Sort the list
        for (Node<T> prev = dummyHead.getNext(); prev != null; prev = prev.getNext()) {
            for (Node<T> curr = prev.getNext(); curr != null; curr = curr.getNext()) {
                if (prev.getData().compareTo(curr.getData()) > 0) {
                    T temp = prev.getData();
                    prev.setData(curr.getData());
                    curr.setData(temp);
                }
            }
        }
        isSorted = true;
    }

    @Override
    public T remove(int index) {
        // Check if index in bounds
        if (index < 0 || index >= size) {
            return null;
        }
        // Remove node from beginning
        Node<T> currNode = dummyHead;
        int idx = 0; // Ends right before the node
        while (currNode.getNext() != null && idx < index) {
            currNode = currNode.getNext();
            idx++;
        }
        Node<T> temp = currNode.getNext();
        currNode.setNext(currNode.getNext().getNext());

        // Check if list sorted
        size--;
        isSorted = checkSorted();
        return temp.getData();
    }

    @Override
    public void equalTo(T element) {
        // Check if element is null
        if (element == null) {
            return;
        }
        // Remove elements not equal to element
        Node<T> ptr = dummyHead.getNext();
        Node<T> trailer = dummyHead;
        while (ptr != null) {
            if (!ptr.getData().equals(element)) {
                trailer.setNext(ptr.getNext()); // Remove node that isn't element
                ptr = ptr.getNext();
                size--;
            } else {
                trailer = ptr;
                ptr = ptr.getNext();
            }
        }
        // Check if list sorted
        isSorted = checkSorted();
    }

    @Override
    public void reverse() {
        Node<T> ptr = dummyHead.getNext();
        Node<T> trailer = dummyHead;
        while (ptr != null) {
            Node<T> next = ptr.getNext(); // Points to ptr's next
            ptr.setNext(trailer);
            trailer = ptr;
            ptr = next;
        }
        dummyHead.setNext(trailer);

        // Check if list sorted
        isSorted = !isSorted;
    }

    @Override
    public void intersect(List<T> otherList) {
        // Check if other list is empty
        if (otherList == null) {
            return;
        }
        LinkedList<T> other = (LinkedList<T>) otherList;
        this.sort();
        // Intersect the two linked lists
        Node<T> curr = dummyHead.getNext();
        Node<T> prev = dummyHead;
        while (curr != null) { // Loop through current list
            boolean match = false;
            Node<T> next = curr.getNext(); // Points to curr's next
            Node<T> otherListCurr = other.dummyHead;
            // Find matching elements
            while (otherListCurr != null && !match) { // Loop through other list
                if (curr.getData().equals(otherListCurr.getData())) {
                    match = true;
                }
                otherListCurr = otherListCurr.getNext();
            }
            // If no match found, remove node from current list
            if (!match) {
                prev.setNext(next);
                size--;
            // If found, point prev to next node in current list
            } else {
                prev = prev.getNext();
            }
            // Move to next node in current list
            curr = curr.getNext();
        }
        isSorted = true; // List is sorted from this.sort()
    }

    @Override
    public T getMin() {
        // Check if list empty
        if (size == 0) {
            return null;
        }
        T min = dummyHead.getNext().getData();
        Node<T> ptr = dummyHead;
        // Check if there is only one element
        if (ptr.getNext().getNext() == null) {
            return min;
        }
        // Find min
        ptr = ptr.getNext();
        while (ptr != null) {
            if (min.compareTo(ptr.getData()) > 0) {
                min = ptr.getData();
            }
            ptr = ptr.getNext();
        }
        return min;
    }

    @Override
    public T getMax() {
        // Check if list empty
        if (size == 0) {
            return null;
        }
        T max = dummyHead.getNext().getData();
        Node<T> ptr = dummyHead;
        // Check if there is only one element
        if (ptr.getNext().getNext() == null) {
            return max;
        }
        // Find max
        ptr = ptr.getNext();
        while (ptr != null) {
            if (max.compareTo(ptr.getData()) < 0) {
                max = ptr.getData();
            }
            ptr = ptr.getNext();
        }
        return max;
    }

    @Override
    public boolean isSorted() {
        return isSorted;
    }

// Helper method to check if list sorted ascending
    public boolean checkSorted() {
        if (dummyHead.getNext() == null) {
            return true;
        }
        Node<T> currNode = dummyHead.getNext();
        while (currNode.getNext() != null) {
            if (currNode.getData().compareTo(currNode.getNext().getData()) > 0) {
                return false;
            }
            currNode = currNode.getNext();
        }
        return true;
    }
}

