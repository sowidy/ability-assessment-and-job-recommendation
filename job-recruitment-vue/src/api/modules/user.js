import request from '@/utils/request'
import { getToken } from '@/utils/token'

//用户登录
export function reUserLogin(params, identity) {
  return request({
    method: 'post',
    url: `/${identity}/login`,
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: {
      phone: params.phone,
      password: params.password
    }
  })
}
//用户注册
export function reUserRegister(params, identity) {
  return request({
    url: `/${identity}/register`,
    method: 'post',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: {
      phone: params.phone,
      name: params.name,
      password: params.password
    }
  })
}
//获取用户信息
export function reGetUserInfo(identity) {
  return request({
    url: `/${identity}/info`,
    headers: { 'token': getToken(), 'Content-Type': 'application/x-www-form-urlencoded' },
    method: 'get',
  })
}

/** 
 * 用户更新头像
 * @param {string} avatar avatar
  * @returns
 */
export function uploadStudentAvatar(avatar, state) {
  return request({
    url: `/${state}/upload/avatar?avatar=${avatar}`,
    method: 'patch',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
  });
}

/** 
 * 更新用户信息接口
 * @param {object} params.bornYear 出生年月
 * @param {string} params.education 学历
 * @param {string} params.email 邮箱
 * @param {string} params.experience 工作经历
 * @param {string} params.gender 性别
 * @param {string} params.major 专业
 * @param {string} params.name 姓名
 * @param {string} params.remark 反馈信息
 * @param {string} params.school 学校
 * @param {string} params.skills 技能
 * @returns
 */
export function updateUser(params, state) {
  console.log(params);
  return request({
    url: `/${state}/update`,
    method: 'post',
    data: params
  });
}

/** 
 * 上传简历存储到本地接口
 * @returns
 */
export function uploadResume(params) {
  return request.post(`/upload/resume`, params);
}

/** 
 * 下载简历接口
 * @param {string} filename
  * @returns
 */
export function download(filename) {
  return request.get(`/download/resume?filename=${filename}`);
}

/** 
 * 获取简历接口
 * @returns
 */
export function getResume() {
  return request.get(`/resume`);
}

/** 
 * 根据id查询简历
 * @param {string} id id
  * @returns
 */
export function getResumeById(id) {
  return request.get(`/public/resumeById?id=${id}`);
}

/** 
 * 上传简历到用户信息的接口
 * @param {object} params 简历信息表
 * @param {object} params.createTime 创建时间
 * @param {number} params.id 主键
 * @param {string} params.linkName 上传后存储的名称
 * @param {string} params.originName 文件原来名称
 * @returns
 */
export function addResume(params) {
  return request.post(`/resume`, params);
}

/**
 * 获取用户反馈
 * @returns
 */
export function getRemarkList() {
  return request.get(`/public/remarks`);
}

/** 
 * 获取所有学生列表接口
 * @returns
 */
export function getAllStudent(size) {
  return request.get(`/public/student/list?size=${size}`);
}

// /** 
//  * 获取所有学生列表接口
//  * @returns
//  */
// export function reGetHotRecomment() {
//   return request.get(`/public/student/list`);
// }

/** 
 * 按不同条件查询学生
 * @param {string} education 学历
  * @param {string} gender 性别
  * @param {string} major 专业
  * @param {string} name 姓名
  * @param {string} school 学校
  * @returns
 */
export function queryStudent(query) {
  return request.get(`/public/query/student?education=${query.education}&gender=${query.gender}&isAsc=${query.isAsc}&major=${query.major}&name=${query.name}&pageNo=${query.pageNo}&pageSize=${query.pageSize}&school=${query.school}&sortBy=${query.sortBy}&resumeId=${query.resumeId}&skills=${query.skills}`);
}

/** 
 * 按技能查找
 * @param {string} skill skill
  * @returns
 */
export function queryBySkills(skill) {
  return request.get(`/student/getListBySkills?skill=${skill}`);
}

/** 
 * 通过id查询学生
 * @param {string} id id
  * @returns
 */
export function findStudentById(id) {
  return request.get(`/public/getStudentByID/${id}`);
}

/**
 * 通过ids删除学生
 * @param {string} ids ids
  * @returns
 */
export function deleteByIds(ids) {
  return request.delete(`/student/deleteByIds?ids=${ids}`);
}
/** 
 * 后台-只删除学生反馈，不删除学生信息
 * @param {string} ids ids
  * @returns
 */
export function deleteRemark(ids) {
  return request.delete(`/student/delete/remark?ids=${ids}`);
}
/** 
 * 后台-获取学生/企业总人数接口
 * @returns
 */
export function getTotalCount(state) {
  return request.get(`/${state}/count`);
}

/** 
 * 后台-获取用户反馈总条数
 * @returns
 */
export function getRemarkCount() {
  return request.get(`/student/remark/count`);
}

/** 
 * 后台-获取用户所有反馈信息
 * @returns
 */
export function getAllRemark() {
  return request.get(`/student/remark/list`);
}

/** 
 * 后台-分页查询用户反馈信息
 * @param {string} isAsc 是否升序
  * @param {string} name 姓名
  * @param {string} pageNo 页码
  * @param {string} pageSize 页码
  * @param {string} remark 6方面反馈的评分
  * @param {string} remarkText 反馈的文本信息
  * @param {string} sortBy 排序字段
  * @param {string} totalScore 反馈总评分
  * @returns
 */
export function getRemarkVoByPage(query) {
  return request.get(`/student/remark/page?isAsc=${query.isAsc}&name=${query.name}&pageNo=${query.pageNo}&pageSize=${query.pageSize}&remark=${query.remark}&remarkText=${query.remarkText}&sortBy=${query.sortBy}&totalScore=${query.totalScore}`);
}
/** 
 * 更新用户密码接口
 * @returns
 */
export function updateUserPassword(state, params) {
  return request.patch(`/${state}/updatepwd`, params);
}

/** 
 * 修改用户手机号码
 * @param {string} phone phone
  * @returns
 */
export function updatePhone(state, phoneForm) {
  return request.patch(`/${state}/update/phone?phone=${phoneForm.phone}`);
}

/** 
 * 查询收藏
 * @param {string} titleId titleId
  * @returns
 */
export function checkFavorite(titleId) {
  return request.post(`/favorite/checkFavorite?titleId=${titleId}`);
}

/** 
 * 添加收藏
 * @param {string} titleId titleId
  * @returns
 */
export function addFavoriteTitle(titleId) {
  return request.post(`/favorite/addTitle?titleId=${titleId}`);
}

/** 
 * 删除收藏
 * @param {string} TitleId TitleId
  * @returns
 */
export function deleteFavoriteTitle(TitleId) {
  return request.delete(`/favorite/deleteTitle?TitleId=${TitleId}`);
}

/** 
 * 获取收藏职位id
 * @returns
 */
export function getFavoriteId() {
  return request.get(`/favorite/getFavoriteId`);
}

/** 
 * 删除所有收藏
 * @returns
 */
export function deleteAllFavoriteTitles() {
  return request.delete(`/favorite/deleteAllTitle`);
}
