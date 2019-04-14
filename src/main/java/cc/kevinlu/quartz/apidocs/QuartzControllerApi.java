package cc.kevinlu.quartz.apidocs;

/**
 *
 * @apiDefine quartz_group 任务调度API
 *
 * @author cc
 */
public class QuartzControllerApi {

    /**
     * @api {post} /job/add 创建调度任务信息
     * @apiDescription 创建调度任务信息
     * @apiVersion 1.0.0
     * @apiGroup quartz_group
     *
     * @apiParam (requestBody) {String{长度1~120个字符}} name 调度任务名称
     * @apiParam (requestBody) {String{长度1~120个字符}} author 调度任务负责人
     * @apiParam (requestBody) {String} url 调度具体任务接口地址
     * @apiParam (requestBody) {String{0-59 * * * * ?}} cornExpression 调度执行时间表达式
     * @apiParam (requestBody) {String} [startTime] 调度任务运行时间区间起期,默认为创建后立即开始
     * @apiParam (requestBody) {String} [endTime] 调度任务运行时间区间止期,默认9999-12-31 23:59:59
     * @apiParam (requestBody) {String} [description] 调度任务描述
     *
     * @apiParamExample {json} 请求示例:
     *{
     * 	"name":"cc_task",
     * 	"author":"cc",
     * 	"url":"http://xxxx/index.php",
     * 	"cornExpression":"0/5 * * * * ?",
     * 	"startTime":"2019-04-09 22:23:00",
     * 	"endTime":"2019-04-10 23:59:59",
     * 	"description":"abc"
     * }
     *
     * @apiSuccess {String} message 结果消息.
     * @apiSuccess {String} code 状态码（200表示请求成功）.
     * @apiSuccess {String} data
     *
     * @apiSuccessExample {json} 请求成功返回值:
     * HTTP/1.1 200 OK
     * {
     *  "code": "200",
     *  "message": "成功",
     *  "data": null
     * }
     *
     */

    /**
     * @api {post} /job/modify 更新调度任务信息
     * @apiDescription 更新调度任务信息
     * @apiVersion 1.0.0
     * @apiGroup quartz_group
     *
     * @apiParam (requestBody) {Number} id 调度任务ID
     * @apiParam (requestBody) {String{长度1~120个字符}} name 调度任务名称
     * @apiParam (requestBody) {String{长度1~120个字符}} author 调度任务负责人
     * @apiParam (requestBody) {String} url 调度具体任务接口地址
     * @apiParam (requestBody) {String{0-59 * * * * ?}} cornExpression 调度执行时间表达式
     * @apiParam (requestBody) {String} [startTime] 调度任务运行时间区间起期,默认为创建后立即开始
     * @apiParam (requestBody) {String} [endTime] 调度任务运行时间区间止期,默认9999-12-31 23:59:59
     * @apiParam (requestBody) {String} [description] 调度任务描述
     *
     * @apiParamExample {json} 请求示例:
     *{
     * 	"id":1,
     * 	"name":"cc_task",
     * 	"author":"cc",
     * 	"url":"http://xxxx/index.php",
     * 	"cornExpression":"0/5 * * * * ?",
     * 	"startTime":"2019-04-09 22:23:00",
     * 	"endTime":"2019-04-10 23:59:59",
     * 	"description":"abc"
     * }
     *
     * @apiSuccess {String} message 结果消息.
     * @apiSuccess {String} code 状态码（200表示请求成功）.
     * @apiSuccess {String} data
     *
     * @apiSuccessExample {json} 请求成功返回值:
     * HTTP/1.1 200 OK
     * {
     *  "code": "200",
     *  "message": "成功",
     *  "data": null
     * }
     *
     */

