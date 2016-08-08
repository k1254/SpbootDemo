var gtransit;
var callPhone;
jQuery(function($) {
	$('#name').focus();
	
	//取消ボタン：ページをリフレッシュ
	$("#btnCancel").click(function() {
		location.reload(true);
	})
	//電話
	callPhone = function(id) {
		if($(id).val()!='') {
			window.location = "tel:" + $(id).val();
		}
	}
	//ルート案内
	gtransit = function(id) {
		user = {
		    "toDoFuKen": $('#toDoFuKen').val(),
		    "siKuTyoSon": $('#siKuTyoSon').val(),
		    "banTi": $('#banTi').val(),
		    "byoin1Jusyo": $(id).val()
		};
		alert("gtransit:"+id+", "+JSON.stringify(user));
		$.ajax({
			async: false, //別ウィンドウで開くため
		    method: 'post',
		    contentType: 'application/json;charset=utf-8',
		    data: JSON.stringify(user),
		    url: 'google.transit',
		    dataType: 'json'
		}).done(function(url) {
			window.open(url)
		});
	}	
});
