package com.fis.thread.ex;

import com.fss.thread.ProcessorListener;
import com.fss.thread.ThreadManager;
import org.springframework.context.ApplicationContext;

/**
 * Author: PhucVM
 * Date: 04/10/2019
 */
public class ThreadManagerEx extends ThreadManager {

    private ApplicationContext context;

    public ThreadManagerEx() throws Exception {
        super();
    }

    public ThreadManagerEx(int iPort, ProcessorListener lsn) throws Exception {
        super(iPort, lsn);
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