    /**
     * @api {get} /job/list 查询调度任务列表
     * @apiDescription 查询调度任务列表
     * @apiVersion 1.0.0
     * @apiGroup quartz_group
     *
     * @apiParam {Number{1...}} pageIndex 页码
     * @apiParam {Number{1...}} pageSize 每页条数
     * @apiParam {String{长度1~120个字符}} [name] 调度名称,模糊查询
     * @apiParam {String} [author] 调度负责人
     *
     * @apiParamExample {json} 请求示例:
     *      ?pageIndex=1&pageSize=20&name=&author=
     *
     * @apiSuccess {String} message 结果消息.
     * @apiSuccess {String} code 状态码（200表示请求成功）.
     * @apiSuccess {String} data 返回结果
     * @apiSuccess {Number} data.pageNum 当前页
     * @apiSuccess {Number} data.pageSize 每页的数量
     * @apiSuccess {Number} data.pages 总页数
     * @apiSuccess {Object[]} data.list 具体数据集合
     * @apiSuccess {Number} data.list.id 主键ID
     * @apiSuccess {String} data.list.name 调度任务名称
     * @apiSuccess {String} data.list.author 调度任务负责人
     * @apiSuccess {String} data.list.url 调度具体任务接口地址
     * @apiSuccess {Number{0:停止,1:运行中,2:暂停}} data.list.status 调度任务状态
     * @apiSuccess {String{0-59 * * * * ?}} data.list.cornExpression 调度执行时间表达式
     * @apiSuccess {String{格式:yyyy-MM-dd HH:mm:ss}} data.list.startTime 调度任务运行时间区间起期
     * @apiSuccess {String{格式:yyyy-MM-dd HH:mm:ss}} data.list.endTime 调度任务运行时间区间止期
     * @apiSuccess {String{格式:yyyy-MM-dd HH:mm:ss}} data.list.gmtCreated 创建时间
     * @apiSuccess {String} data.list.description 调度任务描述
     *
     * @apiSuccessExample {json} 请求成功返回值:
     * HTTP/1.1 200 OK
     * {
     *  "code": "200",
     *  "message": "成功",
     *  "data": {
     *      "pageNum":1,
     *      "pageSize":20,
     *      "pages":2,
     *      "list":[{
     *  	    "id":1,
     *  	    "name":"cc_task",
     *  	    "author":"cc",
     *  	    "url":"http://xxxx/index.php",
     *  	    "status":1,
     *  	    "cornExpression":"0/5 * * * * ?",
     *  	    "startTime":"2019-04-09 22:23:00",
     *  	    "endTime":"2019-04-10 23:59:59",
     *  	    "gmtCreated":"2019-04-10 23:59:59",
     *  	    "description":"abc"
     *      },{
     *  	    "id":2,
     *  	    "name":"cc_task2",
     *  	    "author":"cc",
     *  	    "url":"http://xxxx/index.php",
     *  	    "status":0,
     *  	    "cornExpression":"0/5 * * * * ?",
     *  	    "startTime":"2019-04-09 22:23:00",
     *  	    "endTime":"2019-04-10 23:59:59",
     *  	    "gmtCreated":"2019-04-10 23:59:59",
     *  	    "description":"abc"
     *      }]
     *   }
     * }
     *
     */

    /**
     * @api {get} /job/info 查询调度任务详细信息
     * @apiDescription 查询调度任务详细信息
     * @apiVersion 1.0.0
     * @apiGroup quartz_group
     *
     * @apiParam {Number} id 页码
     *
     * @apiParamExample {json} 请求示例:
     *      ?id=1
     *
     * @apiSuccess {String} message 结果消息.
     * @apiSuccess {String} code 状态码（200表示请求成功）.
     * @apiSuccess {String} data 返回结果
     * @apiSuccess {Number} data.id 主键ID
     * @apiSuccess {String} data.name 调度任务名称
     * @apiSuccess {String} data.author 调度任务负责人
     * @apiSuccess {String} data.url 调度具体任务接口地址
     * @apiSuccess {Number{0:停止,1:运行中,2:暂停}} data.status 调度任务状态
     * @apiSuccess {String{0-59 * * * * ?}} data.cornExpression 调度执行时间表达式
     * @apiSuccess {String{格式:yyyy-MM-dd HH:mm:ss}} data.startTime 调度任务运行时间区间起期
     * @apiSuccess {String{格式:yyyy-MM-dd HH:mm:ss}} data.endTime 调度任务运行时间区间止期
     * @apiSuccess {String{格式:yyyy-MM-dd HH:mm:ss}} data.gmtCreated 创建时间
     * @apiSuccess {String} data.description 调度任务描述
     *
     * @apiSuccessExample {json} 请求成功返回值:
     * HTTP/1.1 200 OK
     * {
     *  "code": "200",
     *  "message": "成功",
     *  "data": {
     *  	    "id":1,
     *  	    "name":"cc_task",
     *  	    "author":"cc",
     *  	    "url":"http://xxxx/index.php",
     *  	    "status":1,
     *  	    "cornExpression":"0/5 * * * * ?",
     *  	    "startTime":"2019-04-09 22:23:00",
     *  	    "endTime":"2019-04-10 23:59:59",
     *  	    "gmtCreated":"2019-04-10 23:59:59",
     *  	    "description":"abc"
     *      }
     * }
     *
     */

