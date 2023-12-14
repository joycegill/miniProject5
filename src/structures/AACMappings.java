package structures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/*
 * Keeps track of the complete set of AAC mappings
 *
 * @author Joyce Gill
 */

public class AACMappings 
{
  /* Fields */
  AACCategory current;
  AACCategory topLevel;
  AssociativeArray<String,AACCategory> arr;

  /* Constructor */
  public AACMappings (String filename) {
    arr = new AssociativeArray<String, AACCategory>();
    this.topLevel = new AACCategory (""); 
    this.current = topLevel;

    try {
      // Read in file
      Scanner sc = new Scanner(new File (filename));

      // While there are elements left in to read in the file
      while (sc.hasNextLine()) {
        // Space used to seperate imageLoc and text in file
        String[] strArr = sc.nextLine().split(" ", 2);
        String imageLoc = strArr[0];
        String text = strArr[1];

        // > indicates subcategory
        // < indicates topLevel
        if(!imageLoc.substring(0, 1).equals(">")){
          // Add item to topLevel 
          topLevel.addItem(imageLoc, text);
          arr.set(imageLoc, new AACCategory(text));
           current = arr.get(imageLoc); 
        } // if
        else {
          current.addItem(imageLoc.substring(1), text);
        } // else
			} // while

      // Reset & close
      current = topLevel;
      sc.close();

    } catch (Exception e) {
        System.err.println("Error");
    }
  } // AACMappings(String)

  /* Determines the associated text */
  public String getText(String imageLoc) {
    try {
      current = arr.get(imageLoc);
      return topLevel.getText(imageLoc);
    } catch (Exception e) {
      return "Error: Can't get category";
    }
  } // getText(String)

  /* Provides an array of all the images in the current category */
  public String[] getImageLocs() throws Exception {
    try {
      return this.current.getImages();
    } catch (Exception e) {
       return new String[] {"Error"};
    }
  } // getImagesLocs()

  /* Resets the current category of the AAC back to the default category */
  public void reset() {
    this.arr = new AssociativeArray<String, AACCategory>();
  } // reset()

  /* Gets the current category */
  public String getCurrentCategory() {
    return this.current.name;
  } //getCurrentCategory()

  /* Determines if the image represents a category of text to speak */
  public boolean isCategory (String imageLoc) {
    return this.arr.hasKey(imageLoc);
  } // isCategory(String)

  /* Write the AAC mappings stored to a file */
  public void writeToFile (String filename) {
    try {
      PrintWriter pen = new PrintWriter(new File(filename));
      
      // Category on home screen
      for(String category : topLevel.getImages()){
        if(category!= null){
          pen.println(">" + category + " " + topLevel.getText(category));

          // Image within a category
          for(String location : arr.get(category).getImages()){
            if(location != null){
              pen.println(location + " " + arr.get(category).getText(location));
            } 
          } 
        } 
      } 
      pen.close();
    } catch (Exception e) {
      System.err.println("Error: unable to write to " + filename);
    }
  } // writeToFile(String)

  /* Adds the mapping to the current category */
  public void add (String imageLoc, String text) {
    if (this.current.name.equals("")) {
      this.topLevel.addItem(imageLoc, text);
      this.arr.set(imageLoc, new AACCategory(text));
    } // if
    else {
      current.addItem(imageLoc, text);
    } // else
  } // add(String)
  
} // class AACMappings
