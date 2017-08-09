var iv = {
	init:function(){
		var self = this;
		self.bindData();

		$('#btn-add').click( function() {
			self.add();
		});

		$('#btn-sync').click( function() {
			self.sync();
		});

		$('#btn-save').click( function() {
			self.save();
		});

		$('#btn-del').click( function() {
			self.del();
		});
		
		
		$("#file-upload").uploadify({
			height          : 34,
			swf             : basePath + '/assets/scripts/uploadify.swf',
			uploader        : basePath + '/admin/file/upload',
			width           : 120,
			multi		    : false,
			buttonText      : '选择图片',
			fileSizeLimit	: 500,
			fileTypeDesc  : '支持格式:jpg/gif/jpeg/png/bmp.',
			fileTypeExts  : '*.jpg;*.gif;*.jpeg;*.png;*.bmp',
			onUploadSuccess : function(file, data, response) {
				data = $.parseJSON(data);
				
				if(data.result){
					$('#img-icon').removeClass('hidden').attr('src',data.url);
					$('#icon-url').val(data.path);
				} else {
					$.mdlg.error('错误',data.message);
				}
			}
		});
	},	
	bindData : function(){
		var setting = {
			view: {
				nameIsHTML: true
			},
			callback : {
				onClick : function(event, treeId, treeNode) {
					$('#form-data').formData(treeNode);
					var s = typeof treeNode.Status == 'undefined' ? 1 : treeNode.Status;
					$('#sts-status').radioButtonList(status,'Status','text','value',s,2);
					
					if(typeof treeNode.IconUrl !== 'undefined'){
						$('#icon-url').val(treeNode.IconUrl);
						$('#img-icon').removeClass('hidden').attr('src',ftpUrl + treeNode.IconUrl);
					} else {
						$('#img-icon').addClass('hidden').attr('src','#');
					}
					
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
		
		$.get(basePath + 'admin/recommendCategory/categoryNodes', null, function(data) {
			zTreeObj = $.fn.zTree.init($("#tree-category"), setting, data);
		})

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
		$('#img-icon').addClass('hidden').attr('src','#');
		$('#cateId').val("");
		$('#parentId').val(nodes[0].Id);
		$('#stsNormal').prop('checked',true);
		$('#sts-status input[value="1"]:radio').prop('checked',true);
	},
	sync:function(){
		var self = this;

		$.mdlg.confirm('提示','您确认要将分类同步到前段展示么？',function(){
			var params = {};
			$.post(basePath + 'admin/recommendCategory/sync', JSON.stringify(params), function(data) {
				if(data.result == true){
					$.mdlg.alert('提示',data.message);
					$('#form-data').resetForm();
					$('#img-icon').addClass('hidden').attr('src','#');
					self.bindData();
				}else{
					$.mdlg.error('错误',data.message);
				}
			})

			$('#form-data').resetForm();
			$('#img-icon').addClass('hidden').attr('src','#');
			//$('#parentId').val(nodes[0].Id);
			$('#stsNormal').prop('checked',true)
		})
	},
	save:function(){
		var params = $("#form-data").serializeJson();
		var self = this;

		$.post(basePath + 'admin/recommendCategory/save', JSON.stringify(params), function(data) {
			if(data.result == true){
				$.mdlg.alert('提示',data.message);
				$('#form-data').resetForm();
				$('#img-icon').addClass('hidden').attr('src','#');
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
					
					$.post(basePath + 'admin/recommendCategory/delete',JSON.stringify(params) , function(data) {
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