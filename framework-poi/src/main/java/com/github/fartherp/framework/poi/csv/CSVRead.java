/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.csv;

import au.com.bytecode.opencsv.CSVReader;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/1/17
 */
public class CSVRead<E> {
    public void read(InputStream inputStream, CSVReaderDeal<E> deal) {
        CSVReader reader = new CSVReader(new InputStreamReader(new DataInputStream(inputStream)));
        try {
            int tmp = deal.getBatchCount();
            List<E> l = new ArrayList<E>(tmp);
            int i = 0;
            String [] arr;
            while ((arr = reader.readNext()) != null) {
                ++i;
                if (i <= deal.skipLine()) {
                    continue;
                }
                E o = deal.dealBean(arr);
                if (o != null) {
                    l.add(o);
                    if (i % tmp == 0) {
                        deal.dealBatchBean(l);
                        l = new ArrayList<E>(tmp);
                    }
                }
            }
            if (!l.isEmpty()) {
                deal.dealBatchBean(l);
            }
        } catch (IOException e) {
            // ignore
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                // ignore
                reader = null;
            }
        }
    }
}
