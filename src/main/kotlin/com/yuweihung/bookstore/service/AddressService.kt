package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.dto.AddressDto
import com.yuweihung.bookstore.bean.entity.Address
import com.yuweihung.bookstore.bean.vo.UserVo
import com.yuweihung.bookstore.common.ErrorCode
import com.yuweihung.bookstore.common.ErrorException
import com.yuweihung.bookstore.repository.AddressRepository
import com.yuweihung.bookstore.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 收货地址相关的服务类
 */
@Service
@Transactional
class AddressService(
    val userRepository: UserRepository,
    val addressRepository: AddressRepository,
) {

    /**
     * 添加收货地址
     */
    fun addAddress(addressDto: AddressDto): UserVo {
        val user = userRepository.findByUsername(addressDto.username) ?: throw ErrorException(ErrorCode.USER_NOT_EXIST)
        val address = Address(addressDto.phoneNumber, addressDto.address, user)
        addressRepository.save(address)
        return UserVo(user)
    }

    /**
     * 修改收货地址
     */
    fun modifyAddress(addressDto: AddressDto): UserVo {
        val user = userRepository.findByUsername(addressDto.username) ?: throw ErrorException(ErrorCode.USER_NOT_EXIST)
        val addressList = user.addresses.filter { it.id == addressDto.addressId }
        if (addressList.isEmpty()) {
            throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        }
        val address = addressList[0]
        address.phoneNumber = addressDto.phoneNumber
        address.address = addressDto.address
        addressRepository.save(address)
        return UserVo(user)
    }

    /**
     * 删除收货地址
     */
    fun removeAddress(addressDto: AddressDto): UserVo {
        val user = userRepository.findByUsername(addressDto.username) ?: throw ErrorException(ErrorCode.USER_NOT_EXIST)
        val addressList = user.addresses.filter { it.id == addressDto.addressId }
        if (addressList.isEmpty()) {
            throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        }
        val address = addressList[0]
        user.addresses.remove(address)
        addressRepository.delete(address)
        return UserVo(user)
    }
}
