$(document).ready( function() {
	var setting = {
		callback : {
			onClick : function(event, treeId, treeNode) {
				$('#form-data').formData(treeNode);
				$('#sts-status').radioButtonList(status,'Status','Text','Value',treeNode.Status,2);
				$('input[name="Name"]').val(treeNode.Name.substr(6));
			}
		}
	};
	
	var bindData = function(){
		$('#sts-status').radioButtonList(status,'Status','Text','Value',1,2);
		
		$.get(basePath + 'admin/category/categoryNodes', null, function(data) {
			zTreeObj = $.fn.zTree.init($("#tree-category"), setting, data);
		})
	};
	
	$('#btn-add').click( function() {
		var treeObj=$.fn.zTree.getZTreeObj("tree-category"),
			nodes=treeObj.getSelectedNodes();

		if(nodes.length == 0){
			$.mdlg.error('提示','请选择父级分类。')
			return;
		}
		
		$('#form-data').resetForm();
		$('#parentId').val(nodes[0].Id);
		$('#stsNormal').prop('checked',true)
	});

	$('#btn-save').click( function() {
		var params = $("#form-data").serializeJson();

		$.post(basePath + 'admin/category/save', JSON.stringify(params), function(data) {
			if(data.result == true){
				$.mdlg.alert('提示',data.message);
				$('#form-data').resetForm();
				bindData();
			}else{
				$.mdlg.error('错误',data.message);
			}
		})
	})

	$('#btn-del').click( function() {
		var treeObj=$.fn.zTree.getZTreeObj("tree-category"),
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
					
					$.post(basePath + 'admin/category/delete',JSON.stringify(params) , function(data) {
						if(data.result == true){
							$.mdlg.alert('提示',data.message);
							$('#form-data').clearForm();
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