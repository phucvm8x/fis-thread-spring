package com.fis.thread.main;

import com.fis.thread.ex.ThreadManagerEx;
import com.fis.thread.util.ApiResourceBundle;
import com.fss.thread.ProcessorListener;
import com.fss.thread.ThreadConstant;
import com.fss.thread.ThreadManager;
import com.fss.util.FileUtil;
import com.fss.util.LogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.PrintStream;

@SpringBootApplication(scanBasePackages = { "com.fis" }, exclude = { HibernateJpaAutoConfiguration.class,
		JpaRepositoriesAutoConfiguration.class })
public class Main implements CommandLineRunner {

	@Autowired
	private ProcessorListener processorListener;

	@Autowired
	private ApiResourceBundle resource;

	@Autowired
	private ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String strLogDir = "../SystemDir/";
		String strLogFile = "error.log";
		String strOutputFile = "output.log";

		ThreadConstant.THREAD_CONFIG_FILE = "../ThreadManager.cfg";

		File f = new File(strLogDir + strLogFile);
		FileUtil.forceFolderExist(f.getParent());
		PrintStream ps = new PrintStream(new LogOutputStream(strLogDir + strLogFile));
		PrintStream psOutput = new PrintStream(new LogOutputStream(strLogDir + strOutputFile));
		System.setOut(psOutput);
		System.setErr(ps);
		File fx = new File(System.getProperty("user.dir")).getParentFile();
		System.setProperty("user.dir", fx.getPath());

		ThreadManagerEx tm = new ThreadManagerEx(Integer.parseInt(resource.getProperty("thread.monitor.port")), processorListener);
		tm.setLoadingMethod(ThreadManager.LOAD_FROM_FILE);
		tm.setContext(context);
		tm.setActionLogFile("../Log/action.log");
		tm.start();
	}
}
