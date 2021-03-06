package com.droidquest.devices;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.droidquest.Room;
import com.droidquest.chipstuff.Port;
import com.droidquest.items.Item;

public class RoomSensor extends Device 
{
String targetClass;
Item target;
Dimension d1 = new Dimension(); // Output pointing Right, Left
Dimension d2 = new Dimension(); // Output pointing Up, Down

public RoomSensor(int X, int Y, Room r, Item item) 
  {
	x=X; y=Y; room = r;
	target = item;
	editable=true;
	targetClass = target.getClass().toString().substring(6); // Removes "class "
	rotation = 1; // Right
	d1.width = 32 + target.getWidth();
	d1.height = Math.max(target.getHeight()+8,18);
	d2.width = Math.max(target.getWidth()+8,18);
	d2.height = 32 + target.getHeight();
	width = d1.width;
	height = d1.height;
	ports = new Port[1];
	ports[0] = new Port(width-2,height/2,Port.TYPE_OUTPUT,24,Port.ROT_UP,this);
	icons = new ImageIcon[2];
	GenerateIcons();
  }

public void GenerateIcons() 
  {
	if (ports==null)
	  {
	     ports = new Port[1];
	     ports[0] = new Port(width-2,height/2,Port.TYPE_OUTPUT,24,Port.ROT_UP,this);
	     if (rotation > 0)
	       {
		  int rot = rotation;
		  if (rotation%2==1)
		    {
		       int temp = width;
		       width = height;
		       height = temp;
		    }
		  rotation = 0;
		  for (int r=0; r<rot; r++)
		    rotate(1);
	       }
	  }
	icons = new ImageIcon[2];
	icons[0] = new ImageIcon( new BufferedImage(d2.width,d2.height,BufferedImage.TYPE_4BYTE_ABGR));
	icons[1] = new ImageIcon( new BufferedImage(d1.width,d1.height,BufferedImage.TYPE_4BYTE_ABGR));
	target.GenerateIcons();
	currentIcon = icons[rotation%2].getImage();
  }

public boolean Function() 
  {
	ports[0].value=false;
	if (room.portalItem == null)
	  {
	     // Room Sensor is not inside robot.
	     for (int a=0;a<level.items.size(); a++)
	       {
		  Item item = (Item) level.items.elementAt(a);
		  if (item.room==room && item.carriedBy==null)
//		    if (item.getClass().toString().endsWith(targetClass))
		    if (target.getClass().isInstance(item))
		      {
			 ports[0].value=true;
			 a=level.items.size();
		      }
	       }
	  }
	else
	  {
	     // Room Sensor is inside Robot.
	     for (int a=0;a<level.items.size(); a++)
	       {
		  Item item = (Item) level.items.elementAt(a);
		  if (item.room == room.portalItem.room  && item.carriedBy==null)
		    if (target.getClass().isInstance(item))
		      {
			 ports[0].value=true;
			 a=level.items.size();
		      }
	       }
	  }	
	return false;
  }

public void Decorate() 
  {
	super.Decorate();
	g.setColor(Color.white);
	switch (rotation)
	  {
	   case Port.ROT_UP:
	     g.drawRect(0,24,width,height-24);
	     g.drawRect(1,25,width-2,height-26);
	     g.drawImage(target.currentIcon, width/2-target.getWidth()/2, 28, level);
	     break;
	   case Port.ROT_RIGHT:
	     g.drawRect(0,0,width-24,height);
	     g.drawRect(1,1,width-26,height-2);
	     g.drawImage(target.currentIcon, 4, 4, level);
	     break;
	   case Port.ROT_DOWN:
	     g.drawRect(0,0,width,height-24);
	     g.drawRect(1,1,width-2,height-26);
	     g.drawImage(target.currentIcon, 4, 4, level);
	     break;
	   case Port.ROT_LEFT:
	     g.drawRect(24,0,width-24,height);
	     g.drawRect(25,1,width-26,height-2);
	     g.drawImage(target.currentIcon, 28, height/2-target.getHeight()/2, level);
	     break;
	  }
  }

public void rotate(int dir) 
  {
	if (rotation ==0 && dir == -1)
	  rotation = 3;
	else if (rotation == 3 && dir == 1)
	  rotation =0;
	else
	  rotation += dir;
	
	if (rotation%2==0) // if rotation == Up or Down
	  {
	     width = d2.width;
	     height = d2.height;
	  }
	else
	  {
	     width = d1.width;
	     height = d1.height;
	  }
	switch(rotation)
	  {
	   case Port.ROT_UP:
	     ports[0].x = width/2-2;
	     ports[0].y = 0;
	     break;
	   case Port.ROT_RIGHT:
	     ports[0].x = width-2;
	     ports[0].y = height/2-2;
	     break;
	   case Port.ROT_DOWN:
	     ports[0].x = width/2+1;
	     ports[0].y = height;
	     break;
	   case Port.ROT_LEFT:
	     ports[0].x = 0;
	     ports[0].y = height/2+1;
	     break;
	  }
  }

public void Erase() 
  {
	super.Erase();
	target = null;
  }

}
