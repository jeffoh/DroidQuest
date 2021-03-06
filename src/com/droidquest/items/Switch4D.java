package com.droidquest.items;

import com.droidquest.materials.Material;
import com.droidquest.materials.Switch;

public class Switch4D extends Switch 
{
transient Switch4A sw = null;

public Switch4D() 
  {
	super(Switch.ROT_LEFT);
  }

public void TouchedByItem(Item item) 
  {
	if (sw==null)
	  {
	     for (int a=0; a<level.materials.size(); a++)
	       {
		  Material mat = (Material) level.materials.elementAt(a);
		  if (mat instanceof Switch4A)
		    sw = (Switch4A) mat;
	       }
	  }
	
	if (!value)
	  {
	     value=true;
	     sw.count++;
	     sw.room = item.room;
	  }
  }
}

