package com.yuweihung.bookstore.common

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import javax.servlet.http.HttpServletResponse

/**
 * HTTP 响应的格式定义和封装
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class Response(
    val code: Int = 0,
    val message: String = "success",
    val data: Any? = null,
) {
    companion object {

        /**
         * 成功返回 0 和数据（如存在）
         */
        fun success(): Response {
            return Response()
        }

        fun success(data: Any): Response {
            return Response(data = data)
        }

        /**
         * 失败返回错误码和错误信息
         */
        fun failure(errorCode: BaseErrorInterface): Response {
            return Response(code = errorCode.code, message = errorCode.message)
        }

        /**
         * 参数校验错误
         */
        fun paramNotValid(message: String): Response {
            return Response(code = ErrorCode.PARAM_NOT_VALID.code, message = message)
        }

        /**
         * 直接修改 HttpServletResponse 的封装
         */
        fun render(response: HttpServletResponse, result: Response) {
            response.setHeader("Content-type", "application/json;charset=UTF-8")
            val mapper = jacksonObjectMapper()
            mapper.writeValue(response.writer, result)
        }
    }
}
