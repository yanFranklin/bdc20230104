/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 查询条件所需的日期初始化、复选框初始化及高级查询
 */
layui.use(['form','jquery','laydate','element','table'],function () {
    var $ = layui.jquery,
        table = layui.table,
        element = layui.element,
        laydate = layui.laydate;
    $(function () {
        //初始化日期控件
        laydate.render({
            elem: '#startTime'
        });
        laydate.render({
            elem: '#endTime'
        });

        //点击高级查询--4的倍数
        $('#seniorSearch').on('click',function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            $(this).parent().toggleClass('bdc-button-box-four');
            $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);

            if($('.bdc-table-box .layui-show .layui-table-main>.layui-table').height() == 0){
                $('.layui-show .layui-table-body .layui-none').html('<img src="../image/table-none.png" alt="">无数据');
            }else {
                $('.layui-show .layui-table-body').height($('.bdc-table-box').height() - 181);
                $('.layui-show .layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 181 - 17);
            }
        });

        var tableData = [
            {
                "id": '01',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '02',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '03',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '04',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '05',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '06',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '07',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '008',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '009',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '010',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '011',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '012',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '013',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '014',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '015',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '016',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            }];
        var tableData1 = [];
        table.render({
            elem: '#pageTable',
            toolbar: '#toolbarDemo',
            title: '用户数据表',
            defaultToolbar: ['filter'],
            even: true,
            cols: [[
                {type: 'checkbox', width:50, fixed: 'left'},
                {field:'id', title:'ID', width:80, fixed: 'left', unresize: true},
                {field:'city', title:'市', width:80},
                {field:'centerName', title:'中心名称', width:200},
                {field:'time', title:'时间', width:160, sort: true},
                {field:'extractingConditions', title:'抽取条件',width:300},
                {field:'extractingSum', title:'抽取总数',minWidth: 100, sort: true},
                {field:'person', title:'质检人',width:100},
                {field:'located', title:'坐落',width:400},
                {fixed: 'right', title:'操作', toolbar: '#barDemo', width:180}
            ]],
            data: tableData,
            page: true,
            done: function () {
                $('.layui-show .layui-table-tool-self').css('right',$('.layui-show .bdc-export-tools').width()+ 17 + 'px');


                if($('.bdc-table-box .layui-show .layui-table-main>.layui-table').height() == 0){
                    $('.layui-show .layui-table-body .layui-none').html('<img src="../image/table-none.png" alt="">无数据');
                }else {
                    $('.layui-show .layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 181);
                }
            }
        });

        var tabTwoIndex = 0;
        element.on('tab(tabFilter)', function(data){
            if($('.bdc-table-box .layui-show .layui-table-main>.layui-table').height() == 0){
                $('.layui-show .layui-table-body .layui-none').html('<img src="../image/table-none.png" alt="">无数据');
            }else {
                $('.layui-show .layui-table-body').height($('.bdc-table-box').height() - 181);
                $('.layui-show .layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 181 - 17);
            }
            if(data.index == 1){
                tabTwoIndex++;
                if(tabTwoIndex == 1){
                    table.render({
                        elem: '#pageTable1',
                        toolbar: '#toolbarDemo',
                        title: '用户数据表1',
                        defaultToolbar: ['filter'],
                        even: true,
                        cols: [[
                            {type: 'checkbox', width:50, fixed: 'left'},
                            {field:'id', title:'ID', width:80, fixed: 'left', unresize: true},
                            {field:'city', title:'市', width:80},
                            {field:'extractingSum', title:'抽取总数',minWidth: 100, sort: true},
                            {field:'person', title:'质检人',width:100},
                            {field:'located', title:'坐落',width:400},
                            {fixed: 'right', title:'操作', toolbar: '#barDemo', width:180}
                        ]],
                        data: tableData,
                        page: true,
                        done: function () {
                            $('.layui-show .layui-table-tool-self').css('right',$('.layui-show .bdc-export-tools').width()+ 17 + 'px');


                            if($('.bdc-table-box .layui-show .layui-table-main>.layui-table').height() == 0){
                                $('.layui-show .layui-table-body .layui-none').html('<img src="../image/table-none.png" alt="">无数据');
                            }else {
                                $('.layui-show .layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 181);
                            }
                        }
                    });
                }
            }
        });

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(pageTable)', function (obj) {
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;

            $.each(data, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedData', {
                        key: v.xmid, value: v //此处填写主键id 暂时用xmid作为demo
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedData', {
                        key: v.xmid, remove: true///此处填写主键id 暂时用xmid作为demo
                    });
                }
            });
            var checkedData = layui.sessionData('checkedData');
        });
        table.on('toolbar(pageTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            exportUrl(checkStatus.data, obj.config);

        });

        function exportUrl(data,obj){
            var url = obj.url;
            var paramData = obj.where;
            $.ajax({
                url: url,
                type: "get",
                data: paramData,
                success: function (data) {
                    if(data.content){
                        exportExcel(data.content,obj);
                    }else{
                        exportExcel(data,obj);
                    }
                }
            });
        }
        /**
         * @description 导出表格方法
         */
        function exportExcel(d, obj){
            var cols = obj.cols[0];
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                layer.alert("<div style='text-align: center'>请选择需要导出Excel的数据行！</div>", {title: '提示'});
                return;
            }
            // 标题
            var showColsTitle = [];
            // 列内容
            var showColsField = [];
            // 列宽
            var showColsWidth = [];
            for(var index in cols){
                if(cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar){
                    showColsTitle.push(cols[index].title);
                    showColsField.push(cols[index].field);
                    if(cols[index].width > 0){
                        showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                    }else if(cols[index].minWidth > 0){
                        showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
                    }else{
                        showColsWidth.push(300 / 100 * 15);
                    }
                }
            }

            var data = new Array();
            $.each(checkedData, function(key, value){
                data.push(value);
            })
            for(var i = 0; i < data.length; i++){
                data[i].xh   = i + 1;
            }
            // 设置Excel基本信息
            $("#fileName").val('demo');//此处填写导出的excel文件名
            $("#sheetName").val('demo');//此处填写导出的excel表格名
            $("#cellTitle").val(showColsTitle);
            $("#cellWidth").val(showColsWidth);
            $("#cellKey").val(showColsField);
            $("#data").val(JSON.stringify(data));
            $("#form").submit();
        }
    });
});