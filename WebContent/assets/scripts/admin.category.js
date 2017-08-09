var iv = {
	init:function(){
		var self = this;
		self.bindData();

		$('#btn-add').click( function() {
			self.add();
		});
		$('#btn-save').click( function() {
			self.save();
		});

		$('#btn-del').click( function() {
			self.del();
		});
	},	
	bindData : function(){
		var setting = {
			view: {
				nameIsHTML: true,
				showTitle:false
			},
			callback : {
				onClick : function(event, treeId, treeNode) {
					$('#form-data').formData(treeNode);
					var s = typeof treeNode.Status == 'undefined' ? 1 : treeNode.Status;
					$('#sts-status').radioButtonList(status,'Status','text','value',s,2);
					if(treeNode.id == 0){
						$('input[name="Name"]').val(treeNode.Name);
					}else{
						$('input[name="Name"]').val(treeNode.CName);
					}

					if(typeof(treeNode.ParentId)=='undefined'||treeNode.ParentId==0){
						$('#parentId').val('');
					}
				}
			}
		};
		$('#sts-status').radioButtonList(status,'Status','text','value',1,2);
		
		$.get(basePath + 'admin/category/categoryNodes', null, function(data) {
			zTreeObj = $.fn.zTree.init($("#tree-category"), setting, data);
		})
		$('input[name="Level"]').kendoNumericTextBox({
			format: "#",
			decimals: 0,
			min: 1,
			max:99999999,
			value:1
		});
		$('input[name="OrderNum"]').kendoNumericTextBox({
			 format: "#",
			 decimals: 0,
			 min: 1,
			 max:99999999,
			 value:1
		});
	},
	add:function(){
		var treeObj=$.fn.zTree.getZTreeObj("tree-category"),
			nodes=treeObj.getSelectedNodes();

		if(nodes.length == 0){
			$.mdlg.error('提示','请选择父级分类。')
			return;
		}
		
		$('#form-data').resetForm();
		$('#cateId').val("");
		$('#parentId').val(nodes[0].Id);
		$('#sts-status input[value="1"]:radio').prop('checked',true);
		var level = (typeof nodes[0].Level == 'undefined')?1: parseInt(nodes[0].Level)+1;
        $('#level').data('kendoNumericTextBox').value(level);
	},

	save:function(){
		var params = $("#form-data").serializeJson();
		var self = this;

		$.post(basePath + 'admin/category/save', JSON.stringify(params), function(data) {
			if(data.result == true){
				$.mdlg.alert('提示',data.message);
				$('#form-data').resetForm();
				self.bindData();
			}else{
				$.mdlg.error('错误',data.message);
			}
		})
	},
	del:function(){
		var self = this;
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
							self.bindData();
						}else{
							$.mdlg.error('错误',data.message);
						}
					})
				}
				
				$(this).closeDialog(modal);
			}
		})
	}
}