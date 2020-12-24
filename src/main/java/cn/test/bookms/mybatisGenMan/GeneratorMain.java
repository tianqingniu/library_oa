package cn.test.bookms.mybatisGenMan;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * mybatis Generator自动生成数据库表对应的mapper,xml,entity
 */
public class GeneratorMain {

	/**
	 * 启动该main方法前先到mbgConfiguration.xml文件中配置需要生成的数据库表
	 * @param args
	 */
	 public static void main(String[] args) {  
		 	System.out.println("mybatis Generator逆向生成工程...");
		 	Long startTime = System.currentTimeMillis();  //
	        List<String> warnings = new ArrayList<String>();  
	        boolean overwrite = true;
	        //如果这里出现空指针，直接写绝对路径即可
	        String genCfg = "/mbgConfiguration.xml";  
	        File configFile = new File(GeneratorMain.class.getResource(genCfg).getFile());  
	        ConfigurationParser cp = new ConfigurationParser(warnings);  
	        Configuration config = null;  
	        try {  
	            config = cp.parseConfiguration(configFile);  
	        } catch (IOException e) {  
	            e.printStackTrace(); 
	        } catch (XMLParserException e) {  
	            e.printStackTrace();  
	        }  
	        DefaultShellCallback callback = new DefaultShellCallback(overwrite);  
	        MyBatisGenerator myBatisGenerator = null;  
	        try {  
	            myBatisGenerator = new MyBatisGenerator(config, callback, warnings); 
	        } catch (InvalidConfigurationException e) {  
	            e.printStackTrace();  
	        }  
	        try {  
	            myBatisGenerator.generate(null);  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }  
	        Long endTime = System.currentTimeMillis();
	        System.out.println("生成完毕。共耗时："+(endTime-startTime)+" 毫秒");
	    }

}
