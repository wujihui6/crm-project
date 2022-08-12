<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet"  type="text/css" href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

	<!--  PAGINATION plugin -->
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
<script type="text/javascript">

	$(function(){
		//给"创建"按钮添加单击事件
		$("#createActivityBtn").click(function(){
			//初始化工作,可以添加自己的jsp代码
			//重置表单
			$("#createActivityForm").get(0).reset();
			//弹出创建市场活动的模态窗口
			$("#createActivityModal").modal("show");
		});

		$("#saveCreateActivityBtn").click(function () {
			var owner =$.trim($("#create-marketActivityOwner").val());
			var name =$.trim( $("#create-marketActivityName").val());
			var startDate = $("#create-startTime").val();
			var endDate = $("#create-endTime").val();
			var cost = $("#create-cost").val();
			var description =$.trim($("#create-describe").val());
			if(owner==""){
				alert("所有者不能为空");
				return;
			}
			if(name==""){
				alert("活动名称不能为空");
				return;
			}
			if(startDate!=""&&endDate!=""){
				if(endDate < startDate){
					alert("结束日期不能比开始时间小");
					return;
				}
			}
			var regExp=/^(([1-9]\d*)|0)$/;
			if(!regExp.test(cost)){
				alert("成本不能为负数");
				return;
			}
			//发送请求
			$.ajax({
				url:'workbench/activity/SaveCreateActivity.do',
				data:{
					owner:owner,
					name:name,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if(data.code=="1"){
						//关闭窗口
						$("#createActivityModal").modal("hide");
						//	//刷新市场活动列，显示第一页数据，保持每页显示条数不变(保留)
						queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));

					}else{
						//提示信息
						alert(data.message);
						//模特窗口不关闭
						$("#createActivityModal").modal("show");//这可以不写，但是要把dismiss属性去掉
					}

				}
			});
		});
		//当容器加载完成之后，对容器调用工具函数
		$("#create-startTime").datetimepicker({
			language:'zh-CN', //语言
			format:'yyyy-mm-dd',//日期的格式
			minView:'month',//可以选择最小视图
			initialDate:new Date(),//初始化显示的日期
			autoclose:true,//设置选择完日期或者时间之后，是否自动关闭日历
			todayBtn:true,//设置是否显示”今天“按钮，默认是false
			clearBtn:true//设置是否显示清空按钮，默认是false
		});
		$("#create-endTime").datetimepicker({
			language:'zh-CN', //语言
			format:'yyyy-mm-dd',//日期的格式
			minView:'month',//可以选择最小视图
			initialDate:new Date(),//初始化显示的日期
			autoclose:true,//设置选择完日期或者时间之后，是否自动关闭日历
			todayBtn:true,//设置是否显示”今天“按钮，默认是false
			clearBtn:true//设置是否显示清空按钮，默认是false
		});
