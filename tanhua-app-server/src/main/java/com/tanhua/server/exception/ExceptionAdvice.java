package com.tanhua.server.exception;

import com.tanhua.model.vo.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author sxs
 * @create 2022-09-10 19:58
 */
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity doBusinessException(BusinessException b){
        b.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(b.getErrorResult());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity doOtherException(Exception e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResult.error());
    }

}
