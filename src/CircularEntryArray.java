
/**
 * CircularEntryArray class represents a circular array data structure with key-value pairs.
 * It extends the CleverSIDC class and provides methods for managing, sorting, and retrieving data in the array.
 * @author Rania Maoukout(40249281) & Barbara Eguche(40245327)
 */
public class CircularEntryArray extends CleverSIDC {
    private int rearOfArray = 0, sizeOfArray; private long[] keys; private String[] values;

    /**
     * Parameterized constructor.
     */
    public CircularEntryArray() {
        //set initial capacity for both key and values
        keys = new long[10];
        values = new String[10];
        sizeOfArray = 0;
    }

    /**
     * This method returns the number of elements in the array.
     * @return the size of the array
     */
    public int getArraySize() {
        return this.sizeOfArray;
    }

    /**
     * This method resizes the circular array by doubling its capacity, and copying existing elements to the new array.
     */
    private void resizeArray() {
        long[] newKeys = new long[sizeOfArray * 2];
        String[] newValues = new String[sizeOfArray * 2];
        for(int i = 0; i < keys.length; i++) {
            newKeys[i] = keys[i];
            newValues[i] = values[i];
        }
        keys = newKeys;
        values = newValues;
    }

    /**
     * This method retrieves the key at the specified index in the circular array.
     * @param index the index of the key to retrieve
     * @return the key at the specified index.
     * @throws IndexOutOfBoundsException if the provided index is out of bounds.
     */
    @Override
    public long getKeyAtIndex(int index) {
        try {
            if (index < 0 || index >= sizeOfArray) throw new IndexOutOfBoundsException("The provided index is out of bounds, therefore, the key doesn't exist.");
        } catch (IndexOutOfBoundsException exception) {
            System.out.println(exception.getMessage());
            return 0;
        }
        return keys[index];
    }

    /**
     * This method finds the index of the specified key in the circular array.
     * @param key the key to search for in the array
     * @return the index of the key if found, or -1 if the key is not present in the array.
     */
    @Override
    public int findKeyInArray(long key) {
       for(int i = 0; i < sizeOfArray; i++) {
           if(keys[i] == key) return i;
       }
       return -1;
    }

    /**
     * This method retrieves the value associated with the specified key in the circular array.
     * @param key the key for which to retrieve the associated value
     * @return the value associated with the key. If the key does not exist, returns a default message.
     */
    @Override
    public String getValueInArray(long key) {
        int keyValue = findKeyInArray(key);
        if(keyValue == -1) return "";
        return values[keyValue];
    }

    /**
     * This method applies the merge sort algorithm to sort a subarray of the circular array.
     * @param left the starting index of the subarray to be sorted
     * @param right the ending index of the subarray to be sorted
     */
    @Override
    public void mergeSortCircularArray(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            //recursively sort each half
            mergeSortCircularArray(left, mid);
            mergeSortCircularArray(mid + 1, right);

            //merge the sorted halves
            mergeCircularArray(left, mid, right);
        }
    }
    /**
     * This method merges two sub-arrays in the circular array during a merge sort operation.
     * @param left the starting index of the left sub-array
     * @param mid the ending index of the left sub-array, and the starting index of the right sub-array
     * @param right the ending index of the right sub-array
     */
    private void mergeCircularArray(int left, int mid, int right) {
        int sizeLeft = mid - left + 1;
        int sizeRight = right - mid;

        long[] leftKeys = new long[sizeLeft], rightKeys = new long[sizeRight];
        String[] leftValues = new String[sizeLeft], rightValues = new String[sizeRight];

        //copy data to temporary arrays
        for (int i = 0; i < sizeLeft; ++i) {
            leftKeys[i] = keys[(left + i) % sizeOfArray];
            leftValues[i] = values[(left + i) % sizeOfArray];
        }
        for (int j = 0; j < sizeRight; ++j) {
            rightKeys[j] = keys[(mid + 1 + j) % sizeOfArray];
            rightValues[j] = values[(mid + 1 + j) % sizeOfArray];
        }

        //merge the temporary arrays back into the original circular array
        int i = 0, j = 0, k = left;
        while (i < sizeLeft && j < sizeRight) {
            if (leftKeys[i] <= rightKeys[j]) {
                keys[k % sizeOfArray] = leftKeys[i];
                values[k % sizeOfArray] = leftValues[i];
                i++;
            } else {
                keys[k % sizeOfArray] = rightKeys[j];
                values[k % sizeOfArray] = rightValues[j];
                j++;
            }
            k++;
        }

        //copy the remaining elements of leftKeys[], leftValues[], if there are any
        while (i < sizeLeft) {
            keys[k % sizeOfArray] = leftKeys[i];
            values[k % sizeOfArray] = leftValues[i];
            i++;
            k++;
        }
        //copy the remaining elements of rightKeys[], rightValues[], if there are any
        while (j < sizeRight) {
            keys[k % sizeOfArray] = rightKeys[j];
            values[k % sizeOfArray] = rightValues[j];
            j++;
            k++;
        }
    }

    /**
     * This method prints the key-value pairs in the circular array.
     */
    @Override
    public void printCircularArray() {
        for(int i = 0; i < sizeOfArray; i++) {
            System.out.printf("%d -- %s%n", keys[i], values[i]);
        }
    }

    /**
     * This method adds a key-value pair to the array if the key is not already present.
     * @param keyToAdd the new key to add
     * @param valueToAdd the value associated with the key
     */
    @Override
    public void addKeyToArray(long keyToAdd, String valueToAdd) {
        if(findKeyInArray(keyToAdd) == -1) {
            if(sizeOfArray == keys.length) resizeArray();
            keys[sizeOfArray] = keyToAdd;
            values[sizeOfArray] = valueToAdd;
            sizeOfArray++;
            rearOfArray++;
        }
    }

    /**
     * This method removes the specified key from the array and returns the removed key.
     * @param keyToRemove the key to be removed from the array
     * @return the removed key, or -1 if the key is not found in the array.
     */
    @Override
    public long removeKeyFromArray(long keyToRemove) {
        long removedKey = -1; int removedKeyIndex = -1;

        for(int i = 0; i < sizeOfArray; i++) {
            if(keys[i] == keyToRemove) {
                removedKey = keys[i];
                removedKeyIndex = i;
                break;
            }
        }
        if(!(removedKeyIndex == -1)) {
            for(int j = removedKeyIndex; j < sizeOfArray - 1; j++) {
                keys[j] = keys[j + 1];
                values[j] = values[j + 1];
            }
            sizeOfArray--;
            rearOfArray--;
        }
        return removedKey;
    }

    /**
     * This method finds the range of indices in the array for keys falling within the specified range (key1, key2).
     * @param key1 the lower bound of the key range
     * @param key2 the upper bound of the key range
     * @return the number of elements in the array with keys in the specified range.
     * Returns -1 if one of the keys does not exist in the array, or 0 if the index of key1 is after that of key2.
     */
    @Override
    public int rangeOfKeyInArray(long key1, long key2) {
        int startRange = findKeyInArray(key1);
        int endRange = findKeyInArray(key2);

        if(startRange == -1 || endRange == -1) {
            System.out.println("One of the keys do not exist.");
            return -1;
        }
        if(startRange > endRange) {
            System.out.println("Index of key1 is after that of key2.");
            return -1;
        }
        return ((endRange - startRange) - 1);
    }
}
