package com.tanhua.server.exception;

import com.tanhua.model.vo.ErrorResult;
import lombok.Data;

/**
 * @author sxs
 * @create 2022-09-10 19:51
 */
@Data
public class BusinessException extends RuntimeException{

    private ErrorResult errorResult;
    public BusinessException(ErrorResult errorResult) {
        super(errorResult.getErrMessage());
        this.errorResult=errorResult;
    }
}
