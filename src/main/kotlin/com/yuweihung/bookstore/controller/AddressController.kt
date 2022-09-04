package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.dto.AddressDto
import com.yuweihung.bookstore.bean.vo.UserVo
import com.yuweihung.bookstore.common.Response
import com.yuweihung.bookstore.service.AddressService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * 收货地址控制类
 */
@RestController
@RequestMapping("/address")
class AddressController(
    val addressService: AddressService,
) {

    /**
     * 添加收货地址
     */
    @PostMapping("/add")
    fun addAddress(@Valid @RequestBody addressDto: AddressDto): Response {
        val user = addressService.addAddress(addressDto)
        val result = UserVo(user)
        return Response.success(result)
    }

    /**
     * 修改收货地址
     */
    @PostMapping("/modify")
    fun modifyAddress(@Valid @RequestBody addressDto: AddressDto): Response {
        val user = addressService.modifyAddress(addressDto)
        val result = UserVo(user)
        return Response.success(result)
    }

    /**
     * 删除收货地址
     */
    @PostMapping("/remove")
    fun removeAddress(@RequestBody addressDto: AddressDto): Response {
        val user = addressService.removeAddress(addressDto)
        val result = UserVo(user)
        return Response.success(result)
    }
}
