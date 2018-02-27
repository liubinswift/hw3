package com.viewscenes.fileUpload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUpload extends HttpServlet {
	
	public Hashtable sampleFile = new Hashtable();

	private String uploadPath = "c:\\runplan\\"; // 定义文件的上传路径
	private int MAXFILESIZE = 1024 * 1024 * 1024; // 限制文件的上传大小

	public FileUpload() {
		super();
	}
	public void destroy() {
		super.destroy();
	}

	// 主处理
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 保存文件到服务器中 |Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096); // 超出这个大小放磁盘，否则放在内存中
		ServletFileUpload upload = new ServletFileUpload(factory); // Create a new file upload handler
		upload.setSizeMax(MAXFILESIZE);
		try {
			List fileItems = upload.parseRequest(request); // Parse the request,have a List of file items that you need to process
			Iterator iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				// 如果item.isFormField为true,则代表简单域，否则为file域。Field有2种，一种是简单域，一种是file域。
				if (!item.isFormField()) {
					// 获得文件名，包括文件的扩展名
					String name = item.getName();
					try {
						String fileName = uploadPath + name;
						File file = new File(fileName);

					     //若文件不存在则需要先创建文件所在目录
					     if (!file.exists()) {
					       makeDirectory(file.getParent());
					      }
						item.write(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}
	  /**
	   * 递归创建文件目录
	   * @param pathName 完整路径名称
	   */
	  private static void makeDirectory(String pathName){
	    if (pathName==null)
	      return;
	    File file = new File(pathName);
	    if (!file.exists()) {
	      if (file.getParent() != null)
	        makeDirectory(file.getParent());
	      file.mkdirs();
	    }
	  }
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet请求
	 * @param response
	 *            servlet响应
	 */
	// 如果是GET请求，则调用doGet方法。
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet请求
	 * @param response
	 *            servlet响应
	 */
	// 如果是POST请求，则调用doPost方法
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	/**
	 * Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "ServletName: FileUploaded. extends: HttpServlet.";
	}



	
	public void addSampleFile(String key, String filepath) {
		sampleFile.put(key, filepath);
	}
}
