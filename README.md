# baba-cdn-authenticode
Authenticode generator to support URL authentication by Alibaba Cloud CDN


MD5Generator follows the standard guide on the URL below

https://www.alibabacloud.com/help/doc-detail/27135.htm


key = "aliyuncdnexp1234";	//	key defined from CDN console

uri = "/video/standard/1K.html";	//	URL to download

//// Sample for Type A

// with 1 day expiry

System.out.println(MD5Generator.getAuthStringA(uri, key));

//// Sample for Type B

uri = "/4/44/44c0909bcfc20a01afaf256ca99a8b8b.mp3";

//	with 1800 S of expiry from now

System.out.println(MD5Generator.getAuthStringB(uri, key));	

//	expiry at given time

System.out.println(MD5Generator.getAuthStringB(uri, "201508150800", key)); 

//// Sample for Type C

uri = "/test.flv";

//	with 1 day expiry from now, type C format 1

System.out.println(MD5Generator.getAuthStringC1(uri, key));	

//	expiry at given time in UNIX format, type C format 1

System.out.println(MD5Generator.getAuthStringC1(uri, "55CE8100", key));	

//	MD5 string to pass to the URL parameter for type C format 2

System.out.println(MD5Generator.getMDStringC(uri, "55CE8100", key));	


.