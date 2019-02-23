package cc.yuukisama.FingerPrintDemo;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.machinezoo.sourceafis.FingerprintTemplate;

public class UserDetails {
	public static int num=0;
	
	public int id;
	public FingerprintTemplate template=null;
	public String name;
	public String path;
	
	public static double dpi=500;
	private static final String[] TYPE= {"png"};
	
	public static void setDpi(double x) {
		dpi=x;
	}
	
	public UserDetails(String imagePath) {
		this.id=num;
		UserDetails.num++;
		
		this.path=imagePath;
		File file=new File(imagePath);
		file=file.getParentFile();
		this.name=file.getName();
		
		byte[] imageByte=null;
		try {
			imageByte = Files.readAllBytes(Paths.get(imagePath));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			template=null;
			return;
		}
		template=new FingerprintTemplate().dpi(dpi).create(imageByte);
	}
	
	public static List<UserDetails> load(String dataPath){
		List<UserDetails> ret =new ArrayList<UserDetails>();
		
		File file=new File(dataPath);
    	
    	File[] files=file.listFiles();
    	for (File f:files) {
    		if (f.isFile() && isImage(f)) {
    			System.out.println("[load]:"+f.getPath());
    			UserDetails temp = new UserDetails(f.getPath());
    			ret.add(temp);
    		}
    		else if (f.isDirectory()) {
				ret.addAll(UserDetails.load(f.getAbsolutePath()));
			}
    	}
    	return ret;
	}

	private static boolean isImage(File f) {
		// TODO 自动生成的方法存根
		String filename=f.getName();
		String fileType=filename.substring(filename.lastIndexOf(".")+1,filename.length()).toLowerCase();
		
		for (String x:TYPE) 
		if (fileType.compareTo(x)==0) return true;
		return false;
	}
}
