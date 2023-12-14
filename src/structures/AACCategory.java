package structures;

/*
 * Represent a single category of items in the AAC. 
 * Stores the mapping between the image location and the text
 *
 * @author Joyce Gill
 */

public class AACCategory 
{
  /* Fields */
  AssociativeArray<String,String> arr; 
  String name;

  /* Constructor */
  public AACCategory (String name) {
    this.name = name;
    this.arr = new AssociativeArray<String,String>();
  } // AACCategory(String)

  /* Method that adds the mapping of the imageLoc to the text to the category */
  public void addItem (String imageLoc, String text) {
    this.arr.set(imageLoc, text);
  } // addItem(String, String)
  
  /* Method that returns the name of the category */
  public String getCategory() {
    return this.name;
  } // getCategory()

  /* Method that returns the text associated with the given image loc in this category */
  public String getText(String imageLoc) throws KeyNotFoundException {
    try {
      // Get text 
      return this.arr.get(imageLoc);
    } catch (Exception e) {
      return "Error";
    }
  } // getText(String)

  /* Method that determines if the provided image is stored in the category */
  public boolean hasImage(String imageLoc) throws KeyNotFoundException {
    try {
      // Get image
      return this.arr.hasKey(imageLoc);
    } catch (Exception e) {
      return false;
    }
  } // hasImage(String)

  /* Method that returns an array of all the images in the category */
  public String[] getImages() throws Exception {
    if(this.arr.getKeys() == null){
      return new String[] {""};
    } // if
    try {
      // Get keys from this category
      return this.arr.getKeys();
    } catch (Exception e) {
      return new String[] {"Error getting keys"};
    } // try/catch
  } // getImages()

} // class AACCategory




