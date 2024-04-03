import request from '@/utils/request'
import { getToken } from '@/utils/auth'
/**
 * 管理员登录接口
 * @returns
 */
export function adminLogin(params) {
  return request({
    method: 'post',
    url: `/admin/login`,
    data: params
  })
}

/**
 * 获取管理员个人信息接口
 * @returns
 */
export function getAdminInfo() {
  return request({
    url:`/admin/info`,
    method:'get',
    headers:{'token': getToken()}
  })
}