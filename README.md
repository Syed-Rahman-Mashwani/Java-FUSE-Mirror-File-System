Java FUSE Mirror File System
============================

I am curently working on Semantic File System (360ᵒ-SFS) Project. Java FUSE Mirror File System is a part of my 360ᵒ-SFS implementation.

I searched the web but did not find Java based FUSE Mirror File System. So, I made it public on github.

To the best of my current knowledge, this is the first JAVA based Mirror File System shared publically on web.

It has both read and write functionalities. 

-----------------------------

Java Fuse Mirror File System is developed using FUSE-JNA (FUSE Java Binding). 

Fuse-JNA is downloadable from https://github.com/EtiennePerot/fuse-jna. 


--------------------------

How To Run it (in Eclipse)
-------------------------
Open it  

modify first two variable in java_mirror_file_system.java files as per your settings:

e.g.

	static File f_mountPoint = new File ("./target/mirrorFS"); // set mount point (path to a blank folder)
	private final String mirroredFolder = "./target/mirrorFolder";  //set the path of a folder with you want to be mirrored.	

	
Import FUSE-JNA Library

and RUN 

That’s it 


License 
----------------------
	/*
	 * Java Mirror File System
	 * Copyright (C) 20014 Syed Rahman Mashwani syed.rahman[REMOVE-IT]@uop.edu.pk
	 * 
	 * This software is released as-is under the GPL license. 
	 */
