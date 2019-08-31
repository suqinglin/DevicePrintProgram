package com.suql.model;

/**
 * Created by Calm on 2017/12/5.
 * TResponseNoData
 */

public class TResponseNoData
{
    public String code;
    public String message;
    public boolean isSuccess()
    {
        return "200".equals(code);
    }
}
