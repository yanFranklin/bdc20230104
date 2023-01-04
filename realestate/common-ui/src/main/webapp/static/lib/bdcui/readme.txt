前端UI规范代码示例使用说明
1.每次html是一个功能，只需将html中的内容写入自己的代码中，引入页面中对应的css，js文件即可。
2.考虑到多个系统主题色的不同，在示例中使用了less，只需要在common.less中修改相应的颜色，重新编译其他各个子less文件，即可完成对主题色等颜色的修改。


3.在页面中不需要引入less文件，只需引入对应的css文件即可，并且，如果修改了css文件，需要重新编译less文件时，在css内修改的内容需要手动重新改动

4.less的使用


     4.1 对于前端开发人员，可以在webstorm中配置，只要改动less文件，即时编译css文件

（参考网址：https://blog.csdn.net/u012780336/article/details/73287500?locationNum=10&fps=1）


     4.2 对于后台开发人员，在需要改动less文件时，可借用外部工具考拉编译成css文件


     （参考网址：https://www.cnblogs.com/webBlog-gqs/p/7174712.html

                        https://www.cnblogs.com/mrhgw/p/4535429.html


        下载地址：http://koala-app.com/index-zh.html

）
