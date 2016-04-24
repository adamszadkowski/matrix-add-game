package info.szadkowski.matrix.add.game.rest.controller;

import info.szadkowski.matrix.add.game.rest.model.ErrorModel;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ExceptionHandlingController {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorModel> errorHandler(HttpServletRequest req, Exception exception) {
    ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);
    HttpStatus code = responseStatus != null ? responseStatus.code() : INTERNAL_SERVER_ERROR;
    String reason = responseStatus != null ? responseStatus.reason() : "Internal server error";
    return new ResponseEntity<ErrorModel>(new ErrorModel(reason, exception.getMessage(), req.getRequestURL().toString()), code);
  }
}
