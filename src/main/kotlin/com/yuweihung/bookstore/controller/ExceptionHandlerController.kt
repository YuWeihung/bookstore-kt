package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.response.ErrorCode
import com.yuweihung.bookstore.response.ErrorException
import com.yuweihung.bookstore.response.Response
import mu.KotlinLogging
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException

private val logger = KotlinLogging.logger { }

/**
 * 全局异常处理
 * @author Yu Weihong
 * @since 2022/7/15
 */
@RestControllerAdvice
class ExceptionHandlerController {
    /**
     * 自定义异常类 ErrorException 的处理
     */
    @ExceptionHandler(ErrorException::class)
    fun handleErrorException(e: ErrorException): Response {
        logger.error { e.message }
        return Response.failure(e)
    }

    /**
     * 处理 POST 请求参数错误
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleJsonParamException(e: MethodArgumentNotValidException): Response {
        val msg = e.fieldErrors[0].defaultMessage
        logger.error { msg }
        val response = if (msg == null) {
            Response.failure(ErrorCode.PARAM_NOT_VALID)
        } else {
            Response.paramNotValid(msg)
        }
        return response
    }

    /**
     * 处理 GET 请求参数错误
     */
    @ExceptionHandler(ConstraintViolationException::class)
    fun handlePathParamException(e: ConstraintViolationException): Response {
        val msg = e.message
        logger.error { msg }
        val response = if (msg == null) {
            Response.failure(ErrorCode.PARAM_NOT_VALID)
        } else {
            Response.paramNotValid(msg)
        }
        return response
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception::class)
    fun handleOtherException(e: Exception): Response {
        logger.error { e.message }
        return Response.failure(ErrorCode.UNKNOWN_ERROR)
    }
}
