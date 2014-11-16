package org.y3.riptools.util;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/** 
 * <p>Title: org.y3.riptools.util - MaxSizeDocument</p>
 * <p>Description: Document with maximum size for input value</p>
 * <p>Copyright: 2014</p>
* <p>Organisation: IT-Happens.de</p>
  * @author Christian.Rybotycky
 * @version $Id$
*/
public class MaxSizeDocument extends PlainDocument {
   int maxSize;
 
   public MaxSizeDocument(int maxSize) {
      this.maxSize = maxSize;
   }
 
   @Override
   public void insertString (final int offset, final String text,
                             final AttributeSet attributeSet) throws BadLocationException {
      if (isNewLengthOk(text))
         super.insertString (offset, text, attributeSet);
      else
         Toolkit.getDefaultToolkit().beep();
   }
 
   protected boolean isNewLengthOk(final String text) {
      if (getLength() + text.length() <= maxSize)
         return true;
      return false;
   }
}
