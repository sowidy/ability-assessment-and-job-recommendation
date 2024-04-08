import request from '@/utils/request'
//用户与企业登录，获取信息 相似，在user中进行了实现
//获取企业列表信息 在首页进行展示
export function reGetEnterpriseList() {
  return request({
    url: '/enterprise/list',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
  })
}

//获取热门企业
export function reGetHotTitle(size) {
  return request({
    url: `/public/enterprise/list?size=${size}`,
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
  })
}
//获取热门推荐岗位
export function reGetHotRecomment(identity,size) {
  return request({
    url: `/public/${identity}/list?size=${size}`,
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
  })
}
/**
 * 按不同条件查询
 * @param {string} address 地址
 * @param {string} isAsc 是否升序
 * @param {string} name 企业名称关键字
 * @param {string} pageNo 页码
 * @param {string} pageSize 页码
 * @param {string} salaryMax 最高薪资
 * @param {string} salaryMin 最低薪资
 * @param {string} sortBy 排序字段
 * @param {string} type 类型
 * @returns
 */
export function conditionQuery(conditionFrom) {
  return request({
    url: `/public/queryByCondition?address=${conditionFrom.address}&enterpriseAuth=${conditionFrom.enterpriseAuth}&isAsc=${conditionFrom.isAsc}&name=${conditionFrom.name}&title=${conditionFrom.title}&pageNo=${conditionFrom.pageNo}&pageSize=${conditionFrom.pageSize}&salaryMax=${conditionFrom.salaryMax}&salaryMin=${conditionFrom.salaryMin}&sortBy=${conditionFrom.sortBy}&type=${conditionFrom.type}`,
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
  });
}

/** 
 * 根据id查询企业
 * @param {string} id id
  * @returns
 */
export function findHotById(id,identity) {
  // console.log(identity);
  return request({
    url: `/public/get${identity}ByID/${id}`,
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
  });
}

/** 
 * 根据期望职业查询职位
 * @param {string} title title
  * @returns
 */
export function queryByName(title) {
  return request.get(`/public/queryByName?title=${title}`);
}

/** 
 * 上传或更新认证信息
 * @param {string} material material
  * @returns
 */
export function addOrUploadMaterial(material) {
  return request.post(`/enterprise/upload/material?material=${material}`);
}


/** 
 * 通过id修改某个企业信息
 */
export function uploadInfoById(params) {
  return request.post(`/enterprise/uploadById`, params);
}

/** 
 * 更新企业信息
 */
export function uploadEnterpriseInfo(params) {
  return request.post(`/enterprise/update`, params);
}

/**
 * 通过ids删除企业
 * @param {string} ids ids
  * @returns
 */
export function deleteByIds(ids) {
  return request.delete(`/enterprise/deleteByIds?ids=${ids}`);
}

/** 
 * 随机获取几条数据
 * @param {string} size size
  * @returns
 */
export function getByRandomSize(size) {
  return request.get(`/public/getByRandom?size=${size}`);
}