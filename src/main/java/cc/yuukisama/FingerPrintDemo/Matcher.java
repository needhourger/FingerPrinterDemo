package cc.yuukisama.FingerPrintDemo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

public class Matcher {
	private static String dataPathString=".\\data";
	private static List <UserDetails> list=new ArrayList<UserDetails>();
	public static double dpi=500;
	
	public static void load(String path) {
		dataPathString=path;
		list=UserDetails.load(dataPathString);
	}
	
	public static void setDpi(double x) {
		dpi=x;	
	}
	
	public static UserDetails search(String path) throws IOException {
		UserDetails ret=null;
		
		byte[] targetByte=Files.readAllBytes(Paths.get(path));
		FingerprintTemplate targeTemplate=new FingerprintTemplate().dpi(500).create(targetByte);
		
		FingerprintMatcher matcher=new FingerprintMatcher().index(targeTemplate);
		double high=0;
		for (UserDetails x:list) {
			double score=matcher.match(x.template);
			if (score>high) {
				high=score;
				ret=x;
			}
		}
		double threshold=40;
		return high>=threshold?ret:null;
	}
	
}
