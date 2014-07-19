Java-FUSE-Mirror-File-System
============================

Java FUSE Mirror File System

I searched the web but did not find Java based FUSE Mirror Files System. So I developed it shared it publically on github.

To the best of my knowledge, this is the first JAVA based Mirror File System shared publically on web.

-----------------------------

Java Fuse Mirror File System is developed using FUSE-JNA (FUSE Java Binding). 

Fuse-JNA is downloadable from https://github.com/EtiennePerot/fuse-jna. 


--------------------------

How To Run
-------------------------
Open it in eclipse. 

modify first two variable in java_mirror_file_system.java files as per your settings:

e.g.
	static File f_mountPoint = new File ("./target/mirrorFS"); // set mount point (path to a blank folder)
	private final String mirroredFolder = "./target/mirrorFolder";  //set the path of a folder with you want to be mirrored.	

	
Import FUSE-JNA Library

and RUN 

That’s it 