    /**
     * @api {post} /job/pause 暂停执行调度任务
     * @apiDescription 暂停执行调度任务
     * @apiVersion 1.0.0
     * @apiGroup quartz_group
     *
     * @apiParam (requestBody) {Number} id 调度任务ID
     *
     * @apiParamExample {json} 请求示例:
     *{
     * 	"id":1
     * }
     *
     * @apiSuccess {String} message 结果消息.
     * @apiSuccess {String} code 状态码（200表示请求成功）.
     * @apiSuccess {String} data
     *
     * @apiSuccessExample {json} 请求成功返回值:
     * HTTP/1.1 200 OK
     * {
     *  "code": "200",
     *  "message": "成功",
     *  "data": null
     * }
     *
     */

    /**
     * @api {post} /job/resume 恢复调度任务继续执行
     * @apiDescription 恢复调度任务继续执行
     * @apiVersion 1.0.0
     * @apiGroup quartz_group
     *
     * @apiParam (requestBody) {Number} id 调度任务ID
     *
     * @apiParamExample {json} 请求示例:
     *{
     * 	"id":1
     * }
     *
     * @apiSuccess {String} message 结果消息.
     * @apiSuccess {String} code 状态码（200表示请求成功）.
     * @apiSuccess {String} data
     *
     * @apiSuccessExample {json} 请求成功返回值:
     * HTTP/1.1 200 OK
     * {
     *  "code": "200",
     *  "message": "成功",
     *  "data": null
     * }
     *
     */

    /**
     * @api {post} /job/stop 停止调度任务执行
     * @apiDescription 停止调度任务执行
     * @apiVersion 1.0.0
     * @apiGroup quartz_group
     *
     * @apiParam (requestBody) {Number} id 调度任务ID
     *
     * @apiParamExample {json} 请求示例:
     *{
     * 	"id":1
     * }
     *
     * @apiSuccess {String} message 结果消息.
     * @apiSuccess {String} code 状态码（200表示请求成功）.
     * @apiSuccess {String} data
     *
     * @apiSuccessExample {json} 请求成功返回值:
     * HTTP/1.1 200 OK
     * {
     *  "code": "200",
     *  "message": "成功",
     *  "data": null
     * }
     *
     */

    /**
     * @api {post} /job/start 开启调度任务
     * @apiDescription 开启调度任务，使调度任务开始执行
     * @apiVersion 1.0.0
     * @apiGroup quartz_group
     *
     * @apiParam (requestBody) {Number} id 调度任务ID
     *
     * @apiParamExample {json} 请求示例:
     *{
     * 	"id":1
     * }
     *
     * @apiSuccess {String} message 结果消息.
     * @apiSuccess {String} code 状态码（200表示请求成功）.
     * @apiSuccess {String} data
     *
     * @apiSuccessExample {json} 请求成功返回值:
     * HTTP/1.1 200 OK
     * {
     *  "code": "200",
     *  "message": "成功",
     *  "data": null
     * }
     *
     */

}
