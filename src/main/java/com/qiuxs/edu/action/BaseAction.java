package com.qiuxs.edu.action;

import java.io.PrintWriter;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.qiuxs.edu.util.Strings;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{

	private Map<String, String> pair ;


	private static final long serialVersionUID = -7771051448180391606L;

	protected void put(String key,Object value){
		ActionContext.getContext().getContextMap().put(key, value);
	}

	protected String getString(String attr){

        Object value = ActionContext.getContext().getParameters().get(attr);
		if (null == value) { return null; }
		if (!value.getClass().isArray()) { return value.toString(); }
		String[] values = (String[]) value;
		if (values.length == 1) {
			return values[0];
		} else {
			return Strings.join(values, ",");
		}
	}

	protected void write(String msg) throws Exception{
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");   
		//获取输出流，然后使用  
		PrintWriter out = ServletActionContext.getResponse().getWriter(); 
		try {
			out.print(msg);
			out.flush();  
		} catch (Exception e) {
			throw e;
		}finally{
			out.close();
		}

	}
	protected void write(Object o) throws Exception{
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");   
		//获取输出流，然后使用  
		PrintWriter out = ServletActionContext.getResponse().getWriter(); 
		try {
			out.print(getJson(o));
			out.flush();  
		} catch (Exception e) {
			throw e;
		}finally{
			out.close();
		}

	}
	public void writeSuccese(String msg) throws Exception{
		Map<String, String> map = new HashedMap<String, String>();
		map.put("status", "success");
		map.put("msg", msg);
		write(map);

	}
	public void writeFail(String msg) throws Exception{
		Map<String, String> map = new HashedMap<String, String>();
		map.put("status", "error");
		map.put("msg", msg);
		write(map);

	}

	protected String getJson(Object object){
		Gson gson = new GsonBuilder().serializeNulls() .create();
		return gson.toJson(object);

		//		Gson gson = new GsonBuilder()
		//	    //序列化null
		//	    .serializeNulls()
		//	    // 设置日期时间格式，另有2个重载方法
		//	    // 在序列化和反序化时均生效
		//	    .setDateFormat("yyyy-MM-dd")
		//	    // 禁此序列化内部类
		//	     .disableInnerClassSerialization()
		//	    //生成不可执行的Json（多了 )]}' 这4个字符）
		//	    .generateNonExecutableJson()
		//	     //禁止转义html标签
		//	    .disableHtmlEscaping()
		//	    //格式化输出
		//	    .setPrettyPrinting()
		//	    .create();
		//	//：内部类(Inner Class)和嵌套类(Nested Class)的区别
	}
	public Map<String, String> getPair() {
		return pair;
	}
	public void setPair(Map<String, String> pair) {
		this.pair = pair;
	}

	public String getPairValue(String key){

		String s = null;

		if (pair!=null) {
			s = pair.get(key);
		}

		return s==null?"":s;

	}
	public void putPairValue(String key,String value){
		
		
		if (pair!=null) {
			pair.put(key, value);
		}
		
		
	}
	
	public int getPairInt(String key){

		int i = 0;

		try {
			i = Integer.parseInt(getPairValue(key));
		} catch (Exception e) {
		}

		return i;


	}

}
