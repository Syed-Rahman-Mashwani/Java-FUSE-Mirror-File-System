
/*
 * Java Mirror File System
 * Copyright (C) 20014 Syed Rahman Mashwani <syed.rahman[REMOVE-IT]@upesh.edu.pk>
 * 
 * This software is released as-is under the GPL license. 
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.fusejna.DirectoryFiller;
import net.fusejna.ErrorCodes;
import net.fusejna.FlockCommand;
import net.fusejna.FuseException;
import net.fusejna.StructFlock.FlockWrapper;
import net.fusejna.StructFuseFileInfo.FileInfoWrapper;
import net.fusejna.StructStat.StatWrapper;
import net.fusejna.types.TypeMode.ModeWrapper;
import net.fusejna.types.TypeMode.NodeType;
import net.fusejna.util.FuseFilesystemAdapterFull;

public class java_mirror_file_system extends FuseFilesystemAdapterFull
{	
	static File f_mountPoint = new File ("./target/mirrorFS"); // set mount point (path to a blank folder)
	private final String mirroredFolder = "./target/mirrorFolder";  //set the path of a folder which you want to be mirrored.	
	private final static String mountPoint = f_mountPoint.getAbsolutePath(); 
	
	public static void main(String args[]) throws FuseException
	{		
		new java_mirror_file_system().log(true).mount(mountPoint); 
	}
	
	@Override
	public int getattr(final String path, final StatWrapper stat)
	{		
		File f = new File(mirroredFolder+path);
		
		//if current path is of file
		if (f.isFile())
		{
			stat.setMode(NodeType.FILE,true,true,true,true,true,true,true,true,true);
			stat.size(f.length());
			stat.atime(f.lastModified()/ 1000L);
			stat.mtime(0);
		    stat.nlink(1);
		    stat.uid(0);
		    stat.gid(0);
			stat.blocks((int) ((f.length() + 511L) / 512L));
			return 0;
		}
		
		//if current file is of Directory
		else if(f.isDirectory())
		{
			stat.setMode(NodeType.DIRECTORY);
			return 0;
		}
				
		return -ErrorCodes.ENOENT();
	}


	@Override
	public int read(final String path, final ByteBuffer buffer, final long size, final long offset, final FileInfoWrapper info)
	{

		Path p = Paths.get(mirroredFolder+path);
		try {
			byte[] data = Files.readAllBytes(p);				
			if ((offset + size) > data.length)
			{
				buffer.put(data, (int) offset, data.length-(int) offset);
				return (int) size;
			}
			else
			{
				buffer.put(data, (int) offset, (int) size);
				return (int) size;
			}													
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return 0;
	}

	public int write(final String path, final ByteBuffer buf, final long bufSize, final long writeOffset,
			final FileInfoWrapper wrapper)
	{
		byte[] b = new byte[(int) bufSize];
		buf.get(b);
		
		
		try {			
			FileOutputStream output = new FileOutputStream(mirroredFolder+path, true);
			output.write(b); 
			output.close();
			return (int) bufSize;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			
		return -1; 			
	}
	
	@Override
	public int opendir(final String path, final FileInfoWrapper info)
	{
		return 0;
	}

	@Override
	public int readdir(final String path, final DirectoryFiller filler)
	{				
		//adding fillers to directory
		File f = new File(mirroredFolder+path);
		
		if(f.isDirectory())
		{			
			 File[] fList = f.listFiles();
			    for (File file : fList) 
			    {
			        filler.add(file.toString());
			    }

		}
		
		return 0;
	}

	@Override
	public int create(final String path, final ModeWrapper mode, final FileInfoWrapper info)
	{
		File f = new File(mirroredFolder+path);
		try {
			f.createNewFile();
			mode.setMode(NodeType.FILE, true, true, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
	public int mkdir(final String path, final ModeWrapper mode)
	{
		File f = new File(mirroredFolder+path);
		if (f.exists()) 
		{
			f = null;
			return -ErrorCodes.EEXIST();
		}
		
		else
		{
			f.mkdir();		
		}
		
		return 0;
	}
		
	@Override
	public int open(final String path, final FileInfoWrapper info)
	{			
		return 0;
	}
	

	@Override
	public int rename(final String path, final String newName)
	{						
		File f = new File(mirroredFolder+path);
	    File f1 = new File(mirroredFolder+newName);
	    boolean bool = false;
							 		      
	     try{
	    	 
	    	// renaming file or folder	         
	        bool = f.renameTo(f1);		   
	        System.out.print("File/folder renamed? "+bool+" Rename from "+path+" To "+newName);
        	        
	      }catch(Exception e){
	         // if any error occurs
	         e.printStackTrace();
	      }
		     
		     
	     return 0;
	} 
		
	
	@Override
	public int readlink(final String path, final ByteBuffer buffer, final long size)
	{
		/*
		Path link = Paths.get(mountPoint+path);
		
		if (Files.isSymbolicLink(link))
		{
			System.out.println("map get. symlink path= "+path+" Target file Path=" +mapSymLink.get(path));
			byte[] b = mapSymLink.get(path).getBytes(Charset.forName("UTF-8"));
			buffer.put(b);
		}
		*/
		return 0;
	}
	
	
	@Override
	public int lock(final String path, final FileInfoWrapper info, final FlockCommand command, final FlockWrapper flock)
	{
		return 0;
		//return -ErrorCodes.ENOSYS();
	}
	
}





