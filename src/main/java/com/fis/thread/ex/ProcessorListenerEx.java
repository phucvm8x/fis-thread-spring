package com.fis.thread.ex;

import com.fss.thread.ManageableThread;
import com.fss.thread.ProcessorListener;
import com.fss.thread.ThreadProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Author: PhucVM
 * Date: 04/10/2019
 */
@Component
public class ProcessorListenerEx implements ProcessorListener {

    @Autowired
    private DataSource dataSource;

    @Override
    public Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    @Override
    public void onCreate(ThreadProcessor threadProcessor) throws Exception {

    }

    @Override
    public void onOpen(ThreadProcessor threadProcessor) throws Exception {
        threadProcessor.mcnMain = getConnection();
    }
}
