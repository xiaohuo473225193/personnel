package util;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PExceptionHandler {
    @ExceptionHandler({PException.class})
    public Result exceptionHandler(PException e){
        return new Result(e.getCode(),false, e.getMessage());
    }
}
