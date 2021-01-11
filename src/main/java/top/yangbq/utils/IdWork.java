package top.yangbq.utils;

import java.util.UUID;

public class IdWork {

	
	public static String getId() {
		return UniqId.getInstance ().getUniqID ().replace("-", "").replace ( ".","" ).substring ( 5,20 );
	}
	
}
