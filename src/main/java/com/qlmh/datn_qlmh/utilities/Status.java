package com.qlmh.datn_qlmh.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Status implements Serializable{
    private String code;
    private String message;
    public static String Success="1";
    public static String Fail="0";
    public static int ACTIVE=1;
    public static int INACTIVE=0;
}