//当容器加载完成之后，对容器调用工具函数
		$("#edit-startTime").datetimepicker({
			language:'zh-CN', //语言
			format:'yyyy-mm-dd',//日期的格式
			minView:'month',//可以选择最小视图
			initialDate:new Date(),//初始化显示的日期
			autoclose:true,//设置选择完日期或者时间之后，是否自动关闭日历
			todayBtn:true,//设置是否显示”今天“按钮，默认是false
			clearBtn:true//设置是否显示清空按钮，默认是false
		});
		$("#edit-endTime").datetimepicker({
			language:'zh-CN', //语言
			format:'yyyy-mm-dd',//日期的格式
			minView:'month',//可以选择最小视图
			initialDate:new Date(),//初始化显示的日期
			autoclose:true,//设置选择完日期或者时间之后，是否自动关闭日历
			todayBtn:true,//设置是否显示”今天“按钮，默认是false
			clearBtn:true//设置是否显示清空按钮，默认是false
		});

		//当活动主页面加载完成，查询所有数据的第一页以及所有数据的总条数，默认每页显示10条

		queryActivityByConditionForPage(1,10);

		//给“查询按钮添加单机事件
		$("#queryActivityBtn").click(function () {
			//查询所有符合条件的数据的第一页及所有符合条件数据的总条数
			queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
		});
		//给"全选"按钮添加单击事件
		$("#checkAll").click(function () {
			//如果"全选"按钮是选中状态，则列表中所有checkbox都选中
			/*if(this.checked==true){
				$("#tBody input[type='checkbox']").prop("checked",true);
			}else{
				$("#tBody input[type='checkbox']").prop("checked",false);
			}*/

			$("#tBody input[type='checkbox']").prop("checked",this.checked);
		});

		$("#tBody").on("click", "input[type='checkbox']",function () {
			if($("#tBody input[type='checkbox']").size() == $("#tBody input[type='checkbox']:checked").size()){
				$("#checkAll").prop("checked",true);
			}else{
				$("#checkAll").prop("checked",false);
			}
		});
        //给删除按钮添加单机事件
        $("#deleteActivityBtn").click(function () {
            //获取参数
            //获取选中的checked的id号
            var str=$("#tBody input[type='checkbox']:checked");
            if(str.size()===0){
                alert("请选择要删除的市场活动");
                return;
            }
            if(window.confirm("你确定要删除吗？")){
                var ids="";
                $.each(str,function () {
                    ids+="id="+this.value+"&";
                })
                ids = ids.substr(0,ids.length-1);
                $.ajax({
                    url:'workbench/activity/deleteActivityByIds.do',
                    data:ids,
                    type:'post',
                    dataType:'json',
                    success:function (data) {
                        if(data.code==="1"){
                            //刷新市场活动页面
                            queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));

                        }else{
                            alert(data.message);
                        }
                    }
                });
            }
        });
		//修改市场活动的页面显示
		$("#updateActivityBtn").click(function (){
			//收集参数
			var checks =$("#tBody input[type='checkbox']:checked");
			if(checks.size() == 0){
				alert("请选择你要修改的");
				return;
			}
			if(checks.size() > 1){
				alert("只能选择一个进行修改");
				return;
			}

			var id = checks.val();
			$.ajax({
				url:'workbench/activity/updateActivity.do',
				data:id,
				type:'post',
				dataType:'json',
				contentType: "application/json; charset=utf-8",//此处不能省略  JSON字符串多了一个等号
				success:function(data){
					//把需要修改的信息显示在模态窗口上
					$("#edit-id").val(data.id);
					$("#edit-marketActivityOwner").val(data.owner);
					$("#edit-marketActivityName").val(data.name);
					$("#edit-startTime").val(data.startDate);
					$("#edit-endTime").val(data.startDate);
					$("#edit-cost").val(data.cost);
					$("#edit-describe").val(data.description);
					//弹出模态窗口
					$("#editActivityModal").modal("show");
				}
			});
		});
	//修改页面的更新按钮
		$("#editActivityBtn").click(function () {
			//获取参数
			var id = $("#edit-id").val();
			var owner = $.trim($("#edit-marketActivityOwner").val());
			var name = $.trim($("#edit-marketActivityName").val());
			var startDate = $("#edit-startTime").val();
			var endDate = $("#edit-endTime").val();
			var cost = $("#edit-cost").val();
			var description = $("#edit-describe").val();
			if(owner==""){
				alert("所有者不能为空");
				return;
			}
			if(name==""){
				alert("活动名称不能为空");
				return;
			}
			if(startDate!=""&&endDate!=""){
				if(endDate < startDate){
					alert("结束日期不能比开始时间小");
					return;
				}
			}
			var regExp=/^(([1-9]\d*)|0)$/;
			if(!regExp.test(cost)){
				alert("成本不能为负数");
				return;
			}

			$.ajax({
				url:'workbench/activity/saveEditActivity.do',
				data:JSON.stringify({
					id:id,
					owner:owner,
					name:name,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				}),
				contentType: "application/json; charset=utf-8",
				type:'post',
				dataType:'json',
				success:function (data) {
					if(data.code=="1"){
						//关闭窗口
						$("#editActivityModal").modal("hide");
						//刷新窗口,保证页号不变
						queryActivityByConditionForPage($("#demo_pag1").bs_pagination('getOption', 'currentPage'),$("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
					}else{
						alert(data.message);
						$("#editActivityModal").modal("show");
					}
				}
			});
		});
		$("#exportActivityAllBtn").click(function () {
			window.location.href="workbench/activity/exportAllActivities.do";
		});

		$("#exportActivityXzBtn").click(function () {
			var checks =$("#tBody input[type='checkbox']:checked");
			if(checks.size() === 0){
				alert("请选择你要修改的");
				return;
			}
			var id="";
			$.each(checks,function () {
				id +="id=" + this.value + "&";
			});
			id = id.substring(0,id.length-1)
			window.location.href = "workbench/activity/exportCheckedActivities.do?" + id;
		})

		//导入市场按钮点击事件
		$("#importActivityBtn").click(function () {
			var activityFilename = $("#activityFile").val();
			//判断是否为xls为后缀，不区分大小写
			var lastname = activityFilename.substring(activityFilename.lastIndexOf(".") + 1).toLocaleLowerCase();
			if (lastname !== "xls"){
				alert("请选择使用excel文件");
				return;
			}
			var activity = $("#activityFile")[0].files[0];
			if(activity.size > 5*1024*1024){
				alert("文件超过5M");
				return;
			}
			var formdata = new FormData();
			formdata.append("myfile",activity);
			$.ajax({
				url:'workbench/activity/exportupload.do',
				data:formdata,
				contentType:false,//设置ajax向后台提交参数之前，是否把参数统一按照urlencoded编码：true--是，false--不是，默认为true
				processData:false,//设置ajax向后台提交参数之前，是否把参数统一转换成字符串：true--是，false--不是，默认为true
				type:'post',
				dataType:'json',
				success:function (data) {
					if(data.code == "1"){
						alert("你已成功导入" + data.other + "条数据");
						//关闭模态窗口
						$("#importActivityModal").modal("hide");
						//刷新窗口,保证页号不变
						queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
					}else{
						alert(data.message);
						$("#importActivityModal").modal("show");
					}
				}
			})
		})

	function queryActivityByConditionForPage(pageNo,pageSize){
		//获取域中的数据
		var name = $("#query-name").val();
		var owner = $("#query-owner").val();
		var startDate = $("#query-startDate").val();
		var endDate = $("#query-endDate").val();
		// var pageNo=1;
		// var pageSize=10;
		$.ajax({
			url:'workbench/activity/queryActivityByCondition.do',
			data:{
				name:name,
				owner:owner,
				startDate:startDate,
				endDate:endDate,
				pageNo:pageNo,
				pageSize:pageSize
			},
			type: 'post',
			dataType: 'json',
			success:function (data) {
				//显示相应的页数
				//$("#totalRows").text(data.totalRows);
				//将查询到的数据对应起来
				//遍历数据，通过字符串拼接起来
				var titleRow="";
				$.each(data.activities,function (index,obj){
					titleRow+="<tr class=\"active\">";
					titleRow+="<td><input type=\"checkbox\" value=\""+obj.id+"\"/></td>";
					titleRow+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detailActivity.do?id="+ obj.id+"'\">"+obj.name+"</a></td>";
					titleRow+="<td>"+obj.owner+"</td>";
					titleRow+="<td>"+obj.startDate+"</td>";
					titleRow+="<td>"+obj.endDate+"</td>";
				});
				$("#tBody").html(titleRow);
				//由于换页之后，全选的checked就会存在，因此需要手动将其关闭
				$("#checkAll").prop("checked",false);

				//总页数
				var totalpage=1;
				if(data.totalRows % pageSize === 0){
					totalpage=data.totalRows/pageSize;
				}else{
					totalpage=parseInt(data.totalRows/pageSize) + 1;
				}

				$("#demo_pag1").bs_pagination({

					currentPage: pageNo,       //默认设置当前页数
					rowsPerPage: pageSize,        //每页显示条数，等于pageSize
					totalRows:data.totalRows,   //总条数
					totalPages: totalpage,     //总页数，可以根据总条数和每页显示条数显示出来
					visiblePageLinks: 5,  //每页中下面显示可选择页的个数
					showGoToPage: true,  //显示跳转页面的那个框，默认是true
					showRowsPerPage: true,      //是否显示每页显示条数部分。默认为true----显示
					showRowsInfo: true,    //是否显示记录信息，默认为true
					onChangePage: function(event,pageobj) { // returns page_num and rows_per_page after a link has clicked
						//用户每换一页会触发的事件
						//pageobj代表可以代表上面的currentPage，rowsPerPage
						queryActivityByConditionForPage(pageobj.currentPage,pageobj.rowsPerPage);
					},

				});
			}
		});

	}


	});
	
