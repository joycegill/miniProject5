package structures;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K and values of type V.
 * Associative Arrays store key/value pairs and permit you to look up values by key.
 *
 * @author Joyce Gill
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  public KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({"unchecked"})
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(), DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> clonedArr = new AssociativeArray<K, V>();
    for (int i = 0; i < this.size; i++) {
      clonedArr.set(pairs[i].key, pairs[i].value);
    } // for
    return clonedArr;
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    String str = "";
    // Check if the associative array is empty
    if (this.size == 0) {
      return "{}";
    }
    // Iterate through the key/value pairs & build a string
    for (int i = 0; i < this.size; i++) {
      str += pairs[i].key + ": " + pairs[i].value;
      if (i < this.size - 1) {
        str += ", ";
      } // if
      else {
        str += " ";
      } // else
    } // for

    return "{ " + str + "}";
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to get(key) will return value.
 * @throws KeyNotFoundException
   */
  public void set(K key, V value) {
    try {
      // Set key to index
      int idx = find(key);
      this.pairs[idx] = new KVPair<K, V>(key, value);
    } catch (Exception e) {
      // Expand & add if array is full
      if (this.pairs.length == size()) {
        this.expand();
        this.pairs[this.size] = new KVPair<K, V>(key, value);
        this.size++;
        return;
      } // if

      // Add a new key/value pair if the key doesn't exist
      this.pairs[this.size] = new KVPair<K, V>(key, value);
      this.size++;
    } // try/catch
  }  // set(K,V)


  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException when the key does not appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    if (!hasKey(key)) {
      throw new KeyNotFoundException();
    } // if
    return this.pairs[find(key)].value;
  } // get(K)

  /**
   * Determine if key appears in the associative array.
   */
  public boolean hasKey(K key) {
    try {
      find (key);
      return true;
    } catch (Exception e) {
      return false;
    } // try/catch
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls to get(key) will throw an
   * exception. If the key does not appear in the associative array, does nothing.
   */
  public void remove(K key) {
    try {
      // Remove pair at idx
      int idx = find(key);
      this.pairs[idx] = null;

      // Shift remaining elements to the left
      for (int i = idx + 1; i < this.size; i++) {
        this.pairs[i-1] = this.pairs[i];
        this.pairs[i] = null;
      } // for

      // Decrement size
      this.size--;

    } catch (Exception e) {
      return;
    } // try/catch
  } // remove(K)

  /**
   * Determine how many values are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()


  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  public void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length + DEFAULT_CAPACITY);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key. If no such entry is found,
   * throws an exception.
   */
  public int find(K key) throws KeyNotFoundException {
    for (int i = 0; i < this.size; i++) {
      // Check if the current entry contains the given key
      if (this.pairs[i].key != null ) {
        if (this.pairs[i].key.equals(key)) {
          return i;
        } // if
      } // if
      else {
        if(this.pairs[i].key == key) {
          return i;
        } // if
      } // else
    } // for 
    throw new KeyNotFoundException();
  } // find(K)

  /* 
   * Return array of keys
   */
  public K[] getKeys() {
    if (this.pairs.length == 0) {
      System.err.println("Error: Empty KVPairs");
      return null;
    } // if

      K[] keys = (K[]) newInstance((this.pairs[0].key).getClass(), this.pairs.length);
      // Traverse & add key
      for (int i = 0; i < this.size(); i++) {
        if (this.pairs[i].key != null) {
          keys[i] = this.pairs[i].key;
        } // if
      } // for
      return keys;
  } // getKeys()

} // class AssociativeArray