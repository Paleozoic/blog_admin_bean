var deptSelect = (function(){
	var select = {};
	/**
	 * deptId 根机构ID 必填
	 * $click 绑定点击弹窗的页面元素，例如 $click = $("input[name='test']",dlg);
	 */
	select.singleSelect = function(deptId,$click,ftn){
		$click.click(function(){
			var options = {
					id:'dlgDeptSingleSelect',
					title:'机构选择',
					width:250,
					height:450,
					mask:true,
					url:"tools/dept/single_select",
					data:{"deptId":deptId},
					onSubmit:ftn,
					beforeSubmit:setDept,
					clear:setDeptEmpty
				};
			$(this).dialog(options);
		});
		
		var setDept = function(){
			var dlg = $.CurrentDialog;
			var deptId = $("input[name='deptId']",dlg).val();
			var deptName = $("span[name='deptName']",dlg).html();
			return {"deptId":deptId,"deptName":deptName};
		}
		var setDeptEmpty = function(){
			return {"deptId":"","deptName":""};
		}
	}
	return select;
})();