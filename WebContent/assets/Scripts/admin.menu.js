$(document).ready( function() {
	var setting = {
		callback : {
			onClick : function(event, treeId, treeNode) {
				$('#form-menu').formData(treeNode);
				$('#name').val(treeNode.MenuName);
			}
		}
	};
	
	var bindData = function(){
		$.get(basePath + '/admin/menu/menuNodes', null, function(data) {
			zTreeObj = $.fn.zTree.init($("#tree-menu"), setting, data);
		})
	};
	
	$('#btn-add').click( function() {
		var treeObj=$.fn.zTree.getZTreeObj("tree-menu"),
			nodes=treeObj.getSelectedNodes();

		if(nodes.length == 0){
			$.mdlg.error('提示','请选择父级菜单。')
			return;
		}
		
		$('#form-menu').clearForm();
		$('#menuId').val('');
		$('#parentId').val(nodes[0].Id);
		$('#enable').prop('checked',true)
		$('#visiable').prop('checked',true)
	});

	$('#btn-save').click( function() {
		var params = $("#form-menu").serializeJson();

		$.post(basePath + 'admin/menu/save', JSON.stringify(params), function(data) {
			if(data.result == true){
				$.mdlg.alert('提示',data.message);
				$('#form-menu').clearForm();
				bindData();
			}else{
				$.mdlg.error('错误',data.message);
			}
		})
	})

	$('#btn-del').click( function() {
		var treeObj=$.fn.zTree.getZTreeObj("tree-menu"),
			nodes=treeObj.getSelectedNodes();
		
		if(nodes.length == 0){
			$.mdlg.error('提示','请选择要删除的记录。')
			return;
		}

		$.mdlg({
			title:'提示',
			content:'确认删除本条记录?',
			showCloseButton:false,
			buttons:["确定","取消"],
			buttonStyles:['btn-success','btn-default'],
			onButtonClick:function(sender,modal,index){
				if(index == 0){
					var params = {Id:nodes[0].Id};

					$.post(basePath + 'admin/menu/delete',JSON.stringify(params) , function(data) {
						if(data.result == true){
							$.mdlg.alert('提示',data.message);
							$('#form-menu').clearForm();
							bindData();
						}else{
							$.mdlg.error('错误',data.message);
						}
					})
				}
				
				$(this).closeDialog(modal);
			}
		})
	})
	
	bindData();
})