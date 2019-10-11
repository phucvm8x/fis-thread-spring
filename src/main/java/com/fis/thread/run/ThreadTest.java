package com.fis.thread.run;

import com.fis.thread.ex.ThreadManagerEx;
import com.fis.thread.util.ApiResourceBundle;
import com.fss.thread.ManageableThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Author: PhucVM
 * Date: 04/10/2019
 */
public class ThreadTest extends ManageableThread {

    @Autowired
    private ApiResourceBundle resource;

    @Override
    protected void onStartThread() throws Exception {
        super.onStartThread();
        ApplicationContext context = ((ThreadManagerEx) mmgrMain).getContext();
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    protected void processSession() throws Exception {
        logInfo("Thread test is running on port " + resource.getProperty("thread.monitor.port") + " (" + System.currentTimeMillis() + ")");
    }
}
