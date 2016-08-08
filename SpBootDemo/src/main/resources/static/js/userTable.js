jQuery(function($) {
    var fBtn = false;
    var oTable = $("#dtTable").dataTable({
        "processing": true,	  //処理中の表示
        "serverSide": true,   //サーバーサイド処理
        "lengthChange": true, //ページ表示行数を変更
        "lengthMenu": [[ 10, 20,],[10, 20]], //ページ表示行数の項目設定
        "ajax": {
            "url" : "userPage",
            "contentType": "application/json",
            "data": function ( d ) {
                return JSON.stringify(d);
            }
        },
        "columns": [
                    {"data": "name", "width": 100 },
                    {"data": "simei", "width": 80 },
                    {"data": "yomi", "width": 80 },
                    {"data": "seinenGappi", "width": 80 },
                    {
                        "data": "id", "class": "center", "width": 160, "orderable": false,
                        "createdCell": function (td, cellData, rowData, row, col) {
                            var bd = $('<button class="btn_default">'+
                                    '<span class="glyphicon glyphicon-trash"></span></button>');
                            bd.button();
                            bd.on('click',function(){
                              fBtn = true;
                              alert("Delete:"+cellData);
                            });
                            var be = $('<button class="btn_default">'+
                                    '<span class="glyphicon glyphicon-pencil"></span></button>');
                            be.button();
                            be.on('click',function(){
                              fBtn = true;
                              window.location.href = "/editUser/" + cellData;
                            });
                            $(td).empty();
                            $(td).prepend(bd);    		 		
                            $(td).prepend(be);
                        }
                    }
                ],
                "language": japaneseLang,
                "pagingType" : "full_numbers"	//ページングボタンの表示タイプ
    }).api(); //dataTables
	//テーブルのクリック行の選択状態をトグルする
	$('#dtTable tbody').on( 'click', 'tr', function () {
        if (fBtn==false) {
            $(this).toggleClass('selected');
        } else {
            fBtn = false;
        }
    });
});
