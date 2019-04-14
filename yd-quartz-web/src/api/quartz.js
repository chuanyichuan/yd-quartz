import request from '@/utils/request'

export function add(dto) {
  return request({
    url: '/job/add',
    method: 'post',
    data: dto
  })
}

export function fetchList(listQuery) {
  return request({
    url: '/job/list',
    method: 'get',
    data: listQuery
  })
}

export function modify(dto) {
  return request({
    url: '/job/modify',
    method: 'post',
    data: dto
  })
}

export function info(id) {
  return request({
    url: '/job/info',
    method: 'get',
    params: { id }
  })
}

export function pause(id) {
  return request({
    url: '/job/pause',
    method: 'post',
    data: { id }
  })
}

export function resume(id) {
  return request({
    url: '/job/resume',
    method: 'post',
    data: { id }
  })
}

export function stop(id) {
  return request({
    url: '/job/stop',
    method: 'post',
    data: { id }
  })
}

export function start(id) {
  return request({
    url: '/job/start',
    method: 'post',
    data: { id }
  })
}
