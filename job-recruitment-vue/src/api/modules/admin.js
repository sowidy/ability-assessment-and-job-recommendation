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
    url: `/admin/info`,
    method: 'get',
    headers: { 'token': getToken() }
  })
}

/** 
 * 添加管理员接口
 * @param {string} password password
  * @param {string} phone phone
  * @returns
 */
export function addAdmin(form) {
  return request.post(`/admin/add?password=${form.password}&phone=${form.phone}`);
}

/** 
 * 修改管理员信息
 * @param {object} params 管理员信息表
 * @param {string} params.avatar 头像
 * @param {object} params.createTime 创建时间
 * @param {string} params.email 邮箱
 * @param {number} params.id 
 * @param {object} params.lastLoginTime 最后登录时间
 * @param {string} params.name 名称
 * @param {string} params.password 密码
 * @param {string} params.phone 账号
 * @param {string} params.status 账号状态 0 禁用 | 1启用
 * @returns
 */
export function updateAdmin(params) {
  return request.post(`/admin/update`, params);
}