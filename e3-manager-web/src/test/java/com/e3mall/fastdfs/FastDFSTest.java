package com.e3mall.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.e3mall.common.utils.FastDFSClient;

public class FastDFSTest {

	@Test
	public void testUploadFile() throws Exception {
		// 1、把FastDFS客户端的jar包添加到工程中，jar包在中央仓库中没有。
		// 2、创建一个配置文件。配置trackerServer所在的ip地址和端口号
		// 3、加载配置文件。
		ClientGlobal.init("E:/Workspaces/Eclipse/e3-manager-web/src/main/resources/conf/client.conf");
		// 4、创建一个TrackerClient对象，直接new，没有参数。
		TrackerClient trackerClient = new TrackerClient();
		// 5、通过TrackerClient对象获得TrackerServer对象。
		TrackerServer trackerServer = trackerClient.getConnection();
		// 6、创建一个StorageClinet对象，需要两个参数TrackerServer、StorageServer（null）
		StorageClient storageClient = new StorageClient(trackerServer, null);
		// 7、使用StorageClient上传文件，返回文件的路径及文件名。
		//参数1：文件名及路径 参数2：扩展名 参数3：元数据
		String[] strings = storageClient.upload_file("C:/Users/Administrator/Desktop/1.jpg", "jpg", null);
		// 8、打印结果
		for (String string : strings) {
			System.out.println(string);
		}
	}
	
	@Test
	public void testFastDFSClient() throws Exception {
		//创建一个FastDFSClient对象
		FastDFSClient fastDFSClient = new FastDFSClient("E:/Workspaces/Eclipse/e3-manager-web/src/main/resources/conf/client.conf");
		//使用对象上传文件，返回文件路径
		String result = fastDFSClient.uploadFile("C:/Users/Administrator/Desktop/2.jpg");
		//打印结果
		System.out.println(result);
	}
}
