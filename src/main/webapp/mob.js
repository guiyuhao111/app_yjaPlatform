/**
 * Created by Administrator on 2017/10/25.
 */
/*
 * MobLink 支持数组=>MobLink([...]) 和对象=>MobLink({...})
 * 页面上有多个元素需要跳转时使用数组方式,仅单个元素时可以使用对象的方式进行初始化
 * el: 表示网页上Element的id值,该字段为空或者不写则表示MobLink默认浮层上的打开按钮(注意:必须为元素id,以#开头)
 * path: 对应App里的路径
 * params: 网页需要带给客户端的参数
 */
MobLink([
    {
        el: '#openAppBtn2',
        path: '',
        params: {
            key1: 'value1',
        key2: 'value2',
    }
    },
    {
        el: '#openAppBtn',
        path: '/html/home/list_win.html',
            params: {
            key1: 'value1',
            key2: 'value2',
        }
    },
]);