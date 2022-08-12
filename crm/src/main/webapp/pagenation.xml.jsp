<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!--  It is a good idea to bundle all CSS in one file. The same with JS -->

    <!--  JQUERY -->
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>

    <!--  BOOTSTRAP -->
    <link rel="stylesheet" type="text/css" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css">
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

    <!--  PAGINATION plugin -->
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
    <script type="text/javascript">
        $(function() {

            $("#demo_pag1").bs_pagination({
                totalPages: 100,   //总条数
                currentPage: 1,       //默认设置当前页数
                rowsPerPage: 10,        //每页显示条数，等于pageSize
                maxRowsPerPage: 100,
                totalPages: 100,     //总页数，可以根据总条数和每页显示条数显示出来
                totalRows: 0,

                visiblePageLinks: 5,  //每页中下面显示可选择页的个数

                showGoToPage: true,  //显示跳转页面的那个框，默认是true
                showRowsPerPage: true,      //是否显示每页显示条数部分。默认为true----显示
                showRowsInfo: true,    //是否显示记录信息，默认为true
                showRowsDefaultInfo: true,
                onChangePage: function(event,pageobj) { // returns page_num and rows_per_page after a link has clicked
                //用户每换一页会触发的事件
                //pageobj代表可以代表上面的currentPage，rowsPerPage
                },

            });


        });
    </script>
    <title>Title</title>
</head>
<body>
<!--  Just create a div and give it an ID -->
<div id="demo_pag1"></div>
</body>
</html>
