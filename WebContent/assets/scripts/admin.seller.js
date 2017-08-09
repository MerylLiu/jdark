iv = {
	init:function(){
		this.bind();
		this.getInfo();
		
		$('#btn-add').click(function(){
			iv.edit();
		})
		$('#btn-edit').click(function(){
			var param = $("#grid").gridSelectedCols("Id");
			if(param.Id.length >0){
				iv.edit(param.Id[0]);
			}
		})
		$('#btn-del').click(function(){
			iv.delete();
		})
	},
	bind:function(){
		$("#grid").kendoGrid({
	        columns: [
	        {
	            title: "序号",
	            width: "80px",
	            field: "Id",
	            encoded: false,
				attributes:{ 'class':'center'},
				filterable:false,
				template: "<span class='row-number'></span>" 
	        },
	        {
	            title: "商家编号",
	            width: "100px",
	            field: "Code",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "商家名称",
	            width: "120px",
	            field: "Name",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "商家家服网ID",
	            width: "100px",
	            field: "SellerId",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "省",
	            width: "100px",
	            field: "Province",
	            encoded: false,
				attributes:{ 'class':'center'},
				values: provinces
	        },
	        {
	            title: "城市",
	            width: "100px",
	            field: "City",
	            encoded: false,
				attributes:{ 'class':'center'},
				values: provinces
	        },
	        {
	            title: "区域",
	            width: "100px",
	            field: "Area",
	            encoded: false,
				attributes:{ 'class':'center'},
				values: provinces
	        },
	        {
	            title: "联系电话",
	            width: "100px",
	            field: "Tel",
	            encoded: false,
				attributes:{ 'class':'center'},
				//values: $.parseJSON($('#role-list').val())
	        },
	        {
	            title: "联系地址",
	            width: "100px",
	            field: "Address",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "视频数",
	            width: "100px",
	            field: "VedioCount",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "开店时间",
	            width: "100px",
	            field: "SetUpDate",
	            encoded: false,
				attributes:{ 'class':'center'},
				format:'{0:yyyy-MM-dd}'
	        },
	        {
	            title: "商家经度",
	            width: "100px",
	            field: "Longitude",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "商家纬度",
	            width: "100px",
	            field: "Latitude",
	            encoded: false,
				attributes:{ 'class':'center'}
	        }],
			page:1,
	        filterable: true,
	        selectable: "Multiple, Row",
	        scrollable: true,
	        resizable:true,
			pageable: {
				"refresh": true,
				"autoBind": false,
				"input": true,
				"buttonCount": 10
			},
			autoBind:false,
	        dataSource: createDataSource(basePath + 'admin/seller/sellerList',{
				SetUpDate: {type: "date", parse: function(value) { return new Date(value);}},
				VedioCount: {type: "number"},
				Province: {type: "number"} 
			}),
			dataBound:function(){
				var rows = this.items();  
                var page = this.pager.page() - 1;  
                var pagesize = this.pager.pageSize();  
                $(rows).each(function () {  
                    var index = $(this).index() + 1 + page * pagesize;  
                    var rowLabel = $(this).find(".row-number");  
                    $(rowLabel).html(index);  
                });  	
			}
	    });	
	},
	getInfo:function(){
	    var grid = $('#grid').data('kendoGrid');
	    grid.dataSource.filter({});
	},
	bindDropDownList:function(){
		$('#txt-province').kendoDropDownList({
			dataTextField: "text",
			dataValueField: "value",
			optionLabel:'==请选择省==',
			dataSource: createDataSource(basePath+'region/getRegions'),
			change:function(){
				var urlCity = basePath+'region/getRegions?parentId=' + $('#txt-province').data('kendoDropDownList').value();

				$('#txt-city').kendoDropDownList({
					dataTextField: "text",
					dataValueField: "value",
					optionLabel:'==请选择市==',
					dataSource: createDataSource(urlCity),
					change:function(){
						var urlArea = basePath+'region/getRegions?parentId=' + $('#txt-city').data('kendoDropDownList').value();
						
						$('#txt-area').kendoDropDownList({
							dataTextField: "text",
							dataValueField: "value",
							optionLabel:'==请选择区／县==',
							dataSource: createDataSource(urlArea),
						});
					}
				});
				$('#txt-area').data("kendoDropDownList").setDataSource(null);
			}
		});
	},
	edit:function(id){
		$.mdlg({
			title:'商家',
			content:function(){
				return $('#data-tmpl').html();
			},
			onShow:function(){
				iv.bindDropDownList();
				
				$("#btn-search").click(function(){
					$.mdlg({
						title:'地图',
						width:'800px',
						content:function(){
							return $('#map-tmpl').html();
						},
						onShow:function(){
							var map = new BMap.Map("allmap",{
				                enableHighResolution: true //是否开启高清
				            });
				            var geoc = new BMap.Geocoder(); //地址解析对象
				            var pointArray = [];
				            var markerArray = [];
				            var pointx;
				            var pointy;

				            map.addControl(new BMap.NavigationControl());
				            map.enableInertialDragging(); //开启关系拖拽
				            map.enableScrollWheelZoom();  //开启鼠标滚动缩放
				            
				          //地图上标注
				            function addMarker(point) {
				                var marker=new BMap.Marker(point);
				                pointArray.push(point);
				                markerArray.push(marker);
				                map.addOverlay(marker);
				                marker.enableDragging();
				                marker.addEventListener("dragend", function (e) {
				                    $("#lng").val(e.point.lng);
				                    $("#lat").val(e.point.lat);
				                });
				            }
				            
				            if($("input[name='Longitude']").val()!=''&&$("input[name='Latitude']").val()!=''){
				            	var p = new BMap.Point($("input[name='Longitude']").val(),
		            					$("input[name='Latitude']").val());
				            	addMarker(p);
				            	map.centerAndZoom(p,17);				
				            	$("#lng").val($("input[name='Longitude']").val());
			                    $("#lat").val($("input[name='Latitude']").val());
							}else{
								map.centerAndZoom("中国",4);
							}
				            
				            function position(){
				            	map.clearOverlays();
				                markerArray = [];
				                pointArray = [];
				                var address = $("#address").val();
				                var provinceIndex = (address).indexOf("省");
				                var province;
				                if(provinceIndex>0){
				                    province = (address).substring(0,provinceIndex+1);
				                }
				                geoc.getPoint(address, function(point) {
				                    if (point) {
				                        //经度
				                        pointx = point.lng;
				                        $("#lng").val(pointx);
				                        //纬度
				                        pointy = point.lat;
				                        $("#lat").val(pointy);
				                        addMarker(point);
				                        map.centerAndZoom(point,17);
				                    }else{
				                        map.centerAndZoom("中国",4);
				                        $("#lng").val('');
					                    $("#lat").val('');
				                    }
				                }, typeof(province)=="undefined"?"四川省":province);
				            }
				            
				            $("#address").keyup(function(){
				            	position();
				            })
				            $("#btn-search-address").click(function(){
				            	position();
				            })
				            $("#lng").keyup(function(){
				            	if($("#lat").val()!=''){
				            		var point = new BMap.Point($("#lng").val(),$("#lat").val());
				            		geoc.getLocation(point, function(rs){
				            			   var addComp = rs.addressComponents;
				            			   $("#address").val(addComp.province + addComp.city + 
				            					   addComp.district + addComp.street
				            					   + addComp.streetNumber);
				            				});
				            			   if($('#address').val()!=''){
				            				   map.clearOverlays();
								               markerArray = [];
								               pointArray = [];
				            				   addMarker(point);
				            				   map.centerAndZoom(point,17);
				            			   }
				            	}
				            })
				            $("#lat").keyup(function(){
				            	if($("#lng").val()!=''){
				            		var point = new BMap.Point($("#lng").val(),$("#lat").val());
				            		geoc.getLocation(point, function(rs){
				            			   var addComp = rs.addressComponents;
				            			   $("#address").val(addComp.province + addComp.city + 
				            					   addComp.district + addComp.street
				            					   + addComp.streetNumber);
				            			   });
				            			   if($('#address').val()!=''){
				            				   map.clearOverlays();
				            				   markerArray = [];
				            				   pointArray = [];
				            				   addMarker(point);
				            				   map.centerAndZoom(point,17);
				            			   }
				            	}
				            })
						},
						showCloseButton:false,
						buttons:["确认","取消"],
						buttonStyles:['btn-success','btn-default'],
						onButtonClick:function(sender,modal,index){
							var self = this;
			                if (index == 0) {
			                	$("input[name='Longitude']").val($("#lng").val());
								$("input[name='Latitude']").val($("#lat").val());
								$(this).closeDialog(modal);
			                }else{
			                	$(this).closeDialog(modal);
			                }
						}
					})
				})
				
				$('#txt-city').kendoDropDownList({});
				$('#txt-area').kendoDropDownList({});

				$('#txt-order').kendoNumericTextBox({
					 format: "#",
                     decimals: 0,
                     min: 1,
                     max:99999999,
                     value:1
				});

				$('#txt-set-date').kendoDatePicker({
					format:"yyyy-MM-dd"
				});

				if(id){
					$.get(basePath + 'admin/seller/getSeller',{Id:id},function(data){
						$('#form-data').formData(data);
						iv.bindDropDownList();
						
						var province = $('#txt-province').data("kendoDropDownList");
						province.value(data.Province);
						province.trigger("change");

						var city = $('#txt-city').data("kendoDropDownList");
						city.value(data.City);
						city.trigger("change");

						$('#txt-area').data("kendoDropDownList").value(data.Area);

						$('#txt-set-date').kendoDatePicker({
							format:"yyyy-MM-dd",
							value:new Date(data.SetUpDate)
						});
					});
				}
			},
			showCloseButton:false,
			buttons:["保存","取消"],
			buttonStyles:['btn-success','btn-default'],
			onButtonClick:function(sender,modal,index){
				var self = this;

				if(index == 0){
					var params = $("#form-data").serializeJson();

					$.post(basePath + 'admin/seller/save',JSON.stringify(params) , function(data) {
						if(data.result == true){
							$.mdlg.alert('提示',data.message);
							$('#form-menu').clearForm();
							iv.getInfo();
							$(this).closeDialog(modal);
						}else{
							$.mdlg.error('错误',data.message);
						}
					})
				}else{
					$(this).closeDialog(modal);
				}
			}
		})
	},
	delete:function(){
		$.mdlg.confirm("删除","您确认要删除么？",function(){
			var params = $("#grid").gridSelectedCols('Id');

			$.post(basePath + 'admin/seller/delete', JSON.stringify(params), function (data) {
				if (data.result) {
					$.mdlg.alert('提示',data.message);
					iv.getInfo();
				} else {
					$.mdlg.error('错误',data.message);
				}
			}).fail(errors);
		})
	}
}