</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="createActivityForm">
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
								  <c:forEach items="${userList}" var="u">
									  <option value="${u.id}">${u.name}</option>
								  </c:forEach>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-startTime" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-endTime" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveCreateActivityBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" >
					<input type="hidden" id="edit-id">
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
									<c:forEach items="${userList}" var="u">
										<option value="${u.id}">${u.name}</option>
									</c:forEach>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startTime" value="2020-10-10" readonly>
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endTime" value="2020-10-20" readonly>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="editActivityBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="query-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="query-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="query-endDate">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="queryActivityBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createActivityBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="updateActivityBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteActivityBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="tBody">
					</tbody>
				</table>
			</div>
			<!--  Just create a div and give it an ID -->
			<div id="demo_pag1"></div>
<%--			<div style="height: 50px; position: relative;top: 30px;">--%>
<%--				<div>--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">共<b id="totalRows">50</b>条记录</button>--%>
<%--				</div>--%>
<%--				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>--%>
<%--					<div class="btn-group">--%>
<%--						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">--%>
<%--							10--%>
<%--							<span class="caret"></span>--%>
<%--						</button>--%>
<%--						<ul class="dropdown-menu" role="menu">--%>
<%--							<li><a href="#">20</a></li>--%>
<%--							<li><a href="#">30</a></li>--%>
<%--						</ul>--%>
<%--					</div>--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>--%>
<%--				</div>--%>
<%--				<div style="position: relative;top: -88px; left: 285px;">--%>
<%--					<nav>--%>
<%--						<ul class="pagination">--%>
<%--							<li class="disabled"><a href="#">首页</a></li>--%>
<%--							<li class="disabled"><a href="#">上一页</a></li>--%>
<%--							<li class="active"><a href="#">1</a></li>--%>
<%--							<li><a href="#">2</a></li>--%>
<%--							<li><a href="#">3</a></li>--%>
<%--							<li><a href="#">4</a></li>--%>
<%--							<li><a href="#">5</a></li>--%>
<%--							<li><a href="#">下一页</a></li>--%>
<%--							<li class="disabled"><a href="#">末页</a></li>--%>
<%--						</ul>--%>
<%--					</nav>--%>
<%--				</div>--%>
<%--			</div>--%>

		</div>
		
	</div>
</body>
</html>