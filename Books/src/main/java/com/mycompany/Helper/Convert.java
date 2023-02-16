/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Helper;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import java.util.Map;


/**
 *
 * @author student
 */
public class Convert {
    
    public static String convertWithGuava(Map<String, String> map) {
        return Joiner.on(",").withKeyValueSeparator("=").join(map);
    }
    
    public static Map<String, String> convertWithGuava(String mapAsString) {
        return Splitter.on(',').withKeyValueSeparator('=').split(mapAsString);
    }
    
}
