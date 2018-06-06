package com.zjw.aliyunmq.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 异步处理异常类：
 * Created by 嘉炜 on 2017/9/23.
 */
@Data
@AllArgsConstructor
public class AsyncException extends Exception  {
    private int code;
    private String errorMessage;
}
