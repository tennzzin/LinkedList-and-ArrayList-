// Written by Tenzin Chonyi, chony003
// Written by Tenzin Gendun, gendu002

public class ArrayList<T extends Comparable<T>> implements List<T> {

    private T[] arr;
    private boolean isSorted;
    private int size = 0;

    public ArrayList() {
        isSorted = true;
        arr = (T[]) new Comparable[2];
    }

    @Override
    public boolean add(T element) {
        // Check if element null
        if (element == null) {
            return false;
        }
        // Check if list is full
        if (size == arr.length) {
            doubleList();
        }
        // Add to list
        arr[size] = element;
        size++;

        // Check if list sorted
        isSorted = checkSorted(); // checkSorted helper function
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        // Check if element null and if index in bounds
        if (index < 0 || index > size || element == null) {
            return false;
        }
        // Check if list full
        if (size == arr.length) {
            doubleList();
        }
        // Shift elements to the right
        for (int i = size; i > index; i--) {
            arr[i] = arr[i - 1];

        }
        // Add to list
        arr[index] = element;
        size++;

        // Check if list sorted
        isSorted = checkSorted();
        return true;
    }

    @Override
    public void clear() {
        arr = (T[]) new Comparable[2];
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
        for (int i = 0; i < size; i++) {
            if (arr[i] == arr[index]) {
                return arr[i];
            }
        }
        return null;
    }

    @Override
    public int indexOf(T element) {
        // Check if element null
        if (element == null) {
            return -1;
        }
        // Find index of element
        for (int i = 0; i < size; i++) {
            if (arr[i] == element) {
                return i;
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
        // Insertion sort
        for (int i = 1; i < size; i++) {
            T key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
        isSorted = true;
    }

    @Override
    public T remove(int index) {
        // Check if index in bounds
        if (index < 0 || index > size) {
            return null;
        }
        T data = arr[index];
        // Shift elements to the left
        for (int i = index; i < size - 1; i++) {
            arr[i] = arr[i + 1];
        }
        // Check if list sorted
        size--;
        isSorted = checkSorted();
        return data;
    }

    @Override
    public void equalTo(T element) {
        // Check if element null
        if (element == null) {
            return;
        }
        // Loop through list
        int resultIdx = 0; // Tracks size of list
        for (int i = 0; i < size; i++) {
            T current = arr[i];
            // Check if the element is equal
            if (current.equals(element)) {
                arr[resultIdx] = current;
                resultIdx++;
            }
        }
        // Remove extra elements from list
        while (size > resultIdx) {
            size--;
        }
        // Check if list sorted
        isSorted = checkSorted();
     }

    @Override
    public void reverse() {;
        // Start from the middle
        int mid = size / 2;
        for (int i = 0; i < mid; i++) {
            T temp = arr[i]; // Store data in temp variable
            arr[i] = arr[size - 1 - i];
            arr[size - 1 - i] = temp;
        }
        // Check if list sorted
        isSorted = checkSorted();
    }

    @Override
    public void intersect(List<T> otherList) {
        // Check if other list is empty
        if (otherList == null) {
            return;
        }
        ArrayList<T> other = (ArrayList<T>) otherList;
        this.sort();
        // Intersect the two linked lists
        int curr = 0;
        while (curr < this.size) { // Loop through current list
            boolean match = false;
            int otherCurr = 0;
            // Find matching elements
            while (otherCurr < other.size && !match) { // Loop through other list
                if (arr[curr].equals(other.arr[otherCurr])) {
                    match = true;
                }
                otherCurr++;
            }
            // If no match found, remove element from current list
            if (!match) {
                remove(curr);
            // If found, move to next element in current list
            } else {
                curr++;
            }
        }
        isSorted = true; // List is sorted from this.sort()
    }

    @Override
    public T getMin() {
        // Set data to first element
        T data = arr[0];
        // Loop to find minimum element
        for (int i = 1; i < size; i++) {
            if (arr[i] != null && arr[i].compareTo(data) < 0) {
                data = arr[i];
            }
        }
        return data;
    }

    @Override
    public T getMax() {
        // Set data to first element
        T data = arr[0];
        // Loop to find max element
        for (int i = 1; i < size; i++) {
            if (arr[i] != null && arr[i].compareTo(data) > 0) {
                data = arr[i];
            }
        }
        return data;
    }

    @Override
    public boolean isSorted() {
        return isSorted;
    }

// Helper method to double list size
    public void doubleList() {
        int newSize = size * 2;
        T[] newArr = (T[]) new Comparable[newSize];
        for (int i = 0; i < size; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

// Helper method to check if list sorted ascending
    public boolean checkSorted() {
        for (int i = 1; i < size; i++) {
            if (arr[i - 1].compareTo(arr[i]) > 0) {
                return false;
            }
        }
        return true;
    }
}


